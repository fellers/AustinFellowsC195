/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static dao.DBDivisions.getAllDivisions;
import java.sql.Timestamp;

/**
 * Constructor class for first level divisions objects.
 * @author indya
 */
public class Divisions {

    private final int divisionID;
    private final String divisionName;
    private final Timestamp createDate;
    private final String createdBy;
    private final Timestamp lastUpdate;
    private final String lastUpdatedBy;
    private final int countryID;
    
    /**
     * Constructor object for divisions. 
     */
    public Divisions(int divisionID, String divisionName, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryID = countryID;
    }
 
    /**
     * Returns a list of divisions based on division ID. 
     */
    public static Divisions getDivision(int divisionID) {
        for (Divisions d : getAllDivisions()) {
            if (d.getDivisionID() == divisionID) {
                return d;
            }
        }
        return null;
    }
   

    /**
     * String override method to return division names for division objects. 
     */
    @Override
    public String toString() {
        return divisionName;
    }
    
    /**
     * Getter for division ID variable. 
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Getter for division name variable. 
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Getter for country ID variable. 
     */
    public int getCountryID() {
        return countryID;
    }    
    
}
