package db;

import java.sql.*;

import Beans.UserBean;

public class DatabaseHandler {
    private static final String DB_URL = "jdbc:derby:\\usersdb;create=true";
    private static final String DB_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
  

    static {
        try {
            Class.forName(DB_DRIVER);  
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Failed to load database driver.");
        }
    }


    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
    
    
    private static void createUserTable() {
        String sql = "CREATE TABLE Users ("
                + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                + "username VARCHAR(255) NOT NULL UNIQUE, "
                + "password VARCHAR(255) NOT NULL, "
                + "firstName VARCHAR(255), "
                + "lastName VARCHAR(255), "
                + "streetName VARCHAR(255), "
                + "streetNumber INT, "
                + "postalCode VARCHAR(10), "
                + "city VARCHAR(255), "
                + "country VARCHAR(255))";

        try (Connection conn = getConnection(); Statement statement = conn.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            if ("X0Y32".equals(e.getSQLState())) {
                // Table already exists
               // System.out.println("Users table already exists.");
            } else {
                e.printStackTrace();
            }
        }
    }
    
    static {
        createUserTable();
    }

    public static int getUserIdByUsername(String username) {
        String query = "SELECT id FROM Users WHERE username = ?";
    
        try (Connection conn = DatabaseHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            // Set the username parameter in the query
            stmt.setString(1, username);
    
            // Execute the query
            ResultSet rs = stmt.executeQuery();
    
            // If a result is found, return the user ID
            while (rs.next()) {
                return rs.getInt("id");  // Return the ID of the user
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Return -1 if no user with the provided username was found
        return -1;
    }
    
    
    public static boolean usernameExists(String username) {
        String query = "SELECT COUNT(*) FROM Users WHERE username = ?";
        try (Connection conn = DatabaseHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean insertUserRecord(String username, String password, String firstName, String lastName,
                                           String streetName, String streetNumber, String postalCode, String city, String country) {
        String query = "INSERT INTO Users (username, password, firstName, lastName, streetName, streetNumber, postalCode, city, country) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, firstName);
            stmt.setString(4, lastName);
            stmt.setString(5, streetName);
            stmt.setInt(6, Integer.parseInt(streetNumber)); 
            stmt.setString(7, postalCode);
            stmt.setString(8, city);
            stmt.setString(9, country);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (NumberFormatException e) {
            System.out.println("Invalid street number format.");
            return false;
        }
    }
  
    public static boolean validateLogin(String username, String password) {
        String query = "SELECT COUNT(*) FROM Users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static UserBean getUserInfo(String username) {
        String query = "SELECT * FROM Users WHERE username = ?";
        UserBean user = null;
        
        try (Connection conn = DatabaseHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new UserBean();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setStreetName(rs.getString("streetName"));
                user.setStreetNumber(rs.getString("streetNumber"));
                user.setPostalCode(rs.getString("postalCode"));
                user.setCity(rs.getString("city"));
                user.setCountry(rs.getString("country"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return user;
    }
    
    
}
