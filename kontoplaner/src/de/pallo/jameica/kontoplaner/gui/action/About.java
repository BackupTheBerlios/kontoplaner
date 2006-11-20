/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/gui/action/About.java,v $
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
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.dialogs.AbstractDialog;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;

public class About implements Action
{

  /**
   * @see de.willuhn.jameica.gui.Action#handleAction(java.lang.Object)
   */
  public void handleAction(Object context) throws ApplicationException
  {
  	try
  	{
			new de.pallo.jameica.kontoplaner.gui.view.About(AbstractDialog.POSITION_CENTER).open();
  	}
  	catch (Exception e)
  	{
  		Logger.error("error while opening about dialog",e);
  		throw new ApplicationException(Settings.i18n().tr("Error while opening the About dialog"));
  	}
  }

}


/**********************************************************************
 * $Log: About.java,v $
 * Revision 1.1  2006/11/20 17:48:45  pallo
 * Initial revision
 *
 **********************************************************************/