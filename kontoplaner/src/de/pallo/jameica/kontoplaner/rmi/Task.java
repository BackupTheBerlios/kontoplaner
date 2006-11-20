/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/rmi/Task.java,v $
 * $Revision: 1.1 $
 * $Date: 2006/11/20 17:48:47 $
 * $Author: pallo $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/

package de.pallo.jameica.kontoplaner.rmi;

import java.rmi.RemoteException;
import java.util.Date;

import de.willuhn.datasource.rmi.DBObject;


/**
 * Interface of the business object for tasks.
 * According to the SQL table, we define some getter an setter here.
 * <pre>
 * CREATE TABLE task (
 *   id NUMERIC default UNIQUEKEY('task'),
 *   project_id int(4) NOT NULL,
 *   name varchar(255) NOT NULL,
 *   comment text NOT NULL,
 *   effort double,
 *   UNIQUE (id),
 *   PRIMARY KEY (id)
 * );
 * </pre>
 * <br>Getters and setters for the primary key (id) are not needed.
 * Every one of the following methods has to throw a RemoteException.
 * <br>
 * 
 * #############################################################################
 * # IMPORTANT:                                                                #
 * # All business objects are RMI objects. So you have to run the              #
 * # rmi compiler (rmic) to create the needed stubs.                           #
 * # Whitout that you will get a                                               #
 * # java.lang.reflect.InvocationTargetException                               #
 * # (caused by java.rmi.StubNotFoundException)                                #
 * #############################################################################
 * 
 * @author willuhn
 */
public interface Task extends DBObject
{
	/**
	 * Returns the project for this task.
   * @return the project.
   * @throws RemoteException
   */
  public Referencekonto getReferencekonto() throws RemoteException;
	
	/**
	 * Stores the Project for this task.
   * @param referencekonto
     * @throws RemoteException
   */
  public void setReferencekonto(Referencekonto referencekonto) throws RemoteException;

	/**
	 * Returns the name of this task.
   * @return name of the task.
   * @throws RemoteException
   */
  public String getName() throws RemoteException;

	/**
	 * Stores the name of the task.
   * @param name name of the task.
   * @throws RemoteException
   */
  public void setName(String name) throws RemoteException;
  
  /**
   * Returns the comment for the task.
   * @return comment.
   * @throws RemoteException
   */
  public String getComment() throws RemoteException;

	/**
	 * Stores the task comment.
   * @param comment task comment.
   * @throws RemoteException
   */
  public void setComment(String comment) throws RemoteException;

	/**
	 * Returns the effort for this task.
   * @return effort.
   * @throws RemoteException
   */
  public double getSumme() throws RemoteException;

	/**
	 * Stores the effort for this task.
   * @param sum
     * @throws RemoteException
   */
  public void setSumme(double sum) throws RemoteException;

    public double getAktuelleSumme() throws RemoteException;

    public double getAktuelleSumme(Date datum) throws RemoteException;

    public Date getStartdatum() throws RemoteException;

    public void setStartdatum(Date datum) throws RemoteException;

    public Date getEnddatum() throws RemoteException;

    public void setEnddatum(Date datum) throws RemoteException;

    public Date getPeriodenStartdatum() throws RemoteException;

    public Date getPeriodenEnddatum() throws RemoteException;

    public PERIODIK_TYPES getPeriodik() throws RemoteException;

    public void setPeriodik(PERIODIK_TYPES periodik) throws RemoteException;

    public TASK_TYPES getTyp() throws RemoteException;

    public void setTyp(TASK_TYPES type) throws RemoteException;

    public INTERVALL_TYPES getIntervall() throws RemoteException;

    public void setIntervall(INTERVALL_TYPES type) throws RemoteException;

    public void setPrioritaet(int prio) throws RemoteException;

    public int getPrioritaet() throws RemoteException;

    public boolean valid() throws RemoteException;
    public boolean valid(Date date) throws RemoteException;

    public static enum INTERVALL_TYPES {
        MONAT, TAG
    }

    public static enum PERIODIK_TYPES {
        EINMAL, ZYKLISCH, JAHR
    }

    public static enum TASK_TYPES {
        SPAREN, AUSGABEN
    }

}


/**********************************************************************
 * $Log: Task.java,v $
 * Revision 1.1  2006/11/20 17:48:47  pallo
 * Initial revision
 *
 **********************************************************************/