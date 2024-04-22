package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DrugDB_Manager {

    private java.sql.Connection connection;


    public DrugDB_Manager(java.sql.Connection connection) {
        this.connection = connection;
    }

    public List<List<Object>> getDrugList() {
        List<List<Object>> drugList = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT id, name, quantity, expirationDate, temperature FROM Drugs");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                List<Object> drugInfo = new ArrayList<>();
                drugInfo.add(rs.getInt("id"));
                drugInfo.add(rs.getString("name"));
                drugInfo.add(rs.getInt("quantity"));
                drugInfo.add(rs.getString("expirationDate"));
                drugInfo.add(rs.getDouble("temperature"));
                drugList.add(drugInfo);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return drugList;
    }

    public void addDrug(String name, int quantity, String expirationDate, double temperature) {
        try {
            String sql = "INSERT INTO Drugs (name, quantity, expirationDate, temperature) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setString(3, expirationDate);
            preparedStatement.setDouble(4, temperature);

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("Erreur SQL: " + e.getMessage());
        }
    }

    public void removeDrug(int id, int quantity) {
        try {
            // Récupérer la quantité actuelle du médicament
            String selectQuantitySQL = "SELECT quantity FROM Drugs WHERE id = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuantitySQL);
            selectStatement.setInt(1, id);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int currentQuantity = resultSet.getInt("quantity");
                int newQuantity = currentQuantity - quantity;

                if (newQuantity <= 0) {
                    // Supprimer la ligne si la nouvelle quantité est nulle ou négative
                    String deleteSQL = "DELETE FROM Drugs WHERE id = ?";
                    PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL);
                    deleteStatement.setInt(1, id);
                    deleteStatement.executeUpdate();
                } else {
                    // Mettre à jour la quantité du médicament
                    String updateQuantitySQL = "UPDATE Drugs SET quantity = ? WHERE id = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateQuantitySQL);
                    updateStatement.setInt(1, newQuantity);
                    updateStatement.setInt(2, id);
                    updateStatement.executeUpdate();
                    System.out.println("Quantité du médicament mise à jour avec succès.");
                }
            }

            resultSet.close();
            selectStatement.close();
        } catch (SQLException e) {
            System.err.println("SQL ERROR: " + e.getMessage());
        }
    }

    public void modifyDrug(int oldMedId, String newName, int newQuantity, String newExpirationDate, double newTemperature) {
        try {

            // Vérifier si le médicament avec l'ancien ID existe
            String selectSQL = "SELECT * FROM Drugs WHERE id = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
            selectStatement.setInt(1, oldMedId);
            if (!selectStatement.executeQuery().next()) {
                return;
            }

            // Mettre à jour les informations du médicament
            String updateSQL = "UPDATE Drugs SET name = ?, quantity = ?, expirationDate = ?, temperature = ? WHERE id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateSQL);
            updateStatement.setString(1, newName);
            updateStatement.setInt(2, newQuantity);
            updateStatement.setString(3, newExpirationDate);
            updateStatement.setDouble(4, newTemperature);
            updateStatement.setInt(5, oldMedId);
            updateStatement.executeUpdate();

            // Fermer les ressources
            updateStatement.close();
            selectStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Erreur SQL: " + e.getMessage());
        }
    }
    
    public void modifyDrugThreshold(String name, int quantity) {
        try{
            PreparedStatement pstmt = connection.prepareStatement("UPDATE drugsThreshold SET quantity = ? WHERE name = ?");

            pstmt.setInt(1, quantity);
            pstmt.setString(2, name);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur SQL: " + e.getMessage());
        }
    }
    /**
     * @return Liste 2D des couples des noms des médicaments avec leurs seuils correspondants
     */
    public List<List<Object>> getDrugsThreshold() {
        List<List<Object>> drugThresholds = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT name, quantity FROM drugsThreshold");

            while (result.next()) {
                List<Object> row = new ArrayList<>();

                String name = result.getString("name");
                int quantity = result.getInt("quantity");

                row.add(name);
                row.add(quantity);

                drugThresholds.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL: " + e.getMessage());
        }
        return drugThresholds;
    }
}