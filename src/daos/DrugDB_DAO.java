package daos;


import db.DrugDB_Manager;
import db.HistoryManager;

import utilities.Drug;
import utilities.DrugActions;

import java.util.*;

public class DrugDB_DAO {

    private HistoryManager historyManager;

    private DrugDB_Manager manager;

    public DrugDB_DAO(java.sql.Connection connection, HistoryManager historyManager) {
        this.historyManager = historyManager;
        this.manager = new DrugDB_Manager(connection);
    }

    public List<Drug> getDrugList() {
        return manager.getDrugList();
    }

    public void addDrug(Drug med)  {
        manager.addDrug(med.getName(), med.quantity, med.expirationDate, med.temperature);
        historyManager.writeDrugAction(DrugActions.Addition,"Ajout d'une quantité de "+med.quantity+" du médicament "+med.getName()+".");
    }

    public void removeDrug(int id, int quantity) {
        manager.removeDrug(id, quantity);

        historyManager.writeDrugAction(DrugActions.Removal,"Retrait d'une quantité de "+quantity+" du médicament d'ID: "+id+".");
    }

    public void modifyDrug(int oldMedId, Drug newMed) {
        manager.modifyDrug(oldMedId, newMed.getName(),newMed.quantity,newMed.expirationDate,newMed.temperature);
        historyManager.writeDrugAction(DrugActions.Modification,"Modification des information du médicament d'ID "+oldMedId+" .");
    }

    public void modifyDrugThreshold(String name, int quantity) {
        manager.modifyDrugThreshold(name, quantity);
    }

    /**
     * @return retourne une liste des noms des médicaments sous seuil
     */
    public List<List<Object>> getDrugsUnderThreshold() {
        return manager.getDrugsUnderThreshold();
    }

}