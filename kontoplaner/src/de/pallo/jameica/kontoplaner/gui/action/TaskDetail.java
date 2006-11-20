/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/gui/action/TaskDetail.java,v $
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
package de.pallo.jameica.kontoplaner.gui.action;

import java.rmi.RemoteException;

import de.pallo.jameica.kontoplaner.Settings;
import de.pallo.jameica.kontoplaner.rmi.Task;
import de.pallo.jameica.kontoplaner.rmi.Referencekonto;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.util.ApplicationException;

/**
 * Action for "show Task details" or "create new Task".
 */
public class TaskDetail implements Action
{

  /**
   * @see de.willuhn.jameica.gui.Action#handleAction(java.lang.Object)
   */
  public void handleAction(Object context) throws ApplicationException
  {

		Task task = null;
		
		// check if the context is a task
		if (context != null && (context instanceof Task))
			task = (Task) context;
		

		// context null?
		// --> create a new task
		if (context == null)
		{
			try
			{
				task = (Task) Settings.getDBService().createObject(Task.class,null);
			}
			catch (RemoteException e)
			{
				throw new ApplicationException(Settings.i18n().tr("error while creating new task"),e);
			}
		}

		// check if the context is a project
		// --> create a new task and assign the given project
  	if (context != null && (context instanceof Referencekonto))
  	{
			try
			{
				Referencekonto referencekonto = (Referencekonto) context;
				if (referencekonto.isNewObject())
					throw new ApplicationException(Settings.i18n().tr("Please store the referencekonto first"));
				task = (Task) Settings.getDBService().createObject(Task.class,null);
				task.setReferencekonto(referencekonto);
			}
			catch (RemoteException e)
			{
				throw new ApplicationException(Settings.i18n().tr("Error while creating new task"),e);
			}
  	}


		// ok, lets start the dialog
  	GUI.startView(de.pallo.jameica.kontoplaner.gui.view.TaskDetail.class.getName(),task);
  }

}


/**********************************************************************
 * $Log: TaskDetail.java,v $
 * Revision 1.2  2006/11/20 19:08:00  pallo
 * renamed some files from prioject to Referencekonten and some translations form english to german
 *
 * Revision 1.1.1.1  2006/11/20 17:48:45  pallo
 * no message
 *
 **********************************************************************/