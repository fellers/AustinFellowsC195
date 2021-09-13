/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static dao.DBAppointments.getAllAppointments;
import dao.DBCustomers;
import static dao.DBCustomers.dbDeleteCustomer;
import static dao.DBCustomers.getAllCustomers;
import java.sql.SQLException;
import java.sql.Timestamp;
import javafx.collections.ObservableList;

/**
 * Customers object class.
 * @author indya
 */
public class Customers {
    
    private int id;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;
    
    private static ObservableList<Customers> allCustomers = DBCustomers.getAllCustomers();
    
    /**
     * Customer object constructor. 
     */
    public Customers(int id, String customerName, String address, String postalCode, String phoneNumber, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionID) {
        this.id = id;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
    }
    
    /**
     * Customer object constructor. 
     */
    public Customers(String customerName, String address, String postalCode, String phoneNumber, int divisionID) {
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
    }
    
   /**
    * Adds a new customer to the database. 
    */ 
    public static void addCustomer(Customers newCustomer) throws SQLException {
        allCustomers.add(newCustomer);
        DBCustomers.dbAddCustomer(newCustomer);
    }
    
    /**
     * Checks for appointments scheduled for a particular customer. 
     */
    public static boolean checkForAppointments(Customers c) throws SQLException {
        for (Appointments a : getAllAppointments()) {
            if (a.getCustomerID() == c.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for customer based on customer ID input. 
     */
    public static boolean checkForCustomer(int customerID) {
        for (Customers c : getAllCustomers()) {
            if (c.getId() == customerID) {
                return true;
            } 
        }
        return false;
    }
    
    /**
     * Deletes a customer from the database. 
     */
    public static void deleteCustomer(int customerID) throws SQLException {
        for (Customers c : getAllCustomers()) {
            if (c.getId() == customerID) {
                deleteCustomer(c);
            }
        }
    }
    
    /**
     * Deletes a customer from the database. 
     */
    public static void deleteCustomer(Customers customer) throws SQLException {
        for (Customers c : getAllCustomers()) {
            if (c.getId() == customer.getId()) {
                allCustomers.remove(customer);
                dbDeleteCustomer(customer);
            }
        }
    }

    /**
     * Getter for customer ID variable. 
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for customer ID variable. 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for customer name variable. 
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Setter for customer name variable.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Getter for address variable. 
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter for address variable. 
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter for postal code variable. 
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Setter for postal code variable. 
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Getter for phone number variable. 
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Setter for phone number variable. 
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Getter for division ID variable.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Setter for division ID variable. 
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
}
