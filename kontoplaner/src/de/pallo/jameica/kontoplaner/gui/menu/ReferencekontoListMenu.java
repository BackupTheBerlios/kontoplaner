/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/gui/menu/ReferencekontoListMenu.java,v $
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
import de.pallo.jameica.kontoplaner.gui.action.ReferencekontoDetail;
import de.willuhn.jameica.gui.parts.CheckedContextMenuItem;
import de.willuhn.jameica.gui.parts.ContextMenu;

/**
 * Prepared context menu for project tables. 
 */
public class ReferencekontoListMenu extends ContextMenu
{
    /**
   * ct.
   */
  public ReferencekontoListMenu()
    {
        // CheckedContextMenuItems will be disabled, if the user clicks into an empty space of the table
        addItem(new CheckedContextMenuItem(Settings.i18n().tr("Open..."),new ReferencekontoDetail()));

    }
}


/**********************************************************************
 * $Log: ReferencekontoListMenu.java,v $
 * Revision 1.1  2006/11/20 17:48:46  pallo
 * Initial revision
 *
 **********************************************************************/