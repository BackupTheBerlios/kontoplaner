package de.pallo.jameica.kontoplaner.gui.action;

import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.pallo.jameica.kontoplaner.Settings;
import de.pallo.jameica.kontoplaner.rmi.Referencekonto;
import de.willuhn.jameica.hbci.rmi.Konto;
import de.willuhn.util.ApplicationException;
import de.willuhn.datasource.rmi.DBService;
import de.willuhn.datasource.rmi.DBIterator;
import de.willuhn.logging.Logger;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: entwicklung
 * Date: 16.11.2006
 * Time: 14:05:50
 * To change this template use File | Settings | File Templates.
 */
public class SyncKonten implements Action {
    public void handleAction(Object object) throws ApplicationException {
        try {
            DBService hibiscusService = Settings.getHibiscusDBService();
            Logger.debug("retrieving hibiscus Konto list");
            DBIterator i = hibiscusService.createList(Konto.class);
            HashMap<String, Konto> hibiscusKontenMap = new HashMap();
            while (i.hasNext()) {
                Konto konto = (Konto) i.next();
                hibiscusKontenMap.put(konto.getKontonummer(), konto);
            }
            DBService dbService = Settings.getDBService();
            Logger.debug("retrieving my Konto list");
            i = dbService.createList(Referencekonto.class);
            HashMap<String, Referencekonto> myKontenMap = new HashMap();
            while (i.hasNext()) {
                Referencekonto konto = (Referencekonto) i.next();
                myKontenMap.put(konto.getKontonummer(), konto);
            }
            Set<String> createSet = new HashSet(hibiscusKontenMap.keySet());
            createSet.removeAll(myKontenMap.keySet());
            for (String key : createSet) {
                Konto konto = hibiscusKontenMap.get(key);
                Referencekonto rKonto = (Referencekonto) dbService.createObject(Referencekonto.class,null);
                Logger.debug("create Konto "+key);
                syncKonto(rKonto, konto);
            }
            Set<String> updateSet = new HashSet(myKontenMap.keySet());
            updateSet.retainAll(hibiscusKontenMap.keySet());
            for (String key : updateSet) {
                Konto konto = hibiscusKontenMap.get(key);
                Referencekonto rKonto = myKontenMap.get(key);
                Logger.debug("update Konto "+key);
                syncKonto(rKonto, konto);
            }
            GUI.getStatusBar().setSuccessText(Settings.i18n().tr("Konten aktualisiert."));
            GUI.startView(de.pallo.jameica.kontoplaner.gui.view.ReferencekontenList.class.getName(),null);

        } catch (RemoteException re) {

        }
    }


    private void syncKonto(Referencekonto rkonto, Konto konto) {
        try {
            rkonto.setBezeichnung(konto.getBezeichnung());
            rkonto.setBLZ(konto.getBLZ());
            rkonto.setKontonummer(konto.getKontonummer());
            rkonto.setName(konto.getName());
            rkonto.setSaldo(konto.getSaldo());
            rkonto.setSaldoDatum(konto.getSaldoDatum());
            rkonto.setWaehrung(konto.getWaehrung());
            rkonto.store();
        } catch (Exception e) {
            Logger.error("unable to sync konto ",e);
        }
    }
}
