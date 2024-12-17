package main.java.payment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class PaymentWriter {
    private String url = "jdbc:sqlite:";

    public void setPath(String dbPath) {
        this.url = url + dbPath + "/PaymentHistory.db";
        System.out.println(this.url);
    }

    public void write(String name, String cardnumber, String response) {
        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
    
            con = DriverManager.getConnection(url);
            
            con.setAutoCommit(false);
            
            String sql = "INSERT INTO History (NameOnCard, CardNumber, ResponseFromServer) VALUES ('" + name + "', " + cardnumber + ", '" + response + "');";
            
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
            
            con.commit();
    
            System.out.println("Data inserted successfully");
        } catch (Exception e) {
            e.printStackTrace();  
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    public void fetchAllRecords() {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection(url);

            stmt = con.createStatement();

            String sql = "SELECT * FROM History;";
            rs = stmt.executeQuery(sql);

            System.out.println("Records from History table:");
            while (rs.next()) {
                String name = rs.getString("NameOnCard");
                String cardNumber = rs.getString("CardNumber");
                String response = rs.getString("ResponseFromServer");
                System.out.println("Name: " + name + ", Card Number: " + cardNumber + ", Response: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    
}
