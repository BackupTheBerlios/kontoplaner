/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/gui/menu/TaskListMenu.java,v $
 * $Revision: 1.1 $
 * $Date: 2006/11/20 17:48:46 $
 * $Author: pallo $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/
package de.pallo.jameica.kontoplaner.gui.menu;

import de.pallo.jameica.kontoplaner.Settings;
import de.pallo.jameica.kontoplaner.gui.action.TaskDelete;
import de.pallo.jameica.kontoplaner.gui.action.TaskDetail;
import de.pallo.jameica.kontoplaner.gui.action.TaskDuplicate;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.parts.CheckedContextMenuItem;
import de.willuhn.jameica.gui.parts.ContextMenu;
import de.willuhn.jameica.gui.parts.ContextMenuItem;
import de.willuhn.util.ApplicationException;

/**
 * Prepared context menu for task tables. 
 */
public class TaskListMenu extends ContextMenu
{
	/**
   * ct.
   */
  public TaskListMenu()
	{
		// CheckedContextMenuItems will be disabled, if the user clicks into an empty space of the table
		addItem(new CheckedContextMenuItem(Settings.i18n().tr("Open..."),new TaskDetail()));

		// separator
		addItem(ContextMenuItem.SEPARATOR);

		addItem(new CheckedContextMenuItem(Settings.i18n().tr("Duplicate Task"),new TaskDuplicate()));

		addItem(new ContextMenuItem(Settings.i18n().tr("Create a new Task"),new Action()
		{
			public void handleAction(Object context) throws ApplicationException
			{
				// we force the context to be null to create a new task in any case
				new TaskDetail().handleAction(null);
			}
		}));

		addItem(ContextMenuItem.SEPARATOR);
		addItem(new CheckedContextMenuItem(Settings.i18n().tr("Delete Task"),new TaskDelete()));

	}
}


/**********************************************************************
 * $Log: TaskListMenu.java,v $
 * Revision 1.1  2006/11/20 17:48:46  pallo
 * Initial revision
 *
 **********************************************************************/