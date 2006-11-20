/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/gui/view/Welcome.java,v $
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
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.util.LabelGroup;
import de.willuhn.util.ApplicationException;


/**
 * Welcome screen of this kontoplaner plugin.
 * @author pallo
 */
public class Welcome extends AbstractView
{

  /**
   * this method will be invoked when starting the view.
   * @see de.willuhn.jameica.gui.AbstractView#bind()
	 */
	public void bind() throws Exception
	{
		GUI.getView().setTitle(Settings.i18n().tr("Example plugin"));
		
		LabelGroup group = new LabelGroup(this.getParent(),Settings.i18n().tr("welcome"));
		
		group.addText("this page intentionally left blank ;)",false);

	}

  /**
   * this method will be executed when exiting the view.
   * You don't need to dispose your widgets, the GUI controller will
   * do this in a recursive way for you.
   * @see de.willuhn.jameica.gui.AbstractView#unbind()
	 */
	public void unbind() throws ApplicationException
	{
    // We've nothing to do here ;)
	}

}


/**********************************************************************
 * $Log: Welcome.java,v $
 * Revision 1.2  2006/11/20 20:05:32  pallo
 * changed author
 *
 * Revision 1.1.1.1  2006/11/20 17:48:47  pallo
 * no message
 *
 **********************************************************************/