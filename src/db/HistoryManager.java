package db;


import utilities.DrugActions;
import utilities.ToolActions;

public class HistoryManager {
    private java.sql.Connection connection;

    public HistoryManager(java.sql.Connection connection) {
        this.connection = connection;
    }

    public void writeDrugAction(DrugActions action, String message) {
        // TODO implement here
    }

    public void writeToolAction(ToolActions action, String message) {
        // TODO implement here
    }

    public void getDrugHistory() {
        // TODO implement here
    }

    public void getToolHistory() {
        // TODO implement here
    }

}