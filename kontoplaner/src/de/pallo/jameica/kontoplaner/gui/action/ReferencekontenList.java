/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/gui/action/ReferencekontenList.java,v $
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

import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.util.ApplicationException;

/**
 * Action to open the project list.
 */
public class ReferencekontenList implements Action
{

  /**
   * @see de.willuhn.jameica.gui.Action#handleAction(java.lang.Object)
   */
  public void handleAction(Object context) throws ApplicationException
  {
  	GUI.startView(de.pallo.jameica.kontoplaner.gui.view.ReferencekontenList.class.getName(),null);
  }

}


/**********************************************************************
 * $Log: ReferencekontenList.java,v $
 * Revision 1.2  2006/11/20 20:05:32  pallo
 * changed author
 *
 * Revision 1.1  2006/11/20 19:08:00  pallo
 * renamed some files from prioject to Referencekonten and some translations form english to german
 *
 * Revision 1.1.1.1  2006/11/20 17:48:45  pallo
 * no message
 *
 **********************************************************************/