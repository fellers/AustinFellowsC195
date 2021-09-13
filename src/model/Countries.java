/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static dao.DBCountries.getAllCountries;
import java.sql.Timestamp;

/**
 * Countries object class.
 * @author indya
 */
public class Countries {
    private final int countryID;
    private final String countryName;
    private final Timestamp createDate;
    private final String createdBy;
    private final Timestamp lastUpdate;
    private final String lastUpdatedBy;


    /**
     * Countries object constructor. 
     */
    public Countries (int countryID, String countryName, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        this.countryID = countryID;
        this.countryName = countryName;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
    /**
     * Returns a list of all divisions associated to countries. 
     */
    public static Countries getCountry(Divisions d) {
        for (Countries c : getAllCountries()) {
            if (c.getCountryID() == d.getCountryID()) {
                return c;
            }
        }
        return null;
    }
    
    /**
     * To string override method for country objects. 
     */
    @Override
    public String toString(){
        return (countryName);
    }
    
    /**
     *  Getter for country ID variable.
     */
    public int getCountryID() {
        return countryID;
    }
    
    /**
     * Getter for country name variable. 
     */
    public String getCountryName() {
        return countryName;
    }
    
}
