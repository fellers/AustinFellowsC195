/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Contacts object class.
 * @author indya
 */
public class Contacts {
    private int contactID;
    private String contactName;
    private String email;
    
    /**
     * Contact object constructor. 
     */
    public Contacts(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }
    
    /**
     * String override method to return contact name. 
     */
    @Override
    public String toString() {
        return contactName;
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

    /**
     * Getter for contact name variable. 
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Setter for contact name variable. 
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Getter for email variable. 
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for email variable. 
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
}
