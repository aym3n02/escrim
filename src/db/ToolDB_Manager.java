package db;
import java.util.List;
import utilities.Tool;

public class ToolDB_Manager {

    private java.sql.Connection connection;

    public ToolDB_Manager(java.sql.Connection connection) {
        this.connection = connection;
    }

    public List<Tool> getToolList() {
        // TODO implement here
        return null;
    }

    public void addTool(String newName, int quantity) {
        // TODO implement here
    }

    public void modifyTool(int oldMedId, String newName, int quantity) {
        // TODO implement here
    }

    public void removeTool(int id, int quantity) {
        // TODO implement here
    }

    public void modifyToolThreshold(String name, int quantity) {
        // TODO implement here
    }

    public List<String> getToolsUnderThreshold() {
        // TODO implement here
        return null;
    }

}