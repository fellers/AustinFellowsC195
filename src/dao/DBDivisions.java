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
import model.Divisions;
import java.sql.Timestamp;

/**
 * Class used to perform database transactions on the divisions table.
 * @author indya
 */
public class DBDivisions {
    /**
     * Returns a complete list of all divisions in the database. 
     */
    public static ObservableList<Divisions> getAllDivisions() {
        ObservableList<Divisions> clist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT * from first_level_divisions";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy  = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy  = rs.getString("Last_Updated_By");
                int countryID = rs.getInt("Country_ID");
                Divisions d = new Divisions(divisionID, divisionName, createDate, createdBy, lastUpdate, lastUpdatedBy, countryID);
                clist.add(d);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
    
    /**
     * Returns an observable list of United States divisions from the database. 
     */
    public static ObservableList<Divisions> getUSDivisions() {
        ObservableList<Divisions> clist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT * from first_level_divisions where country_id = 1";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy  = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy  = rs.getString("Last_Updated_By");
                int countryID = rs.getInt("Country_ID");
                Divisions d = new Divisions(divisionID, divisionName, createDate, createdBy, lastUpdate, lastUpdatedBy, countryID);
                clist.add(d);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
    
    /**
     * Returns an observable list of all Canadian divisions in the database. 
     */
    public static ObservableList<Divisions> getCanadianDivisions() {
        ObservableList<Divisions> clist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT * from first_level_divisions where country_id = 3";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy  = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy  = rs.getString("Last_Updated_By");
                int countryID = rs.getInt("Country_ID");
                Divisions d = new Divisions(divisionID, divisionName, createDate, createdBy, lastUpdate, lastUpdatedBy, countryID);
                clist.add(d);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
    
    /**
     * Returns an observable list of all UK divisions in the database. 
     */
    public static ObservableList<Divisions> getUKDivisions() {
        ObservableList<Divisions> clist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT * from first_level_divisions where country_id = 2";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy  = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy  = rs.getString("Last_Updated_By");
                int countryID = rs.getInt("Country_ID");
                Divisions d = new Divisions(divisionID, divisionName, createDate, createdBy, lastUpdate, lastUpdatedBy, countryID);
                clist.add(d);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
}
