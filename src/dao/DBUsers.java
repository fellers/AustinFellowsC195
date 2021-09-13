/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Timestamp;
import model.Users;

/**
 * Class used to perform database transactions on the users table.
 * @author indya
 */
public class DBUsers {
    /**
     * Returns an observable list of all users from the database. 
     */
    public static ObservableList<Users> getAllUsers() {
        ObservableList<Users> clist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT * from users";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                Users u = new Users(userID, userName, password, createDate, createdBy, lastUpdate, lastUpdatedBy);
                clist.add(u);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
    
    /**
     * Returns an observable list of distinct user IDs from the database. 
     */
    public static ObservableList<Integer> getUserIDs() {
        ObservableList<Integer> clist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT distinct user_id from users";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int userID = rs.getInt("User_ID");
                clist.add(userID);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
}
