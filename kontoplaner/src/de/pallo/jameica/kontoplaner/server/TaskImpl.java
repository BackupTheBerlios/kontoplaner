/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/server/TaskImpl.java,v $
 * $Revision: 1.1 $
 * $Date: 2006/11/20 17:48:48 $
 * $Author: pallo $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/
package de.pallo.jameica.kontoplaner.server;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import de.willuhn.datasource.db.AbstractDBObject;
import de.willuhn.datasource.rmi.ObjectNotFoundException;
import de.pallo.jameica.kontoplaner.Settings;
import de.pallo.jameica.kontoplaner.util.Round;
import de.pallo.jameica.kontoplaner.rmi.Task;
import de.pallo.jameica.kontoplaner.rmi.Referencekonto;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;

/**
 * Implementation of the task interface.
 * Look into ProjectImpl for more code comments.
 */
public class TaskImpl extends AbstractDBObject implements Task
{

    /** The number of seconds in a minute */
    public static final long SECONDS_IN_MINUTE = 60;

    /** The number of minutes in an hour */
    public static final long MINUTES_IN_HOUR = 60;

    /** The number of hours in a day */
    public static final long HOURS_IN_DAY = 24;
    Date cachedPeriodenstart;
    Date cachedPeriodenend;
    Double cachedAktuelleSumme;
  /**
   * ct.
   * @throws RemoteException
   */
  public TaskImpl() throws RemoteException
  {
    super();
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#getTableName()
   */
  protected String getTableName()
  {
  	// this is the sql table name.
    return "task";
  }

  /**
   * @see de.willuhn.datasource.GenericObject#getPrimaryAttribute()
   */
  public String getPrimaryAttribute() throws RemoteException
  {
  	// our primary attribute is the name.
    return "name";
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#deleteCheck()
   */
  protected void deleteCheck() throws ApplicationException
  {
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#insertCheck()
   */
  protected void insertCheck() throws ApplicationException
  {
		try {
			if (getName() == null || getName().length() == 0)
				throw new ApplicationException(Settings.i18n().tr("Please enter a task name"));
			
			if (getReferencekonto() == null)
				throw new ApplicationException(Settings.i18n().tr("Please choose a project"));

			if (getReferencekonto().isNewObject())
				throw new ApplicationException(Settings.i18n().tr("Please store project first"));

		}
		catch (RemoteException e)
		{
			Logger.error("insert check of project failed",e);
			throw new ApplicationException(Settings.i18n().tr("unable to store project, please check the system log"));
		}
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#updateCheck()
   */
  protected void updateCheck() throws ApplicationException
  {
  	// same as insertCheck
  	insertCheck();
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#getForeignObject(java.lang.String)
   */
  protected Class getForeignObject(String field) throws RemoteException
  {
		// the system is able to resolve foreign keys and loads
		// the according objects automatically. You only have to
		// define which class handles which foreign key.
  	if ("referencekonto_id".equals(field))
  		return Referencekonto.class;
    return null;
  }


    private void clearCachedObjects() {
        cachedPeriodenstart = null;
        cachedPeriodenend = null;
        cachedAktuelleSumme = null;
    }
  /**
   * @see de.pallo.jameica.kontoplaner.rmi.Task#getReferencekonto()
   */
  public Referencekonto getReferencekonto() throws RemoteException
  {
  	// Yes, we can cast this directly to Project, because getForeignObject(String)
  	// contains the mapping for this attribute.
  	try
  	{
			return (Referencekonto) getAttribute("referencekonto_id");
  	}
  	catch (ObjectNotFoundException e)
  	{
  		return null;
  	}
  }

  /**
   * @see de.pallo.jameica.kontoplaner.rmi.Task#setReferencekonto(de.pallo.jameica.kontoplaner.rmi.Referencekonto)
   * @param referencekonto
   */
  public void setReferencekonto(Referencekonto referencekonto) throws RemoteException
  {
  	// same here
  	setAttribute("referencekonto_id",referencekonto);
  }

  /**
   * @see de.pallo.jameica.kontoplaner.rmi.Task#getName()
   */
  public String getName() throws RemoteException
  {
    return (String) getAttribute("name");
  }

  /**
   * @see de.pallo.jameica.kontoplaner.rmi.Task#setName(java.lang.String)
   */
  public void setName(String name) throws RemoteException
  {
  	setAttribute("name",name);
  }

  /**
   * @see de.pallo.jameica.kontoplaner.rmi.Task#getComment()
   */
  public String getComment() throws RemoteException
  {
    return (String) getAttribute("comment");
  }

  /**
   * @see de.pallo.jameica.kontoplaner.rmi.Task#setComment(java.lang.String)
   */
  public void setComment(String comment) throws RemoteException
  {
  	setAttribute("comment",comment);
  }

  /**
   * @see de.pallo.jameica.kontoplaner.rmi.Task#getSumme()
   */
  public double getSumme() throws RemoteException
  {
    Double d = (Double) getAttribute("summe");
    return d == null ? 0.0 : d.doubleValue();
  }

  /**
   * @see de.pallo.jameica.kontoplaner.rmi.Task#setSumme(double)
   * @param sum
   */
  public void setSumme(double sum) throws RemoteException
  {
  	setAttribute("summe", new Double(sum));
      clearCachedObjects();
  }

    public double getAktuelleSumme() throws RemoteException {
        return getAktuelleSumme(new Date());
    }

    public double getAktuelleSumme(Date date) throws RemoteException {
        if (cachedAktuelleSumme != null) {
            return cachedAktuelleSumme.doubleValue();
        }
        if (valid(date)) {
            if (TASK_TYPES.SPAREN.equals(getTyp())) {
                cachedAktuelleSumme = calculateSparSumme(date);
            } else {
                cachedAktuelleSumme = calculateAusgabenSumme(date);
            }
        } else {
            cachedAktuelleSumme = 0.0;
        }
        return cachedAktuelleSumme;
    }

    private double calculateAusgabenSumme(Date date) throws RemoteException {
        double anteil = getSparAnteil(date);
        return Round.round(getSumme() * (1-anteil));
    }

    private double getSparAnteil(Date date) throws RemoteException {
        double anteil = 1;
        if (Task.INTERVALL_TYPES.MONAT.equals(getIntervall())) {
            anteil = getMonthAnteil(date);
        } else {
            // Tag berechnen
            anteil = getTagAnteil(date);
        }
        return anteil;
    }

    private double calculateSparSumme(Date date) throws RemoteException {
        double anteil = getSparAnteil(date);
        return Round.round(getSumme()*anteil);
    }

    private double getMonthAnteil(Date date) throws RemoteException {
        Calendar refCalendar = new GregorianCalendar();
        refCalendar.setTime(date);
        Calendar start = new GregorianCalendar();
        start.setTime(getPeriodenStartdatum());
        Calendar end = new GregorianCalendar();
        end.setTime(getPeriodenEnddatum());
        double monthDiffOverall = (double) monthDifference(end, start);
        // add one if the day of month is later than start
        if (end.get(Calendar.DAY_OF_MONTH) > start.get(Calendar.DAY_OF_MONTH)) {
            monthDiffOverall++;
        }
        double monthDiff = (double) monthDifference(refCalendar, start);
        if (monthDiff == 0) {
            monthDiff++;
        }
        // add one if the day of month is later than start
        if (end.get(Calendar.DAY_OF_MONTH) > start.get(Calendar.DAY_OF_MONTH)) {
            monthDiff++;
        }
        return monthDiff / monthDiffOverall;
    }

    private double getTagAnteil(Date date) throws RemoteException {
        Calendar refCalendar = new GregorianCalendar();
        refCalendar.setTime(date);
        Calendar start = new GregorianCalendar();
        start.setTime(getPeriodenStartdatum());
        Calendar end = new GregorianCalendar();
        end.setTime(getPeriodenEnddatum());
        double dayDiffOverall = dateDifference(end, start);
        double dayDiff = dateDifference(refCalendar, start);
        return dayDiff / dayDiffOverall;
    }

    private int monthDifference(Calendar end, Calendar begin) {
        int yearDiff = end.get(Calendar.YEAR) - begin.get(Calendar.YEAR);
        int monthDiff = yearDiff * 12 + end.get(Calendar.MONTH) - begin.get(Calendar.MONTH);

        return monthDiff;
    }
/**
     * The purpose of this method is to return the number of days between
     * two dates. The method truncates off the hour, minute and second values
     * of the two dates and subtracts them to get the number of calendar days
     * that separate the two calendars.
     */
    private int dateDifference(Calendar endDate, Calendar beginDate)
    {
        long differenceInMillis = dateDifferenceInMillis(beginDate, endDate);
        return (int)Math.round((double)(differenceInMillis)/1000/SECONDS_IN_MINUTE/MINUTES_IN_HOUR/HOURS_IN_DAY);
    }

    /**
     * The purpose of this method is to return the number of milliseconds between
     * two dates. The method truncates off the hour, minute and second values
     * of the two dates and subtracts them to get the number of millisconds
     * that separate the two calendars.
     */
    private long dateDifferenceInMillis(Calendar beginDate, Calendar endDate) {
        Calendar truncatedBegin = (Calendar) beginDate.clone();
        Calendar truncatedEnd = (Calendar) endDate.clone();
        truncatedBegin.set(Calendar.AM_PM, 0);
        truncatedBegin.set(Calendar.HOUR_OF_DAY, 0);
        truncatedBegin.set(Calendar.HOUR, 0);
        truncatedBegin.set(Calendar.MINUTE, 0);
        truncatedBegin.set(Calendar.SECOND, 0);
        truncatedBegin.set(Calendar.MILLISECOND, 0);
        truncatedEnd.set(Calendar.AM_PM, 0);
        truncatedEnd.set(Calendar.HOUR_OF_DAY, 0);
        truncatedEnd.set(Calendar.HOUR, 0);
        truncatedEnd.set(Calendar.MINUTE, 0);
        truncatedEnd.set(Calendar.SECOND, 0);
        truncatedEnd.set(Calendar.MILLISECOND, 0);

        return truncatedEnd.getTime().getTime() -
               truncatedBegin.getTime().getTime();
    }


    public Date getStartdatum() throws RemoteException {
        return (Date)getAttribute("startdatum");
    }

    public void setStartdatum(Date datum) throws RemoteException {
        setAttribute("startdatum", datum);
        clearCachedObjects();
    }

    public Date getEnddatum() throws RemoteException {
        return (Date) getAttribute("enddatum");
    }

    public void setEnddatum(Date datum) throws RemoteException {
        setAttribute("enddatum",datum);
        clearCachedObjects();
    }

    private void calculatePeriode(Date date) throws RemoteException {
        do {
            calculateNextPeriode();
        } while( (!valid(date) && !cachedPeriodenstart.after(date)) && !getPeriodik().equals(PERIODIK_TYPES.EINMAL));
    }

    private void calculateNextPeriode() throws RemoteException {
        if (cachedPeriodenstart == null) {
            cachedPeriodenstart = getStartdatum();
            if (cachedPeriodenstart == null) {
                cachedPeriodenstart = new Date();
            }
            cachedPeriodenend = getEnddatum();
        } else {
            if (PERIODIK_TYPES.ZYKLISCH.equals(getPeriodik())) {
                Calendar start = new GregorianCalendar();
                start.setTime(cachedPeriodenstart);
                Calendar end = new GregorianCalendar();
                end.setTime(cachedPeriodenend);
                int monthDiff = monthDifference(end, start);
                if (monthDiff == 0) {
                    monthDiff++;
                }
                start.add(Calendar.MONTH, monthDiff);
                end.add(Calendar.MONTH, monthDiff);
                cachedPeriodenstart = start.getTime();
                cachedPeriodenend = end.getTime();
            } else {
                Calendar start = new GregorianCalendar();
                start.setTime(cachedPeriodenstart);
                Calendar end = new GregorianCalendar();
                end.setTime(cachedPeriodenend);
                start.add(Calendar.YEAR, 1);
                end.add(Calendar.YEAR, 1);
                cachedPeriodenstart = start.getTime();
                cachedPeriodenend = end.getTime();
            }
        }
    }

    public Date getPeriodenStartdatum() throws RemoteException {
        if (cachedPeriodenstart == null) {
            calculatePeriode(new Date());
        }
        return cachedPeriodenstart;
    }

    public Date getPeriodenEnddatum() throws RemoteException {
        if (cachedPeriodenend == null) {
            calculatePeriode(new Date());
        }

        return cachedPeriodenend;
    }

    public PERIODIK_TYPES getPeriodik() throws RemoteException {
        String periodik = (String) getAttribute("periodik");
        if (periodik == null) {
            return PERIODIK_TYPES.EINMAL;
        }
        return Enum.valueOf(Task.PERIODIK_TYPES.class, periodik);
    }

    public void setTyp(TASK_TYPES typ) throws RemoteException {
        String typString = (String) getAttribute("typ");
        setAttribute("typ", typ);
        clearCachedObjects();
    }

    public TASK_TYPES getTyp() throws RemoteException {
        String typString = (String) getAttribute("typ");
        if (typString == null) {
            return TASK_TYPES.SPAREN;
        }
        return Enum.valueOf(Task.TASK_TYPES.class, typString);
    }

    public void setPeriodik(Task.PERIODIK_TYPES type) throws RemoteException {
        setAttribute("periodik", type);
        clearCachedObjects();
    }

    public INTERVALL_TYPES getIntervall() throws RemoteException {
        String intervall = ((String)getAttribute("intervall"));
        if (intervall == null) {
            return Task.INTERVALL_TYPES.TAG;
        }

        return Enum.valueOf(Task.INTERVALL_TYPES.class, intervall.toUpperCase());
    }

    public void setIntervall(INTERVALL_TYPES type) throws RemoteException {
        setAttribute("intervall", type);
        clearCachedObjects();
    }

    public void setPrioritaet(int prio) throws RemoteException {
        setAttribute("prioritaet",prio);
    }

    public int getPrioritaet() throws RemoteException {
        Integer prio = (Integer)getAttribute("prioritaet");
        if (prio == null) {
            return -1;
        }
        return prio;
    }

    public boolean valid() throws RemoteException {
        return valid(new Date());
    }
    public boolean valid(Date refDate) throws RemoteException {
        if ( (refDate.equals(getPeriodenStartdatum()) || refDate.after(getPeriodenStartdatum())) &&
            (getPeriodenEnddatum() == null || refDate.before(getPeriodenEnddatum()))) {
            return true;
        }
        return false;
    }


    public Object getAttribute(String string) throws RemoteException {
        if ("periodenstartdatum".equals(string)) {
            return getPeriodenStartdatum();
        } else if("periodenendedatum".equals(string)) {
            return getPeriodenEnddatum();
        } else if("aktuellesumme".equals(string)) {
            return getAktuelleSumme();
        }
        return super.getAttribute(string);
    }
}


/**********************************************************************
 * $Log: TaskImpl.java,v $
 * Revision 1.1  2006/11/20 17:48:48  pallo
 * Initial revision
 *
 **********************************************************************/