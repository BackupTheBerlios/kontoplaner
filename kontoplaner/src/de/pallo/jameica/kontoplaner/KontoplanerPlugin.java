/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/KontoplanerPlugin.java,v $
 * $Revision: 1.1 $
 * $Date: 2006/11/20 17:48:44 $
 * $Author: pallo $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/
package de.pallo.jameica.kontoplaner;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Locale;

import de.willuhn.datasource.db.EmbeddedDatabase;
import de.willuhn.jameica.plugin.AbstractPlugin;
import de.willuhn.jameica.plugin.PluginResources;
import de.willuhn.jameica.system.Application;
import de.willuhn.util.ApplicationException;
import de.willuhn.logging.Logger;

/**
 * You need to have at least one class wich inherits from <code>AbstractPlugin</code>.
 * If so, Jameica will detect your plugin automatically at startup.
 */
public class KontoplanerPlugin extends AbstractPlugin {

    private EmbeddedDatabase db = null;

    /**
     * constructor.
     * @param file the plugin file (your jar or the plugin directory).
     */
    public KontoplanerPlugin(File file) {
        super(file);
    }

  /**
   * This method is invoked on every startup.
   * You can make here some stuff to init your plugin.
   * If you get some errors here and you dont want to activate the plugin,
   * simply throw an ApplicationException.
   * @see de.willuhn.jameica.plugin.AbstractPlugin#init()
   */
  public void init() throws ApplicationException
  {
  }

  /**
   * This method is called only the first time, the plugin is loaded (before executing init()).
   * if your installation procedure was not successfull, throw an ApplicationException.
   * @see de.willuhn.jameica.plugin.AbstractPlugin#install()
   */
  public void install() throws ApplicationException
  {

        // If we are running in client/server mode and this instance
        // is the client, we do not need to create a database.
        // Instead of this we will get our objects via RMI from
        // the server
        if (Application.inClientMode())
            return;

    try {

            // Let's create an embedded Database
            PluginResources res = Application.getPluginLoader().getPlugin(KontoplanerPlugin.class).getResources();
            EmbeddedDatabase db = new EmbeddedDatabase(res.getWorkPath() + "/db","exampleuser","examplepassword");

            // create the sql tables.
      db.executeSQLScript(new File(res.getPath() + "/sql/create.sql"));

            // That's all. Database installed and tables created ;)
    }
    catch (Exception e)
    {
        throw new ApplicationException("error while installing plugin",e);
    }
  }

  /**
   * This method will be executed on every version change.
   * @see de.willuhn.jameica.plugin.AbstractPlugin#update(double)
   */
  public void update(double oldVersion) throws ApplicationException
  {
if (Application.inClientMode())
      return; // Kein Update im Client-Mode noetig.

        Logger.info("starting update process for kontoplaner");

        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(Locale.ENGLISH);
        df.setMaximumFractionDigits(1);
        df.setMinimumFractionDigits(1);
        df.setGroupingUsed(false);

    double newVersion = oldVersion + 0.1d;

        try
        {
      // Bevor wir irgendein Update fahren, checken wir, ob die Datenbank
      // mit der von Jameica gemeldeten Version uebereinstimmt.

//      String checkSum = getDatabase().getMD5Sum();
//      Double expectedVersion = (Double) DBMAPPING.get(checkSum);
//      if (expectedVersion == null)
//        throw new ApplicationException(getResources().getI18N().tr("Update der Datenbank abgebrochen. Aktuelle Checksumme {0} entspricht keiner bekannten Hibiscus-Version",checkSum));
//
//      double d = expectedVersion.doubleValue();
//      if (d != oldVersion)
//        throw new ApplicationException(getResources().getI18N().tr("Update der Datenbank abgebrochen. Erwartete Datenbank-Version: {0}, tatsächliche Version: {1}", new String[]{df.format(oldVersion),df.format(d)}));

      // OK, wir haben eine bekannte Version, dann koennen wir jetzt das Update starten
      File f = new File(getResources().getPath() + "/sql/update_" +
          df.format(oldVersion) + "-" +
          df.format(newVersion) + ".sql");

      Logger.info("checking sql file " + f.getAbsolutePath());
            while (f.exists())
            {
                Logger.info("  file exists, executing");
                getDatabase().executeSQLScript(f);
                oldVersion = newVersion;
                newVersion = oldVersion + 0.1d;
                f = new File(getResources().getPath() + "/sql/update_" +
                                       df.format(oldVersion) + "-" +
                                       df.format(newVersion) + ".sql");
            }
      Logger.info("Update completed");
        }
    catch (ApplicationException ae)
    {
      throw ae;
    }
        catch (Exception e)
        {
            throw new ApplicationException(getResources().getI18N().tr("Fehler beim Update der Datenbank"),e);
        }
  }
  /**
   * Liefert die Datenbank des Plugins.
   * Lauft die Anwendung im Client-Mode, wird
   * immer <code>null</code> zurueckgegeben.
   * @return die Embedded Datenbank.
   * @throws Exception
   */
  private EmbeddedDatabase getDatabase() throws Exception
  {
    if (Application.inClientMode())
      return null;
    if (db != null)
      return db;
    db = new EmbeddedDatabase(getResources().getWorkPath() + "/db","exampleuser","examplepassword");
    return db;
  }
  /**
   * Here you can do some cleanup stuff.
   * The method will be called on every clean shutdown of jameica.
   * @see de.willuhn.jameica.plugin.AbstractPlugin#shutDown()
   */
  public void shutDown()
  {
  }
}


/**********************************************************************
 * $Log: KontoplanerPlugin.java,v $
 * Revision 1.1  2006/11/20 17:48:44  pallo
 * Initial revision
 *
 **********************************************************************/