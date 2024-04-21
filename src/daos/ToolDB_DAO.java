package daos;


import db.HistoryManager;
import db.ToolDB_Manager;
import utilities.Tool;
import utilities.ToolActions;

import java.util.*;

public class ToolDB_DAO {

    private HistoryManager historyManager;
    private ToolDB_Manager manager;

    public ToolDB_DAO(java.sql.Connection connection, HistoryManager historyManager) {
        this.historyManager = historyManager;
        manager= new ToolDB_Manager(connection);
    }

    public List<Tool> getToolList() {
        return manager.getToolList();
    }

    public void addTool(Tool tool) {
        manager.addTool(tool.getName(), tool.quantity);
        historyManager.writeToolAction(ToolActions.Addition,"Ajout d'une quantité de "+tool.quantity+" de l'outil "+tool.getName()+".");
    }

    public void removeTool(int id, int quantity) {
        manager.removeTool(id, quantity);
        historyManager.writeToolAction(ToolActions.Removal,"Retrait d'une quantité de "+quantity+" de l'outil d'ID: "+id+".");
    }

    public void modifyTool(int oldToolId, Tool newTool) {
        manager.modifyTool(oldToolId,newTool.getName(),newTool.quantity);
        historyManager.writeToolAction(ToolActions.Modification,"Modification des information de l'outil d'ID "+oldToolId+" .");

    }

    public void modifyToolThreshold(String name, int quantity) {
        manager.modifyToolThreshold(name, quantity);

    }

    public List<String> getToolsUnderThreshold() {
        return manager.getToolsUnderThreshold();
    }

}