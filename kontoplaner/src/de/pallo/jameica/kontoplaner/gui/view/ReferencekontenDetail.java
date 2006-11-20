/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/gui/view/ReferencekontenDetail.java,v $
 * $Revision: 1.2 $
 * $Date: 2006/11/20 19:08:00 $
 * $Author: pallo $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/

package de.pallo.jameica.kontoplaner.gui.view;

import de.pallo.jameica.kontoplaner.Settings;
import de.pallo.jameica.kontoplaner.gui.action.Back;
import de.pallo.jameica.kontoplaner.gui.action.TaskDetail;
import de.pallo.jameica.kontoplaner.gui.control.ReferencekontenControl;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.util.ButtonArea;
import de.willuhn.jameica.gui.util.Headline;
import de.willuhn.jameica.gui.util.LabelGroup;
import de.willuhn.util.ApplicationException;


/**
 * this is the dialog for the project details. 
 */
public class ReferencekontenDetail extends AbstractView
{

	/**
   * @see de.willuhn.jameica.gui.AbstractView#bind()
   */
    public void bind() throws Exception
    {
        // draw the title
        GUI.getView().setTitle(Settings.i18n().tr("Konten details"));

        // instanciate controller
        final ReferencekontenControl control = new ReferencekontenControl(this);
    
        // create a bordered group
        LabelGroup group = new LabelGroup(getParent(),Settings.i18n().tr("Konten details"));
    
        // all all input fields to the group.
        group.addLabelPair(Settings.i18n().tr("Bezeichnung"), control.getBezeichnung());
        group.addLabelPair(Settings.i18n().tr("Name"),        control.getName());
        group.addLabelPair(Settings.i18n().tr("Kontonummer"),       control.getKontonummer());
        group.addLabelPair(Settings.i18n().tr("BLZ"),  control.getBLZ());
        group.addLabelPair(Settings.i18n().tr("Saldo"),	control.getSaldo());
        // show up the effort summary
        LabelGroup summary = new LabelGroup(getParent(),Settings.i18n().tr("Summe"));
        summary.addLabelPair(Settings.i18n().tr("Verfügbar" + ":"),	control.getSaldoAvailable());

        // add some buttons
        ButtonArea buttons = new ButtonArea(getParent(),4);

        buttons.addButton(Settings.i18n().tr("<< Zurück"),                    	new Back());
        buttons.addButton(Settings.i18n().tr("Neuer Task für dieses Konto"), new TaskDetail(),control.getCurrentObject());


        // show task tasks in this project
        new Headline(getParent(),Settings.i18n().tr("Tasks dieses Kontos"));
        control.getTaskList().paint(getParent());

	}

	/**
   * @see de.willuhn.jameica.gui.AbstractView#unbind()
   */
  public void unbind() throws ApplicationException
	{
    // this method will be invoked when leaving the dialog.
    // You are able to interrupt the unbind by throwing an
    // ApplicationException.
	}

}


/**********************************************************************
 * $Log: ReferencekontenDetail.java,v $
 * Revision 1.2  2006/11/20 19:08:00  pallo
 * renamed some files from prioject to Referencekonten and some translations form english to german
 *
 * Revision 1.1.1.1  2006/11/20 17:48:46  pallo
 * no message
 *
 **********************************************************************/