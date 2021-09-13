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
import model.Contacts;

/**
 * Class used to retrieve contact information from the database.
 * @author indya
 */
public class DBContacts {
    /**
     * Returns a list of all contacts in the database.
     */
    public static ObservableList<Contacts> getAllContacts() {
        ObservableList<Contacts> clist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT * from contacts";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contacts c = new Contacts(contactID, contactName, email);
                clist.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
    
}
