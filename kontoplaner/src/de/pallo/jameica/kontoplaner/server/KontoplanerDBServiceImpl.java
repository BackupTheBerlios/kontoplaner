/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/server/KontoplanerDBServiceImpl.java,v $
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
package de.pallo.jameica.kontoplaner.server;

import java.rmi.RemoteException;

import de.willuhn.datasource.db.EmbeddedDBServiceImpl;
import de.pallo.jameica.kontoplaner.KontoplanerPlugin;
import de.willuhn.jameica.system.Application;

/**
 * this is our database service which can work over RMI.
 */
public class KontoplanerDBServiceImpl extends EmbeddedDBServiceImpl
{

  /**
   * ct.
   * @throws RemoteException
   */
  public KontoplanerDBServiceImpl() throws RemoteException
  {
    super(Application.getPluginLoader().getPlugin(KontoplanerPlugin.class).getResources().getWorkPath() + "/db/db.conf",
    			"exampleuser", "examplepassword");

    // We have to define jameicas classfinder.
    // otherwise, the db service will not be able to find
    // implementors by their interfaces.  
    this.setClassFinder(Application.getClassLoader().getClassFinder());
  }

}


/**********************************************************************
 * $Log: KontoplanerDBServiceImpl.java,v $
 * Revision 1.2  2006/11/20 20:05:32  pallo
 * changed author
 *
 * Revision 1.1.1.1  2006/11/20 17:48:47  pallo
 * no message
 *
 **********************************************************************/