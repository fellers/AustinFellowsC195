/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

/**
 * Class for performing database transactions on the customer table.
 * @author indya
 */
public class DBCustomers {
    
    /**
     * Returns an observable list of all customers from the database. 
     */
    public static ObservableList<Customers> getAllCustomers() {
        ObservableList<Customers> clist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT * from customers";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divisionID = rs.getInt("Division_ID");
                Customers c = new Customers(customerID, customerName, address, postalCode, phoneNumber,createDate, createdBy, lastUpdate, lastUpdatedBy, divisionID);
                clist.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
    
    /**
     * Used to retrieve a list of distinct customer IDs from the database. 
     */
    public static ObservableList<Integer> getCustomerIDs() {
        ObservableList<Integer> clist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT distinct customer_id from customers";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                clist.add(customerID);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
    
    /**
     * Adds a new customer to the database. 
     */
    public static void dbAddCustomer(Customers c) throws SQLException {
        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.setString(1, c.getCustomerName());
            ps.setString(2, c.getAddress());
            ps.setString(3, c.getPostalCode());
            ps.setString(4, c.getPhoneNumber());
            ps.setInt(5, c.getDivisionID());
            
            ps.executeUpdate();
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    /**
     * Deletes a customer from the database using the Customer's primary key value. 
     */
    public static void dbDeleteCustomer(Customers c) throws SQLException {
        try {
            String sql = "DELETE from customers where Customer_ID = " + c.getId();
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.executeUpdate();
            
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    }
    
    /**
     * Updates an existing customer in the database. 
     */
    public static void dbUpdateCustomer(int customerID, String customerName, String address, String postalCode, String phoneNumber, int divisionID) throws SQLException {
        try {
            String sql = "UPDATE customers set customer_name = ?, address = ?, postal_code = ?, phone = ?, division_id = ? where customer_id = ?";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNumber);
            ps.setInt(5, divisionID);
            ps.setInt(6, customerID);
            
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    /**
     * Deletes all appointments in the database that are associated to a particular customer. 
     */   
    public static void dbDeleteCustomerAppointments(Customers c) throws SQLException {
        try {
            String sql = "DELETE from appointments where Customer_ID = " + c.getId();
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.executeUpdate();
            
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    }
}
    
