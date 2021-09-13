/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Appointments object class.
 * @author indya
 */
public class Appointments {

    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;
    private String month;
    private int count;
    
    /**
     * Used for tracking upcoming appointments and alerting the user.
     */
    private static Appointments nextUpcomingAppointment;
    
    /**
     * String property used for populating contact combo box with contact names.
     */
    private final StringProperty contactName = new SimpleStringProperty();

    /**
     * Used to override the default to string method when populating appointments in combo boxes. 
     */
    @Override
    public String toString() {
        return type;
    }
    
    /**
     * Sets the value of the upcoming appointment variable. 
     */
    public static void setUpcomingAppointment(Appointments a) {
        nextUpcomingAppointment = a;
    }
    
    /**
     * Returns the next upcoming appointment variable. 
     */
    public static Appointments getUpcomingAppointment() {
        return nextUpcomingAppointment;
    }
    
    /**
     * Getter for simple string property contact name. 
     */
    public final StringProperty contactProperty() {
        return contactName;
    }
    
    /**
     * Sets the contact name. 
     */
    public final void setContactName(String name) {
        contactName.set(name);
    }
    
    /**
     * Extracts the time from a timestamp value. 
     */
    public static final String extractTime(Timestamp t) {
        String date = t.toString();
        return date.substring(11, 16);
    }
    
    /**
     * Extracts the date from a timestamp value. 
     */
    public static final LocalDate extractDate(Timestamp t) {
        String date = t.toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date.substring(0, 10), formatter);
        return localDate;
    }
    
    /**
     * Appointments object constructor. 
     */
    public Appointments(String type, String month, int count) {
        this.type = type;
        this.month = month;
        this.count = count;
    }
    
    /**
     * Appointments object constructor. 
     */
    public Appointments(int appointmentID, String title, String type, String description, Timestamp start, Timestamp end, int customerID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.type = type;
        this.description = description;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
    }
    
    /**
     * Appointments object constructor. 
     */
    public Appointments(int userID, int appointmentID, String title, String type, String description, Timestamp start, Timestamp end) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.type = type;
        this.description = description;
        this.start = start;
        this.end = end;
        this.userID = userID;
    }
    
    /**
     * Appointments object constructor. 
     */
    public Appointments(int appointmentID, String title, String description, String location, String type, Timestamp start, Timestamp end, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }
    
    /**
     * Appointments object constructor. 
     */
    public Appointments(int appointmentID, Timestamp start, Timestamp end, int customerID) {
        this.appointmentID = appointmentID;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
    }
    
    /**
     * Appointments object constructor. 
     */
    public Appointments (String title, String description, String location, String type, Timestamp start, Timestamp end, int customerID, int userID, int contactID) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }
    
    /**
     * Appointments object constructor. 
     */
    public Appointments (String title, String description, String location, String type, Timestamp start, Timestamp end, int customerID, int userID, int contactID, String contactName) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        setContactName(contactName);
    }
    
    /**
     * Appointments object constructor. 
     */
    public Appointments (int appointmentID, String title, String description, String location, String type, Timestamp start, Timestamp end, int customerID, int userID, int contactID, String contactName) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        setContactName(contactName);
    }
    
    /**
     * Getter for appointment ID variable. 
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Setter for appointment ID variable.
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }
    
    /**
     * Getter for month variable. 
     */
    public String getMonth() {
        return month;
    }
    
    /**
     * Getter for count variable. 
     */
    public int getCount() {
        return count;
    }

    /**
     * Getter for title variable. 
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for title variable. 
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for description variable. 
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for description variable. 
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for location variable. 
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter for location variable. 
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter for type variable. 
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for type variable. 
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for start variable. 
     */
    public Timestamp getStart() {
        return start;
    }

    /**
     * Setter for start variable. 
     */
    public void setStart(Timestamp start) {
        this.start = start;
    }

    /**
     * Getter for end variable. 
     */
    public Timestamp getEnd() {
        return end;
    }

    /**
     * Setter for end variable. 
     */
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    /**
     * Getter for customer ID variable. 
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Setter for customer ID variable. 
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Getter for user ID variable. 
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Setter for user ID variable. 
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Getter for contact ID variable. 
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Setter for contact ID variable. 
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

}
