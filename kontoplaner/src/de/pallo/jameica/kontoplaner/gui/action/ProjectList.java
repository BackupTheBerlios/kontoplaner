/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/gui/action/Attic/ProjectList.java,v $
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

import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.util.ApplicationException;

/**
 * Action to open the project list.
 */
public class ProjectList implements Action
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
 * $Log: ProjectList.java,v $
 * Revision 1.1  2006/11/20 17:48:45  pallo
 * Initial revision
 *
 **********************************************************************/