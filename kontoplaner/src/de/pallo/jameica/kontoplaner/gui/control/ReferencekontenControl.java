/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/gui/control/ReferencekontenControl.java,v $
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

package de.pallo.jameica.kontoplaner.gui.control;

import java.rmi.RemoteException;

import de.willuhn.datasource.GenericIterator;
import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.datasource.rmi.DBService;
import de.pallo.jameica.kontoplaner.Settings;
import de.pallo.jameica.kontoplaner.util.Round;
import de.pallo.jameica.kontoplaner.gui.action.TaskDetail;
import de.pallo.jameica.kontoplaner.gui.menu.ReferencekontoListMenu;
import de.pallo.jameica.kontoplaner.gui.menu.TaskListMenu;
import de.pallo.jameica.kontoplaner.rmi.Referencekonto;
import de.pallo.jameica.kontoplaner.rmi.Task;
import de.willuhn.jameica.gui.AbstractControl;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.Part;
import de.willuhn.jameica.gui.formatter.CurrencyFormatter;
import de.willuhn.jameica.gui.formatter.DateFormatter;
import de.willuhn.jameica.gui.formatter.TableFormatter;
import de.willuhn.jameica.gui.input.Input;
import de.willuhn.jameica.gui.input.LabelInput;
import de.willuhn.jameica.gui.parts.ContextMenuItem;
import de.willuhn.jameica.gui.parts.TablePart;
import de.willuhn.jameica.gui.parts.TableChangeListener;
import de.willuhn.util.ApplicationException;
import de.willuhn.logging.Logger;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.graphics.Color;


/**
 * @author willuhn
 */
public class ReferencekontenControl extends AbstractControl
{

  // list of all referencekonten
  private TablePart referencekontenList;

  // Input fields for the referencekonto attributes,
  private Input name;
  private Input bezeichnung;
  private Input kontonummer;
  private Input blz;
  private Input saldo;
  private Input saldodatum;
    private Input saldoavailable;

    // list of tasks contained in this referencekonto
    private TablePart taskList;

  // this is the currently opened konto
  private Referencekonto referencekonto;

  /**
   * ct.
     * @param view this is our view (the welcome screen).
     */
    public ReferencekontenControl(AbstractView view)
    {
        super(view);
    }

  /**
   * Small helper method to get the current referencekonto.
   * @return Referencekonto
   */
  private Referencekonto getReferencekonto()
  {
    if (referencekonto != null)
      return referencekonto;
    referencekonto = (Referencekonto) getCurrentObject();
    return referencekonto;
  }

  /**
   * Returns the input field for the referencekonto name.
   * @return input field.
   * @throws RemoteException
   */
  public Input getName() throws RemoteException
  {
    if (name != null)
      return name;
    // "255" is the maximum length for this input field.
    name = new LabelInput(getReferencekonto().getName());
    return name;
  }

  /**
   * Returns the input field for the referencekonto description.
   * @return input field.
   * @throws RemoteException
   */
  public Input getBezeichnung() throws RemoteException
  {
    if (bezeichnung != null)
      return bezeichnung;
    bezeichnung = new LabelInput(getReferencekonto().getBezeichnung());
    return bezeichnung;
  }

  /**
   * Returns the input field for the referencekonto email.
   * @return input field.
   * @throws RemoteException
   */
  public Input getKontonummer() throws RemoteException
  {
    if (kontonummer != null)
      return kontonummer;
    kontonummer = new LabelInput(getReferencekonto().getKontonummer());
    return kontonummer;
  }
/**
   * Returns the input field for the referencekonto email.
   * @return input field.
   * @throws RemoteException
   */
  public Input getBLZ() throws RemoteException
  {
    if (blz != null)
      return blz;
    blz = new LabelInput(getReferencekonto().getBLZ());
    return blz;
  }
    /**
     * Returns a text label that contains the summary of all efforts in this referencekonto.
   * @return label.
   * @throws RemoteException
   */
  public Input getSaldo() throws RemoteException
    {
        if (saldo != null)
            return saldo;

        double sum = getReferencekonto().getSaldo();

        saldo = new LabelInput(Settings.DECIMALFORMAT.format(sum));
        if (getReferencekonto().getSaldoDatum() != null) {
            saldo.setComment(" [" +
                Settings.DATEFORMAT.format(getReferencekonto().getSaldoDatum()) + "]");
        }

        return saldo;
    }

 public Input getSaldoAvailable() throws RemoteException
    {
        if (saldoavailable != null)
            return saldoavailable;

        double sum = getReferencekonto().getSaldoAvailable();
        double saldoDiff = Round.round(getReferencekonto().getSaldo()-sum);
        saldoavailable = new LabelInput(Settings.DECIMALFORMAT.format(sum));
        if (getReferencekonto().getSaldoDatum() != null) {
            saldoavailable.setComment("reserviert "+saldoDiff+" [" +
                Settings.DATEFORMAT.format(getReferencekonto().getSaldoDatum()) + "]");
        }

        return saldoavailable;
    }

  /**
   * Creates a table containing all projects.
   * @return a table with projects.
   * @throws RemoteException
   */
  public Part getReferencekontoTable() throws RemoteException
  {
      // do we have an allready created table?
      if (referencekontenList != null)
          return referencekontenList;

      // 1) get the dataservice
      DBService service = Settings.getDBService();

      // 2) now we can create the referencekonto list.
      //    We do not need to specify the implementing class for
      //    the interface "Project". Jameicas Classloader knows
      //    all classes an finds the right implementation automatically. ;)
      DBIterator projects = service.createList(Referencekonto.class);

      // 4) create the table
      referencekontenList = new TablePart(projects,new de.pallo.jameica.kontoplaner.gui.action.ReferencekontoDetail());

      // 5) now we have to add some columns.
      referencekontenList.addColumn(Settings.i18n().tr("Bezeichnung"),"bezeichnung"); // "name" is the field name from the sql table.
      referencekontenList.addColumn(Settings.i18n().tr("Name"),"name"); // "name" is the field name from the sql table.

      // 6) the following fields are a date fields. So we add a date formatter.
      referencekontenList.addColumn(Settings.i18n().tr("Konto"),"kontonummer");
      referencekontenList.addColumn(Settings.i18n().tr("BLZ"),"blz");

      // 7) calculated referencekonto price (price per hour * hours)
      referencekontenList.addColumn(Settings.i18n().tr("Saldo"),"saldo", new CurrencyFormatter(Settings.CURRENCY,Settings.DECIMALFORMAT));
      referencekontenList.addColumn(Settings.i18n().tr("Saldodatum"),"saldo_datum",new DateFormatter(Settings.DATEFORMAT));


      // 8) we are adding a context menu
      referencekontenList.setContextMenu(new ReferencekontoListMenu());
      return referencekontenList;
  }

    /**
     * Returns a list of tasks in this referencekonto.
   * @return list of tasks in this referencekonto
   * @throws RemoteException
   */
  public Part getTaskList() throws RemoteException
    {
        if (taskList != null)
            return taskList;

        GenericIterator tasks = getReferencekonto().getTasks();
        taskList = new TablePart(tasks,new TaskDetail());
        taskList.setFormatter(new TableFormatter() {

            public void format(TableItem tableItem) {
                // format sum red for items which are producing minus saldo
                double tempSum = 0;
                if (tableItem.getParent() != null) {
                    Table table = tableItem.getParent();
                    for (int i =0; i< table.getItemCount();i++) {
                        TableItem cTableItem = table.getItem(i);
                        try {
                            tempSum = tempSum + ((Task)cTableItem.getData()).getAktuelleSumme();

                        } catch (RemoteException re) {
                            Logger.error("unable to get summe",re);
                        }
                        if (cTableItem == tableItem) {
                            break;
                        }
                    }
                }
                try {
                    if (tempSum > getReferencekonto().getSaldo())
                        tableItem.setBackground(1,new Color(null, 255,50,0));
                } catch (RemoteException re) {
                 Logger.error("unable to compare sums",re);
                }
            }
        });
        taskList.addColumn(Settings.i18n().tr("Task name"),"name");
        taskList.addColumn(Settings.i18n().tr("Aktuell"),"aktuellesumme");
        taskList.addColumn(Settings.i18n().tr("Zielsumme"), "summe");
        taskList.addColumn(Settings.i18n().tr("Start"),"periodenstartdatum",new DateFormatter(Settings.DATEFORMAT));
        taskList.addColumn(Settings.i18n().tr("Ende"),"periodenendedatum",new DateFormatter(Settings.DATEFORMAT));
        taskList.addColumn(Settings.i18n().tr("Intervall"),"intervall");
        taskList.addColumn(Settings.i18n().tr("Periodik"),"periodik");
        taskList.addColumn(Settings.i18n().tr("Typ"),"typ");
        taskList.addColumn(Settings.i18n().tr("Priorität"),"prioritaet");
        TaskListMenu tlm = new TaskListMenu();

        // we add an additional menu item to create tasks with predefined referencekonto.
        tlm.addItem(new ContextMenuItem(Settings.i18n().tr("Create new task within this Project"),new Action()
    {
      public void handleAction(Object context) throws ApplicationException
      {
          new TaskDetail().handleAction(getReferencekonto());
      }
    }));
    taskList.setContextMenu(tlm);
    taskList.setSummary(false);
    return taskList;
    }

  /**
   * This method stores the referencekonto using the current values.
   */
  public void handleStore()
  {

  }
}


/**********************************************************************
 * $Log: ReferencekontenControl.java,v $
 * Revision 1.1  2006/11/20 17:48:46  pallo
 * Initial revision
 *
 **********************************************************************/