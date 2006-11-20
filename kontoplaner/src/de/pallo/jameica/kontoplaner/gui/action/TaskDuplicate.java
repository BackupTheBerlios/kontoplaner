/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/gui/action/TaskDuplicate.java,v $
 * $Revision: 1.2 $
 * $Date: 2006/11/20 20:05:32 $
 * $Author: pallo $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by pallo
 * All rights reserved
 *
 **********************************************************************/
package de.pallo.jameica.kontoplaner.gui.action;

import java.rmi.RemoteException;

import de.pallo.jameica.kontoplaner.Settings;
import de.pallo.jameica.kontoplaner.rmi.Task;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.util.ApplicationException;

/**
 * Action for "duplicate Task".
 */
public class TaskDuplicate implements Action
{

  /**
   * @see de.willuhn.jameica.gui.Action#handleAction(java.lang.Object)
   */
  public void handleAction(Object context) throws ApplicationException
  {

		if (context == null || !(context instanceof Task))
			throw new ApplicationException(Settings.i18n().tr("Please a task you want to duplicate"));

		Task task = null;
		try
		{
			// Lets create a new Task
			task = (Task) Settings.getDBService().createObject(Task.class,null);
		
			// copy the attributes into the new object.
			task.overwrite((Task)context);
		}
		catch (RemoteException e)
		{
			throw new ApplicationException(Settings.i18n().tr("Error while duplicating the task"),e);
		}

		// ok, lets start the dialog 
  	GUI.startView(de.pallo.jameica.kontoplaner.gui.view.TaskDetail.class.getName(),task);
  }

}


/**********************************************************************
 * $Log: TaskDuplicate.java,v $
 * Revision 1.2  2006/11/20 20:05:32  pallo
 * changed author
 *
 * Revision 1.1.1.1  2006/11/20 17:48:45  pallo
 * no message
 *
 **********************************************************************/