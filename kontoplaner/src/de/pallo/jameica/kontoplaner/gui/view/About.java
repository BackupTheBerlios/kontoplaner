/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/gui/view/About.java,v $
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

import org.eclipse.swt.widgets.Composite;

import de.pallo.jameica.kontoplaner.KontoplanerPlugin;
import de.pallo.jameica.kontoplaner.Settings;
import de.willuhn.jameica.gui.dialogs.AbstractDialog;
import de.willuhn.jameica.gui.input.LabelInput;
import de.willuhn.jameica.gui.parts.FormTextPart;
import de.willuhn.jameica.gui.util.LabelGroup;
import de.willuhn.jameica.plugin.AbstractPlugin;
import de.willuhn.jameica.system.Application;

/**
 * Our "About..." dialog.
 */
public class About extends AbstractDialog
{

  /**
   * ct.
   * @param position
   */
  public About(int position)
  {
    super(position);
    this.setTitle(Settings.i18n().tr("About..."));
  }

  /**
   * @see de.willuhn.jameica.gui.dialogs.AbstractDialog#paint(org.eclipse.swt.widgets.Composite)
   */
  protected void paint(Composite parent) throws Exception
  {

		FormTextPart text = new FormTextPart();
		text.setText("<form>" +
			"<p><b>Jameica kontoplaner plugin</b></p>" +
			"<br/>Licence: GPL (http://www.gnu.org/copyleft/gpl.html)" +
			"<br/><p>Copyright by Markus Pallo [pallo@users.berlios.de]</p>" +
			"<p>http://kontoplaner.berlios.de</p>" +
			"</form>");

		text.paint(parent);

		LabelGroup group = new LabelGroup(parent, " Information ");

		AbstractPlugin p = Application.getPluginLoader().getPlugin(KontoplanerPlugin.class);

		group.addLabelPair(Settings.i18n().tr("Version"), 					new LabelInput(""+p.getManifest().getVersion()));
		group.addLabelPair(Settings.i18n().tr("Working directory"), new LabelInput(""+p.getResources().getWorkPath()));

  }

  /**
   * @see de.willuhn.jameica.gui.dialogs.AbstractDialog#getData()
   */
  protected Object getData() throws Exception
  {
    return null;
  }

}


/**********************************************************************
 * $Log: About.java,v $
 * Revision 1.2  2006/11/20 20:05:32  pallo
 * changed author
 *
 * Revision 1.1.1.1  2006/11/20 17:48:46  pallo
 * no message
 *
 **********************************************************************/