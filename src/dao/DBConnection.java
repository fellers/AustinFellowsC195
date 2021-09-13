/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class used to establish connection to the SQL database.
 * @author indya
 */
public class DBConnection {
    //JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ073Wm";    
    
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;
    
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    
    private static final String username = "U073Wm";
    private static final String password = "53688941987";
    private static Connection conn = null;
    
    /**
     * Initializes the database connection.
     */
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }
    
    /**
     * Closes the connection with the database.
     */
    public static void closeConnection() {
        try{
            conn.close();
        } catch (Exception e) {
            
        }
    }
    
    /**
     * Returns connection object.
     */
    public static Connection getConnection() {
        return conn;
    }
}
