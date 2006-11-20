/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/gui/view/TaskDetail.java,v $
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
import de.pallo.jameica.kontoplaner.gui.action.TaskDelete;
import de.pallo.jameica.kontoplaner.gui.control.TaskControl;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.util.ButtonArea;
import de.willuhn.jameica.gui.util.LabelGroup;
import de.willuhn.util.ApplicationException;

/**
 */
public class TaskDetail extends AbstractView
{

  /**
   * @see de.willuhn.jameica.gui.AbstractView#bind()
   */
  public void bind() throws Exception
  {
		// draw the title
		GUI.getView().setTitle(Settings.i18n().tr("Task details"));

		// instanciate controller
		final TaskControl control = new TaskControl(this);
    
		// create a bordered group
		LabelGroup group = new LabelGroup(getParent(),Settings.i18n().tr("Task details"));
    
		// all all input fields to the group.
		group.addLabelPair(Settings.i18n().tr("Konto"),     control.getReferencekonto());
		group.addLabelPair(Settings.i18n().tr("Name"),        control.getName());		group.addLabelPair(Settings.i18n().tr("Comment"), 		control.getComment());
        group.addLabelPair(Settings.i18n().tr("Startdatum"), control.getStartdatum());
        group.addLabelPair(Settings.i18n().tr("Zieldatum"), control.getEnddatum());
        group.addLabelPair(Settings.i18n().tr("Summe"),      control.getSumme());
        group.addLabelPair(Settings.i18n().tr("Sparintervall"),      control.getIntervall());
        group.addLabelPair(Settings.i18n().tr("Periodik"),      control.getPeriodik());
        group.addLabelPair(Settings.i18n().tr("Typ"),      control.getTyp());
        group.addLabelPair(Settings.i18n().tr("Priorität"),      control.getPrioritaet());

        // add some buttons
		ButtonArea buttons = new ButtonArea(getParent(),4);

		buttons.addButton(Settings.i18n().tr("<< Zurück"),        new Back());
		buttons.addButton(Settings.i18n().tr("Task löschen"),		new TaskDelete(),control.getCurrentObject());
		buttons.addButton(Settings.i18n().tr("Task speichern"),  		new Action()
		{
			public void handleAction(Object context) throws ApplicationException
			{
				control.handleStore();
                	GUI.startPreviousView();
            }
		},null,true); // "true" defines this button as the default button
  }

  /**
   * @see de.willuhn.jameica.gui.AbstractView#unbind()
   */
  public void unbind() throws ApplicationException
  {
  }

}


/**********************************************************************
 * $Log: TaskDetail.java,v $
 * Revision 1.2  2006/11/20 19:08:00  pallo
 * renamed some files from prioject to Referencekonten and some translations form english to german
 *
 * Revision 1.1.1.1  2006/11/20 17:48:47  pallo
 * no message
 *
 **********************************************************************/