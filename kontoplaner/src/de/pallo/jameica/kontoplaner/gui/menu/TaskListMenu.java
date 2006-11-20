/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/gui/menu/TaskListMenu.java,v $
 * $Revision: 1.3 $
 * $Date: 2006/11/20 20:05:32 $
 * $Author: pallo $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by pallo
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
		addItem(new CheckedContextMenuItem(Settings.i18n().tr("Öffnen..."),new TaskDetail()));

		// separator
		addItem(ContextMenuItem.SEPARATOR);

		addItem(new CheckedContextMenuItem(Settings.i18n().tr("Task duplizieren"),new TaskDuplicate()));

		addItem(new ContextMenuItem(Settings.i18n().tr("Neuen Task erstellen"),new Action()
		{
			public void handleAction(Object context) throws ApplicationException
			{
				// we force the context to be null to create a new task in any case
				new TaskDetail().handleAction(null);
			}
		}));

		addItem(ContextMenuItem.SEPARATOR);
		addItem(new CheckedContextMenuItem(Settings.i18n().tr("Task löschen"),new TaskDelete()));

	}
}


/**********************************************************************
 * $Log: TaskListMenu.java,v $
 * Revision 1.3  2006/11/20 20:05:32  pallo
 * changed author
 *
 * Revision 1.2  2006/11/20 20:01:52  pallo
 * added help and made translations
 *
 * Revision 1.1.1.1  2006/11/20 17:48:46  pallo
 * no message
 *
 **********************************************************************/