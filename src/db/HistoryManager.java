package db;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utilities.DrugActions;
import utilities.ToolActions;

public class HistoryManager {
    private java.sql.Connection connection;

    public HistoryManager(java.sql.Connection connection) {
        this.connection = connection;
    }

    public void writeDrugAction(DrugActions action, String message) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO toolHistory(action, information) VALUES(?, ?)");
            pstmt.setString(1, action.toString());
            pstmt.setString(2, message);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeToolAction(ToolActions action, String message) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO drugsHistory(action, information) VALUES(?, ?)");
            pstmt.setString(1, action.toString());
            pstmt.setString(2, message);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<String> getDrugHistory() {
        List<String> drugHistory = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT action, information FROM toolHistory");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String action = rs.getString("action");
                String information = rs.getString("information");
                String entry = action + ": " + information;
                drugHistory.add(entry);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return drugHistory;
    }

    public List<String> getToolHistory() {
        List<String> toolHistory = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT action, information FROM toolHistory");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String action = rs.getString("action");
                String information = rs.getString("information");
                String entry = action + ": " + information;
                toolHistory.add(entry);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return toolHistory;
    }

}