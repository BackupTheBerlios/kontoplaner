package de.pallo.jameica.kontoplaner.server;

import de.willuhn.datasource.db.AbstractDBObject;
import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.datasource.rmi.DBService;
import de.willuhn.util.I18N;
import de.willuhn.util.ApplicationException;
import de.willuhn.jameica.system.Application;
import de.pallo.jameica.kontoplaner.rmi.Referencekonto;
import de.pallo.jameica.kontoplaner.rmi.Task;
import de.pallo.jameica.kontoplaner.KontoplanerPlugin;
import de.pallo.jameica.kontoplaner.Settings;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * Bildet eine Bankverbindung ab.
 */
public class ReferencekontoImpl extends AbstractDBObject implements Referencekonto
{

  private I18N i18n;

  /**
   * ct.
   *
   * @throws java.rmi.RemoteException
   */
  public ReferencekontoImpl() throws RemoteException
  {
    super();
    i18n = Application.getPluginLoader().getPlugin(KontoplanerPlugin.class).getResources()
        .getI18N();
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#getTableName()
   */
  protected String getTableName()
  {
    return "referencekonto";
  }

  /**
   * @see de.willuhn.datasource.GenericObject#getPrimaryAttribute()
   */
  public String getPrimaryAttribute() throws RemoteException
  {
    return "kontonummer";
  }


  /**
   * @see de.willuhn.jameica.hbci.rmi.Konto#getKontonummer()
   */
  public String getKontonummer() throws RemoteException
  {
    return (String) getAttribute("kontonummer");
  }

  /**
   * @see de.willuhn.jameica.hbci.rmi.Konto#getBLZ()
   */
  public String getBLZ() throws RemoteException
  {
    return (String) getAttribute("blz");
  }

  /**
   * @see de.willuhn.jameica.hbci.rmi.Konto#getName()
   */
  public String getName() throws RemoteException
  {
    return (String) getAttribute("name");
  }


  /**
   * @see de.willuhn.jameica.hbci.rmi.Konto#setKontonummer(java.lang.String)
   */
  public void setKontonummer(String kontonummer) throws RemoteException
  {
    setAttribute("kontonummer", kontonummer);
  }

  /**
   * @see de.willuhn.jameica.hbci.rmi.Konto#setBLZ(java.lang.String)
   */
  public void setBLZ(String blz) throws RemoteException
  {
    setAttribute("blz", blz);
  }

  /**
   * @see de.willuhn.jameica.hbci.rmi.Konto#setName(java.lang.String)
   */
  public void setName(String name) throws RemoteException
  {
    setAttribute("name", name);
  }

  /**
   * @see de.willuhn.datasource.rmi.DBObject#delete()
   */
  public void delete() throws RemoteException, ApplicationException
  {
    // Wir muessen auch alle Umsaetze, Ueberweisungen und Protokolle mitloeschen
    // da Constraints dorthin existieren.
    try
    {
      this.transactionBegin();

      // todo delete dependent records

      // Jetzt koennen wir uns selbst loeschen
      super.delete();
      this.transactionCommit();
    }
    catch (RemoteException e)
    {
      this.transactionRollback();
      throw e;
    }
    catch (ApplicationException e2)
    {
      this.transactionRollback();
      throw e2;
    }
  }

  /**
   * @see de.willuhn.jameica.hbci.rmi.Konto#getWaehrung()
   */
  public String getWaehrung() throws RemoteException
  {
    return (String) getAttribute("waehrung");
  }

  /**
   * @see de.willuhn.jameica.hbci.rmi.Konto#setWaehrung(java.lang.String)
   */
  public void setWaehrung(String waehrung) throws RemoteException
  {
    setAttribute("waehrung", waehrung);
  }

    public double getSaldoAvailable() throws RemoteException {
        double currSaldo = getSaldo();
        DBIterator ite = getTasks();
        Date refDate = new Date();
        while (ite.hasNext()) {
            Task task = (Task) ite.next();
            if (task.valid(refDate)) {
                currSaldo = currSaldo - task.getAktuelleSumme();
            }
        }
        return currSaldo;
    }

  /**
   * @see de.willuhn.jameica.hbci.rmi.Konto#getSaldo()
   */
  public double getSaldo() throws RemoteException
  {
    Double d = (Double) getAttribute("saldo");
    if (d == null)
      return 0;
    return d.doubleValue();
  }

  /**
   * @see de.willuhn.jameica.hbci.rmi.Konto#getSaldoDatum()
   */
  public Date getSaldoDatum() throws RemoteException
  {
    return (Date) getAttribute("saldo_datum");
  }

  /**
   * @see de.willuhn.jameica.example.rmi.Referencekonto#setSaldoDatum(java.util.Date)
   */
  public void setSaldoDatum(Date date) throws RemoteException
  {
    setAttribute("saldo_datum", date);
  }

      /**
     * @see de.willuhn.jameica.example.rmi.Referencekonto#getTasks()
     */
    public DBIterator getTasks() throws RemoteException
    {
    try
    {
      // 1) Get the Database Service.
      DBService service = Settings.getDBService();

      // you can get the Database Service also via:
      // DBService service = this.getService();

      // 3) We create the task list using getList(Class)
      DBIterator tasks = service.createList(Task.class);
      tasks.setOrder("ORDER BY prioritaet, name");

      // 4) we add a filter to only query for tasks with our project id
      tasks.addFilter("referencekonto_id = " + this.getID());

      return tasks;
    }
    catch (Exception e)
    {
        throw new RemoteException("unable to load task list",e);
    }
    }


  /**
   * @see de.willuhn.jameica.hbci.rmi.Konto#getBezeichnung()
   */
  public String getBezeichnung() throws RemoteException
  {
    return (String) getAttribute("bezeichnung");
  }

  /**
   * @see de.willuhn.jameica.hbci.rmi.Konto#setBezeichnung(java.lang.String)
   */
  public void setBezeichnung(String bezeichnung) throws RemoteException
  {
    setAttribute("bezeichnung", bezeichnung);
  }


  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#insert()
   */
  public void insert() throws RemoteException, ApplicationException
  {
    super.insert();
    //addToProtokoll(i18n.tr("ReferenceKonto angelegt"), Protokoll.TYP_SUCCESS);
  }

  /**
   * @see de.willuhn.datasource.rmi.DBObject#store()
   */
  public void store() throws RemoteException, ApplicationException
  {
    if (hasChanged())
    //  addToProtokoll(i18n.tr("Referencekonto-Eigenschaften aktualisiert"),
    //      Protokoll.TYP_SUCCESS);
    super.store();
  }


  /**
   * Die Funktion ueberschreiben wir um ein zusaetzliches virtuelles Attribut
   * "longname" einzufuehren. Bei Abfrage dieses Attributs wird "[Kontonummer]
   * Bezeichnung" zurueckgeliefert.
   *
   * @see de.willuhn.datasource.GenericObject#getAttribute(java.lang.String)
   */
  public Object getAttribute(String arg0) throws RemoteException
  {


    if ("longname".equals(arg0))
    {
      String bez = getBezeichnung();
      String blz = getBLZ();
      String kto = getKontonummer();


      if (bez != null && bez.length() > 0)
        return i18n.tr("{0}, Kto. {1} [{2}]", new String[] { bez, kto, blz });
      return i18n.tr("Kto. {0} [BLZ: {1}]", new String[] { kto, blz });
    }

    return super.getAttribute(arg0);
  }



  /**
   * @see de.willuhn.jameica.hbci.rmi.Konto#setSaldo(double)
   */
  public void setSaldo(double saldo) throws RemoteException
  {
    setAttribute("saldo", new Double(saldo));
    setAttribute("saldo_datum", new Date());
  }





  /**
   * @see de.willuhn.jameica.hbci.rmi.Konto#getLongName()
   */
  public String getLongName() throws RemoteException
  {
    return (String) getAttribute("longname");
  }

}
