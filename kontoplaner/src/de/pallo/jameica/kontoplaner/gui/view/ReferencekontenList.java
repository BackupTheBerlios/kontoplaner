/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/gui/view/ReferencekontenList.java,v $
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
package de.pallo.jameica.kontoplaner.gui.view;

import de.pallo.jameica.kontoplaner.Settings;
import de.pallo.jameica.kontoplaner.gui.action.Back;
import de.pallo.jameica.kontoplaner.gui.control.ReferencekontenControl;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.util.ButtonArea;
import de.willuhn.util.ApplicationException;

/**
 * View to show the list of existing projects.
 */
public class ReferencekontenList extends AbstractView
{

  /**
   * @see de.willuhn.jameica.gui.AbstractView#bind()
   */
  public void bind() throws Exception {

		GUI.getView().setTitle(Settings.i18n().tr("Vorhandene Konten"));
		
		ReferencekontenControl control = new ReferencekontenControl(this);
		
		control.getReferencekontoTable().paint(this.getParent());
		
		ButtonArea buttons = new ButtonArea(this.getParent(),2);
		buttons.addButton(Settings.i18n().tr("<< Zurück"),				  		new Back());
		
		// the last parameter "true" makes the button the default one
		buttons.addButton(Settings.i18n().tr("Aktualisieren"),		new de.pallo.jameica.kontoplaner.gui.action.SyncKonten(),null,true);
		
  }

  /**
   * @see de.willuhn.jameica.gui.AbstractView#unbind()
   */
  public void unbind() throws ApplicationException {
  }

}


/**********************************************************************
 * $Log: ReferencekontenList.java,v $
 * Revision 1.2  2006/11/20 20:05:32  pallo
 * changed author
 *
 * Revision 1.1.1.1  2006/11/20 17:48:47  pallo
 * no message
 *
 **********************************************************************/