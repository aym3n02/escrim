package db;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ToolDB_Manager {

    private java.sql.Connection connection;

    public ToolDB_Manager(java.sql.Connection connection) {
        this.connection = connection;
    }

    public List<List<Object>> getToolList() {
        List<List<Object>> toolList = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT rowid, name, quantity FROM toolsThreshold");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                List<Object> tool = new ArrayList<>();
                tool.add(rs.getInt("rowid"));
                tool.add(rs.getString("name"));
                tool.add(rs.getInt("quantity"));
                toolList.add(tool);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return toolList;
    }

    public void addTool(String newName, int quantity) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO toolsThreshold(name, quantity) VALUES(?, ?)");
            pstmt.setString(1, newName);
            pstmt.setInt(2, quantity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modifyTool(int oldMedId, String newName, int quantity) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("UPDATE toolsThreshold SET name = ?, quantity = ? WHERE rowid = ?");
            pstmt.setString(1, newName);
            pstmt.setInt(2, quantity);
            pstmt.setInt(3, oldMedId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeTool(int id, int quantity) {
        try {
            PreparedStatement checkStmt = connection.prepareStatement("SELECT quantity FROM toolsThreshold WHERE rowid = ?");
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                int currentQuantity = rs.getInt("quantity");
                int newQuantity = currentQuantity - quantity;
                
                if (newQuantity <= 0) {
                    // Remove the tool if the new quantity is zero or less
                    PreparedStatement removeStmt = connection.prepareStatement("DELETE FROM toolsThreshold WHERE rowid = ?");
                    removeStmt.setInt(1, id);
                    removeStmt.executeUpdate();
                } else {
                    // Update the quantity otherwise
                    PreparedStatement updateStmt = connection.prepareStatement("UPDATE toolsThreshold SET quantity = ? WHERE rowid = ?");
                    updateStmt.setInt(1, newQuantity);
                    updateStmt.setInt(2, id);
                    updateStmt.executeUpdate();
                }
            } else {
                System.out.println("No tool found with the given id.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modifyToolThreshold(String name, int quantity) {
        try{
            PreparedStatement pstmt = connection.prepareStatement("UPDATE toolThreshold SET quantity = ? WHERE name = ?");

            pstmt.setInt(1, quantity);
            pstmt.setString(2, name);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur SQL: " + e.getMessage());
        }
    }

    public List<List<Object>> getToolsUnderThreshold() {
        List<List<Object>> toolThresholds = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT name, quantity FROM toolsThreshold");

            while (result.next()) {
                List<Object> row = new ArrayList<>();

                String name = result.getString("name");
                int quantity = result.getInt("quantity");

                row.add(name);
                row.add(quantity);

                toolThresholds.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL: " + e.getMessage());
        }
        return toolThresholds;
    }

}