/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/Settings.java,v $
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

package de.pallo.jameica.kontoplaner;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.DecimalFormat;

import de.willuhn.datasource.rmi.DBService;
import de.willuhn.jameica.system.Application;
import de.willuhn.jameica.hbci.HBCI;
import de.willuhn.util.I18N;


/**
 * @author pallo
 * This class holds some settings for our plugin.
 */
public class Settings
{

	private static DBService db;
	private static DBService hibiscusDb;
    private static I18N i18n;

  /**
   * Our DateFormatter.
   */
  public final static DateFormat DATEFORMAT =
    DateFormat.getDateInstance(DateFormat.DEFAULT, Application.getConfig().getLocale());
  
  /**
   * Our decimal formatter.
   */
  public final static DecimalFormat DECIMALFORMAT =
    (DecimalFormat) DecimalFormat.getInstance(Application.getConfig().getLocale());

  /**
   * Our currency name.
   */
  public final static String CURRENCY = "EUR";

	static
	{
		DECIMALFORMAT.setMinimumFractionDigits(2);
		DECIMALFORMAT.setMaximumFractionDigits(2);
	}

	/**
	 * Small helper function to get the database service.
   * @return db service.
   * @throws RemoteException
   */
  public static DBService getDBService() throws RemoteException
	{
		if (db != null)
			return db;

		try
		{
			// We have to ask Jameica's ServiceFactory.
			// If we are running in Client/Server mode and we are the
			// client, the factory returns the remote dbService from the
			// Jameica server.
			// The name and class of the service is defined in plugin.xml
			db = (DBService) Application.getServiceFactory().lookup(KontoplanerPlugin.class,"kontoplanerdb");
			return db;
		}
		catch (Exception e)
		{
			throw new RemoteException("error while getting database service",e);
		}
	}
	/**
	 * Small helper function to get the database service.
   * @return db service.
   * @throws RemoteException
   */
  public static DBService getHibiscusDBService() throws RemoteException
	{
		if (hibiscusDb != null)
			return hibiscusDb;

		try
		{
			// We have to ask Jameica's ServiceFactory.
			// If we are running in Client/Server mode and we are the
			// client, the factory returns the remote dbService from the
			// Jameica server.
			// The name and class of the service is defined in plugin.xml
			hibiscusDb = (DBService) Application.getServiceFactory().lookup(HBCI.class,"database");
			return hibiscusDb;
		}
		catch (Exception e)
		{
			throw new RemoteException("error while getting database service",e);
		}
	}
	/**
	 * Small helper function to get the translator.
   * @return translator.
   */
  public static I18N i18n()
	{
		if (i18n != null)
			return i18n;
		i18n = Application.getPluginLoader().getPlugin(KontoplanerPlugin.class).getResources().getI18N();
		return i18n; 
	}
  
}


/**********************************************************************
 * $Log: Settings.java,v $
 * Revision 1.2  2006/11/20 20:05:32  pallo
 * changed author
 *
 * Revision 1.1.1.1  2006/11/20 17:48:44  pallo
 * no message
 *
 **********************************************************************/