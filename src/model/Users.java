/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static dao.DBUsers.getAllUsers;
import java.sql.Timestamp;

/**
 * Class used to construct user objects.
 * @author indya
 */
public class Users {
    
    private final int id;
    private final String userName;
    private final String password;
    private final Timestamp createDate;
    private final String createdBy;
    private final Timestamp lastUpdate;
    private final String lastUpdatedBy;
    
    /**
     * Object constructor for users. 
     */
    public Users(int id, String userName, String password, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
    /**
     * Overrides the to string method for user objects by returning the user name. 
     */
    @Override
    public String toString() {
        return userName;
    }
    
    /**
     * Variable used to store the user currently logged in.
     */
    private static Users currentUser;
    
    /**
     * Setter for the current user variable. 
     */
    public static void setCurrentUser(String userName) {
        for (Users u : getAllUsers()) {
            if (u.getUserName().equals(userName)) {
                currentUser = u;
            }
        }
    }
    
    /**
     * Getter for the current user variable. 
     */
    public static Users getCurrentUser() {
        return currentUser;
    }

    /**
     * Getter for the user ID variable.
     */
    public int getUserID() {
        return id;
    }

    /**
     * Getter for username variable. 
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Getter for password variable. 
     */
    public String getPassword() {
        return password;
    }
}
