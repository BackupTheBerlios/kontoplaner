/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/gui/control/TaskControl.java,v $
 * $Revision: 1.2 $
 * $Date: 2006/11/20 19:08:00 $
 * $Author: pallo $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/
package de.pallo.jameica.kontoplaner.gui.control;

import java.rmi.RemoteException;
import java.util.Date;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import de.willuhn.datasource.GenericIterator;
import de.pallo.jameica.kontoplaner.Settings;
import de.pallo.jameica.kontoplaner.rmi.Task;
import de.pallo.jameica.kontoplaner.rmi.Referencekonto;
import de.willuhn.jameica.gui.AbstractControl;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.dialogs.ListDialog;
import de.willuhn.jameica.gui.input.*;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;

/**
 * Controller for the task view.
 */
public class TaskControl extends AbstractControl
{

	// the current task object
	private Task task;

	// the input fields for the task.
	private DialogInput referencekonto;
	private Input name;
    private Input comment;
    private DateInput startdatum;
    private DateInput endedatum;
    private Input summe;
    private SelectInput intervall;
    private SelectInput periodik;
    private SelectInput typ;
    private IntegerInput prioritaet;

  /**
   * ct.
   * @param view
   */
  public TaskControl(AbstractView view)
  {
    super(view);
  }

	/**
	 * Returns the current task.
   * @return the task.
   */
  private Task getTask()
	{
		if (task != null)
			return task;
		task = (Task) getCurrentObject();
		return task;
	}

	/**
	 * Returns a the field to choose the project.
   * @return the project.
   * @throws RemoteException
   */
  public Input getReferencekonto() throws RemoteException
	{
		if (referencekonto != null)
			return referencekonto;
		
		// this custom widget contains two parts.
		// 1) a readonly text field to show up the choosen project name
		// 2) a button behind that opens a dialog, containing a list of existing projects.
		
		// first we have to create the dialog
		GenericIterator projects = Settings.getDBService().createList(Referencekonto.class);
		ListDialog d = new ListDialog(projects,ListDialog.POSITION_MOUSE);
		d.setTitle(Settings.i18n().tr("Wähle ein Referenzkonto"));
		d.addColumn(Settings.i18n().tr("Bezeichnung"),"bezeichnung");
		d.addCloseListener(new Listener()
    {
			// The text field doesn't know, what to display after choosing the project
			// So add a close listener to the dialog, catch the project and set it's
			// name by hand into the text field.
      public void handleEvent(Event event)
      {
      	if (event == null || event.data == null)
      		return;
      	if (!(event.data instanceof Referencekonto))
      		return;
      		
				try
				{
					referencekonto.setText(((Referencekonto)event.data).getName());
				}
				catch (RemoteException e)
				{
					Logger.error("erro while choosing referencekonto",e);
					GUI.getStatusBar().setErrorText(Settings.i18n().tr("Error while choosing referencekonto"));
				}
      }
    });
    
    Referencekonto referencekonto = getTask().getReferencekonto();

		// the initial choosen project
    String s = referencekonto == null ? "" : referencekonto.getName();
    this.referencekonto = new DialogInput(s,d);
    this.referencekonto.setValue(referencekonto); // we store the initial value

		// we disable the client control (the text field)
		// the user shouldn't be able to enter a project name by hand
    this.referencekonto.disableClientControl();
    return this.referencekonto;
	}

	/**
	 * Returns an input field for the task name.
   * @return input field.
   * @throws RemoteException
   */
  public Input getName() throws RemoteException
	{
		if (name != null)
			return name;
		// "255" is the maximum length of the name attribute.
		name = new TextInput(getTask().getName(),255);
		return name;
    }

    /**
     * Returns an input field for the task summe.
     * @return input field.
     * @throws RemoteException
     */
    public Input getSumme() throws RemoteException
    {
        if (summe != null)
            return summe;

        // we assign our system decimal formatter
        summe = new DecimalInput(getTask().getSumme(),Settings.DECIMALFORMAT);
        summe.setComment(Settings.i18n().tr("Hours [kontoplaner: enter \"0,5\" to store 30 minutes]"));
        return summe;
    }

    public DateInput getStartdatum() throws RemoteException
    {

        if (startdatum != null)
            return startdatum;

        Date d = task.getStartdatum();
        if (d == null)
            d = new Date();

        this.startdatum = new DateInput(d,Settings.DATEFORMAT);
        this.startdatum.setTitle(Settings.i18n().tr("Termin"));
        this.startdatum.setText(Settings.i18n().tr("Bitte wählen Sie einen Termin"));
        this.startdatum.setComment("");
        return startdatum;
    }

    public DateInput getEnddatum() throws RemoteException
    {

        if (endedatum != null)
            return endedatum;

        Date d = task.getEnddatum();
        if (d == null)
            d = new Date();

        this.endedatum = new DateInput(d,Settings.DATEFORMAT);
        this.endedatum.setTitle(Settings.i18n().tr("Zielermin"));
        this.endedatum.setText(Settings.i18n().tr("Bitte wählen Sie einen Termin"));
        this.endedatum.setComment("");
        return endedatum;
    }

    /**
	 * Returns an input field for the task comment.
   * @return input field.
   * @throws RemoteException
   */
  public Input getComment() throws RemoteException
	{
		if (comment != null)
			return comment;
		comment = new TextAreaInput(getTask().getComment());
		return comment;
	}

 public Input getIntervall() throws RemoteException
  {
    if (this.intervall != null)
      return this.intervall;

      String value = null;
      if (task.getIntervall() != null) {
          value = task.getIntervall().toString();
      }
    this.intervall = new SelectInput(toString(Task.INTERVALL_TYPES.values()),value);
    this.intervall.setPleaseChoose(Settings.i18n().tr("Alle Intervalle"));
    return this.intervall;
  }

 public Input getPeriodik() throws RemoteException
 {
     if (periodik != null)
         return periodik;

     String value = null;
     if (task.getPeriodik() != null) {
         value = task.getPeriodik().toString();
     }
     periodik = new SelectInput(toString(Task.PERIODIK_TYPES.values()),value);
     this.periodik.setPleaseChoose(Settings.i18n().tr("Alle Perioden"));
     return periodik;
 }

    public Input getTyp() throws RemoteException
    {
        if (typ != null)
            return typ;

        String value = null;
        if (task.getTyp() != null) {
            value = task.getTyp().toString();
        }
        typ = new SelectInput(toString(Task.TASK_TYPES.values()),value);
        return typ;
    }

    public IntegerInput getPrioritaet() throws RemoteException {
        if (prioritaet != null)
            return prioritaet;

        prioritaet = new IntegerInput(task.getPrioritaet());
        return prioritaet;
    }

  private String[] toString(Object[] oa) {
      String[] s = new String[oa.length];
      for (int i=0 ; i< oa.length; i++) {
          s[i] = oa[i].toString();
      }
      return s;
  }

    /**
	 * This method stores the task using the current values. 
	 */
    public void handleStore()
    {
        try
        {

            // get the current task.
            Task t = getTask();

            // invoke all Setters of this task and assign the current values
            t.setName((String) getName().getValue());

            // we can cast the value of the project dialogInput directly to "Project".
            t.setReferencekonto((Referencekonto) getReferencekonto().getValue());

            // the DecimalInput fields returns a Double object
            Double d = (Double) getSumme().getValue();
            t.setSumme(d == null ? 0.0 : d.doubleValue());
            t.setStartdatum((Date)getStartdatum().getValue());
            t.setComment((String) getComment().getValue());
            t.setStartdatum((Date) getStartdatum().getValue());
            t.setEnddatum((Date) getEnddatum().getValue());
            if (getIntervall().getValue() != null) {
                t.setIntervall(Task.INTERVALL_TYPES.valueOf((String)getIntervall().getValue()));
            } else {
                t.setIntervall(null);
            }
            if (getPeriodik().getValue() != null) {
                t.setPeriodik(Task.PERIODIK_TYPES.valueOf((String) getPeriodik().getValue()));
            } else {
                t.setPeriodik(null);
            }

            if (getTyp().getValue() != null) {
                t.setTyp(Task.TASK_TYPES.valueOf((String) getTyp().getValue()));
            } else {
                t.setTyp(null);
            }

            if (getPrioritaet().getValue() != null) {
                t.setPrioritaet((Integer) getPrioritaet().getValue());
            } else {
                t.setPrioritaet(1);
            }


            // Now, let's store the project
            // The store() method throws ApplicationExceptions if
            // insertCheck() or updateCheck() failed.
            try
            {
                t.store();
				GUI.getStatusBar().setSuccessText(Settings.i18n().tr("Task stored successfully"));
			}
			catch (ApplicationException e)
			{
				GUI.getView().setErrorText(e.getMessage());
			}
		}
		catch (RemoteException e)
		{
			Logger.error("error while storing task",e);
			GUI.getStatusBar().setErrorText(Settings.i18n().tr("Error while storing Task"));
		}
	}

}


/**********************************************************************
 * $Log: TaskControl.java,v $
 * Revision 1.2  2006/11/20 19:08:00  pallo
 * renamed some files from prioject to Referencekonten and some translations form english to german
 *
 * Revision 1.1.1.1  2006/11/20 17:48:46  pallo
 * no message
 *
 **********************************************************************/