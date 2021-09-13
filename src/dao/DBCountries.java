/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

/**
 * Class used to retrieve country information from the database.
 * @author indya
 */
public class DBCountries {
    
    /**
     * Returns a list of all countries from the database.
     */
    public static ObservableList<Countries> getAllCountries() {
        ObservableList<Countries> clist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT * from countries";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                Countries c = new Countries(countryId, countryName, createDate, createdBy, lastUpdate, lastUpdatedBy);
                clist.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
    
    /**
     * Used to retrieve distinct country names from the database, returns an observable list.
     */
    public static ObservableList<Countries> getDistinctCountries() {
        ObservableList<Countries> clist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT * from countries WHERE COUNTRY_ID <= 3";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                Countries c = new Countries(countryId, countryName, createDate, createdBy, lastUpdate, lastUpdatedBy);
                clist.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
}
