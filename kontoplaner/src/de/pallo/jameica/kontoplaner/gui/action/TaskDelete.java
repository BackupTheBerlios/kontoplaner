/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/gui/action/TaskDelete.java,v $
 * $Revision: 1.1 $
 * $Date: 2006/11/20 17:48:45 $
 * $Author: pallo $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/
package de.pallo.jameica.kontoplaner.gui.action;

import de.pallo.jameica.kontoplaner.Settings;
import de.pallo.jameica.kontoplaner.rmi.Task;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.dialogs.YesNoDialog;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;

/**
 * Action for "delete task".
 */
public class TaskDelete implements Action
{

  /**
   * @see de.willuhn.jameica.gui.Action#handleAction(java.lang.Object)
   */
  public void handleAction(Object context) throws ApplicationException
  {

		// check if the context is a project
  	if (context == null || !(context instanceof Task))
  		throw new ApplicationException(Settings.i18n().tr("Please choose a task you want to delete"));

    Task t = (Task) context;
    
    try
    {

			// before deleting the task , we show up a confirm dialog ;)
			
			YesNoDialog d = new YesNoDialog(YesNoDialog.POSITION_CENTER);
			d.setTitle(Settings.i18n().tr("Are you sure?"));
			d.setText(Settings.i18n().tr("Do you really want to delete this task?"));
			
			Boolean choice = (Boolean) d.open();
			if (!choice.booleanValue())
				return;

      t.delete();
      GUI.getStatusBar().setSuccessText(Settings.i18n().tr("Task deleted successfully"));
         	GUI.startPreviousView();       
    }
    catch (Exception e)
    {
      Logger.error("error while deleting task",e);
      throw new ApplicationException(Settings.i18n().tr("Error while deleting task"));
    }
  }

}


/**********************************************************************
 * $Log: TaskDelete.java,v $
 * Revision 1.1  2006/11/20 17:48:45  pallo
 * Initial revision
 *
 **********************************************************************/