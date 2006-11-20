/**********************************************************************
 * $Source: /home/xubuntu/berlios_backup/github/tmp-cvs/kontoplaner/Repository/kontoplaner/src/de/pallo/jameica/kontoplaner/rmi/Referencekonto.java,v $
 * $Revision: 1.1 $
 * $Date: 2006/11/20 17:48:47 $
 * $Author: pallo $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/

package de.pallo.jameica.kontoplaner.rmi;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.datasource.rmi.DBObject;


/**
 * Interface of the business object for projects.
 * According to the SQL table, we define some getter an setter here.
 * <pre>
 * CREATE TABLE project (
 *   id NUMERIC default UNIQUEKEY('project'),
 *   name varchar(255) NOT NULL,
 *   description text NOT NULL,
 *   email varchar(255) NOT NULL,
 *   price double,
 *   startdate date,
 *   enddate date,
 *   UNIQUE (id),
 *   PRIMARY KEY (id)
 * );
 * </pre>
 * <br>Getters and setters for the primary key (id) are not needed.
 * Every one of the following methods has to throw a RemoteException.
 * <br>
 * 
 * #############################################################################
 * # IMPORTANT:                                                                #
 * # All business objects are RMI objects. So you have to run the              #
 * # rmi compiler (rmic) to create the needed stubs.                           #
 * # Whitout that you will get a                                               #
 * # java.lang.reflect.InvocationTargetException                               #
 * # (caused by java.rmi.StubNotFoundException)                                #
 * #############################################################################
 * 
 * @author willuhn
 */
public interface Referencekonto extends DBObject
{

 /**
	 * Liefert die Kontonummer fuer diese Bankverbindung.
	 *
	 * @return Kontonummer.
	 * @throws RemoteException
	 */
	public String getKontonummer() throws RemoteException;

	/**
	 * Liefert die Bankleitzahl fuer diese Bankverbindung.
	 *
	 * @return Bankleitzahl.
	 * @throws RemoteException
	 */
	public String getBLZ() throws RemoteException;

	/**
	 * Liefert den Namen des Konto-Inhabers.
	 *
	 * @return Name des Konto-Inhabers.
	 * @throws RemoteException
	 */
	public String getName() throws RemoteException;

	/**
	 * Liefert eine ausfuehrliche Bezeichnung des Kontos bestehend aus
	 * Bezeichnung, Kto und BLZ.
	 *
	 * @return ausfuehrliche Bezeichnung.
	 * @throws RemoteException
	 */
	public String getLongName() throws RemoteException;

	/**
	 * Liefert die Bezeichnung des Kontos.
	 *
	 * @return Bezeichnung des Kontos.
	 * @throws RemoteException
	 */
	public String getBezeichnung() throws RemoteException;


	/**
	 * Liefert die Waehrungs-Bezeichnung der Bankverbindung.
	 *
	 * @return Waehrungsbezeichnung.
	 * @throws RemoteException
	 */
	public String getWaehrung() throws RemoteException;

	/**
	 * Speichert die Kontonummer der Bankverbindung.
	 *
	 * @param kontonummer
	 *          Kontonummer.
	 * @throws RemoteException
	 */
	public void setKontonummer(String kontonummer) throws RemoteException;

	/**
	 * Speichert die Bankleitzahl der Bankverbindung.
	 *
	 * @param blz
	 *          Bankleitzahl.
	 * @throws RemoteException
	 */
	public void setBLZ(String blz) throws RemoteException;

	/**
	 * Speichert den Namen des Konto-Inhabers.
	 *
	 * @param name
	 *          Name des Konto-Inhaber.s
	 * @throws RemoteException
	 */
	public void setName(String name) throws RemoteException;

	/**
	 * Speichert die Bezeichnung des Kontos.
	 *
	 * @param bezeichnung
	 *          Bezeichnung.
	 * @throws RemoteException
	 */
	public void setBezeichnung(String bezeichnung) throws RemoteException;

	/**
	 * Speichert die Waehrungsbezeichnung.
	 *
	 * @param waehrung
	 *          Bezeichnung.
	 * @throws RemoteException
	 */
	public void setWaehrung(String waehrung) throws RemoteException;


    public double getSaldoAvailable() throws RemoteException;

    /**
	 * Liefert den Saldo des Kontos oder <code>0.0</code> wenn er noch nie
	 * abgefragt wurde.
	 *
	 * @return Saldo des Kontos.
	 * @throws RemoteException
	 */
	public double getSaldo() throws RemoteException;

	/**
	 * Speichert den neuen Saldo.
	 *
	 * @param saldo
	 *          Neuer Saldo.
	 * @throws RemoteException
	 */
	public void setSaldo(double saldo) throws RemoteException;

	/**
	 * Liefert das Datum des aktuellen Saldos oder <code>null</code> wenn er
	 * noch nie abgefragt wurde.
	 *
	 * @return Datum des Saldos.
	 * @throws RemoteException
	 */
	public Date getSaldoDatum() throws RemoteException;

    public void setSaldoDatum(Date date) throws RemoteException;

	public DBIterator getTasks() throws RemoteException;

}
