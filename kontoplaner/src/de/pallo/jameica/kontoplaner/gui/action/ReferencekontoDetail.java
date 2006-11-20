/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/gui/action/ReferencekontoDetail.java,v $
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

import java.rmi.RemoteException;

import de.pallo.jameica.kontoplaner.Settings;
import de.pallo.jameica.kontoplaner.rmi.Referencekonto;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.util.ApplicationException;

/**
 * Action for "show project details" or "create new Project".
 */
public class ReferencekontoDetail implements Action
{

  /**
   * @see de.willuhn.jameica.gui.Action#handleAction(java.lang.Object)
   */
  public void handleAction(Object context) throws ApplicationException
  {

		Referencekonto referencekonto = null;

		// check if the context is a project
  	if (context != null && (context instanceof Referencekonto))
  		referencekonto = (Referencekonto) context;
		else
		{
			try
			{
				referencekonto = (Referencekonto) Settings.getDBService().createObject(Referencekonto.class,null);
			}
			catch (RemoteException e)
			{
				throw new ApplicationException(Settings.i18n().tr("error while creating new referencekotno"),e);
			}
		}

		// ok, lets start the dialog
  	GUI.startView(de.pallo.jameica.kontoplaner.gui.view.ReferencekontenDetail.class.getName(),referencekonto);
  }

}


/**********************************************************************
 * $Log: ReferencekontoDetail.java,v $
 * Revision 1.2  2006/11/20 20:05:32  pallo
 * changed author
 *
 * Revision 1.1.1.1  2006/11/20 17:48:45  pallo
 * no message
 *
 **********************************************************************/