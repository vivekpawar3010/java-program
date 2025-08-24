import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataSaver {
    private static final String DB_URL = "jdbc:sqlserver://VIVEK\\SQLEXPRESS:1433;databaseName=datasaver;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "King@3010";

    public void saveData(String dataToSave) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish a connection to the database
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Create a SQL statement to insert data
            String sql = "INSERT INTO History (link) VALUES (?)";
            preparedStatement = connection.prepareStatement(sql);

            // Set the parameter value for the prepared statement
            preparedStatement.setString(1, dataToSave);

            // Execute the SQL statement to insert the data
            preparedStatement.executeUpdate();

            System.out.println("Data saved successfully!");
        } catch (SQLException e) {
            System.err.println("Error saving data: " + e.getMessage());
        } finally {
            // Close the resources
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        DataSaver dataSaver = new DataSaver();
        dataSaver.saveData("Data to save");
    }
}
