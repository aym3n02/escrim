package daos;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;


import db.*;

public class DB_DAO {

    // DAOs
    private DrugDB_DAO drugDAO;
    private ToolDB_DAO toolDAO;

    private HistoryManager historyManager;
    private Connection connection;

    public DB_DAO() {
        try (Connection connection = DriverManager
                .getConnection("jdbc:sqlite:C:/Users/Aym3n/Documents/projet scrim/code 0/escrim/escrim.db")) {

            this.connection = connection;

            this.historyManager = new HistoryManager(connection);
            this.drugDAO = new DrugDB_DAO(connection, historyManager);
            this.toolDAO = new ToolDB_DAO(connection, historyManager);

            initializeDB(connection);

        } catch (SQLException e) {
            System.out.println("Failed to connect to SQLite database: " + e.getMessage());
        }
    }

    @Override
    protected void finalize() throws Throwable {
        connection.close();
    }

    public DrugDB_DAO getDrugDAO() {
        return drugDAO;
    }

    public ToolDB_DAO getToolDAO() {
        return toolDAO;
    }

    private void initializeDB(Connection connection) {
        try {

            Statement statement = connection.createStatement();

            String createDrugsTableSQL = "CREATE TABLE IF NOT EXISTS Drugs (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "quantity INTEGER," +
                    "expirationDate TEXT," +
                    "temperature REAL" +
                    ")";
            statement.executeUpdate(createDrugsTableSQL);

            String createToolsTableSQL = "CREATE TABLE IF NOT EXISTS Tools (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "quantity INTEGER" +
                    ")";
            statement.executeUpdate(createToolsTableSQL);

            String createDrugsHistoryTableSQL = "CREATE TABLE IF NOT EXISTS drugsHistory (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "action TEXT," +
                    "information TEXT" +
                    ")";
            statement.executeUpdate(createDrugsHistoryTableSQL);

            // Create toolHistory table
            String createToolHistoryTableSQL = "CREATE TABLE IF NOT EXISTS toolHistory (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "action TEXT," +
                    "information TEXT" +
                    ")";
            statement.executeUpdate(createToolHistoryTableSQL);

            String createDrugsThresholdTableSQL = "CREATE TABLE IF NOT EXISTS drugsThreshold (" +
                    "name TEXT PRIMARY KEY," +
                    "quantity INTEGER" +
                    ")";
            statement.executeUpdate(createDrugsThresholdTableSQL);

            String createToolsThresholdTableSQL = "CREATE TABLE IF NOT EXISTS toolsThreshold (" +
                    "name TEXT PRIMARY KEY," +
                    "quantity INTEGER" +
                    ")";
            statement.executeUpdate(createToolsThresholdTableSQL);

            statement.close();
        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
        }
    }
}