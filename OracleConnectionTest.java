import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleConnectionTest {
    public static void main(String[] args) {
        // JDBC URL, username, and password
        String url = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";
        String user = "system";
        String password = "pranit123";

        Connection connection = null;
        Statement statement = null;

        try {
            // Load the Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Establish connection
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to Oracle database!");
            
            statement = connection.createStatement();
            statement.executeUpdate("create table emp2(eno number,esal number )");
            System.out.println("Table created");

        } catch (ClassNotFoundException e) {
            System.err.println("Error: Oracle JDBC driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error connecting to Oracle database: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources in finally block
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
