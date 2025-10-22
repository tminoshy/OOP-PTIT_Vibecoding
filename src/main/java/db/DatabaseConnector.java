package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A utility class to manage the connection to the MySQL database.
 * This class uses the Singleton pattern to ensure only one connection
 * URL/User/Password is used, but it returns a new connection instance each time.
 */
public class DatabaseConnector {

    // The JDBC URL for the MySQL database.
    // "quanlysinhvien" is the database name.
    // "useSSL=false" and "serverTimezone" are often needed for compatibility.
    private static final String URL = "jdbc:mysql://localhost:3306/quanlysinhvien?useSSL=false&serverTimezone=UTC";

    // Your MySQL database username (default is often "root")
    private static final String USER = "root";

    // Your MySQL database password (CHANGE THIS to your password)
    private static final String PASSWORD = "root"; // <-- *** CHANGE THIS ***

    /**
     * Attempts to establish a connection to the database.
     *
     * @return A Connection object.
     * @throws SQLException if a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        // No need to explicitly load the driver for modern JDBC (versions 4.0+)
        // Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // You can add a main method to test the connection independently.
    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            if (conn != null) {
                System.out.println("Connection successful!");
                System.out.println("Database Product Name: " + conn.getMetaData().getDatabaseProductName());
                System.out.println("Database Product Version: " + conn.getMetaData().getDatabaseProductVersion());
                conn.close();
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.println("Connection failed! Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}