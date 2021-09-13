/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import static model.Appointments.setUpcomingAppointment;
import model.Users;

/**
 * Class used to store methods that access the appointments table in the database.
 * @author indya
 */
public class DBAppointments {
    
    /**
     * Returns a list of all appointments in the database.
     */
    public static ObservableList<Appointments> getAllAppointments() {
        ObservableList<Appointments> clist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT * FROM appointments;";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointments a = new Appointments(appointmentID, title, description, location, type, start, end, customerID, userID, contactID);
                clist.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clist;
    }
    
    /**
     * Creates an observable list of times for the user to select when scheduling an appointment.
     */
    public static ObservableList<String> getTimes() {
        ObservableList<String> timeList = FXCollections.observableArrayList();
        timeList.addAll("00:00", "00:15", "00:30", "00:45" , "01:00", "01:15", "01:30", "01:45", "02:00", "02:15", "02:30", "02:45");
        timeList.addAll("03:00", "03:15", "03:30", "03:45", "04:00", "04:15", "04:30", "04:45", "05:00", "05:15", "05:30", "05:45");
        timeList.addAll("06:00", "06:15", "06:30", "06:45", "07:00", "07:15", "07:30", "07:45", "08:00", "08:15", "08:30", "08:45");
        timeList.addAll("09:00", "09:15", "09:30", "09:45", "10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45");
        timeList.addAll("12:00", "12:15", "12:30", "12:45", "13:00", "13:15", "13:30", "13:45", "14:00", "14:15", "14:30", "14:45");
        timeList.addAll("15:00", "15:15", "15:30", "15:45", "16:00", "16:15", "16:30", "16:45", "17:00", "17:15", "17:30", "17:45");
        timeList.addAll("18:00", "18:15", "18:30", "18:45", "19:00", "19:15", "19:30", "19:45", "20:00", "20:15", "20:30", "20:45");
        timeList.addAll("21:00", "21:15", "21:30", "21:45", "22:00", "22:15", "22:30", "22:45", "23:00", "23:15", "23:30", "23:45");
        return timeList;
    }
    
    /**
     * Checks whether or not the appointment being saved is within business hours.
     */
    public static boolean checkForBusinessHours(String startTime, String startDate, String endTime, String endDate) {
        boolean withinHours = true;
        LocalDate userStartLD = LocalDate.of(Integer.parseInt(startDate.substring(0, 4)), Integer.parseInt(startDate.substring(6, 7)), Integer.parseInt(startDate.substring(9, 10)));
        LocalDate userEndLD = LocalDate.of(Integer.parseInt(endDate.substring(0, 4)), Integer.parseInt(endDate.substring(6, 7)), Integer.parseInt(endDate.substring(9, 10)));
        LocalTime userStartLT = LocalTime.of(Integer.parseInt(startTime.substring(0, 2)), Integer.parseInt(startTime.substring(4)));
        LocalTime userEndLT = LocalTime.of(Integer.parseInt(endTime.substring(0, 2)), Integer.parseInt(endTime.substring(4)));
        LocalDateTime startLDT = LocalDateTime.of(userStartLD, userStartLT);
        LocalDateTime endLDT = LocalDateTime.of(userEndLD, userEndLT);
        ZoneId userZoneID = ZoneId.systemDefault();
        ZonedDateTime userStartZDT = ZonedDateTime.of(startLDT, userZoneID);
        ZonedDateTime userEndZDT = ZonedDateTime.of(endLDT, userZoneID);
        
        ZoneId estZoneId = ZoneId.of("US/Eastern");
        ZonedDateTime estStartZDT = ZonedDateTime.ofInstant(userStartZDT.toInstant(), estZoneId);
        ZonedDateTime estEndZDT = ZonedDateTime.ofInstant(userEndZDT.toInstant(), estZoneId);
        int userStartEst = estStartZDT.getHour();
        int userEndEst = estEndZDT.getHour();
        int userEndMinute = estEndZDT.getMinute();
        if (userStartEst < 8 || userEndEst > 22) {
            withinHours = false;
        } else if (userEndEst == 22 && userEndEst + userEndMinute > 22) {
            withinHours = false;
        }
        return withinHours;
    }
    
    /**
     * Checks for appointments scheduled within 15 minutes of a user's login.
     */
    public static boolean checkForUpcomingAppointments(Users u) {
        ObservableList<Appointments> alist = FXCollections.observableArrayList();
        
        boolean upcomingAppointment = false;
        int userID = u.getUserID();
        try {
            String sql = "SELECT appointment_id, customer_id, start, end from appointments where (user_id = ? AND start between sysdate() and date_add(sysdate(), INTERVAL 15 minute))";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                        
            ps.setInt(1, userID);
            
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                int customer = rs.getInt("Customer_ID");
                Timestamp startTimestamp = rs.getTimestamp("Start");
                Timestamp endTimestamp = rs.getTimestamp("End");
                
                Appointments a = new Appointments(appointmentID, startTimestamp, endTimestamp, customer);
                alist.add(a);
                setUpcomingAppointment(a);
            }
            if (!alist.isEmpty()) { 
                upcomingAppointment = true;
            }
             
        } catch (Exception e) {
            e.printStackTrace();
        }
        return upcomingAppointment;
    }
    
    public static ObservableList<Appointments> getCustomerAppointmentsReport() {
        ObservableList<Appointments> alist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT monthname(start) as month, type, count(*) as count from appointments group by monthname(start), type";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int count = rs.getInt("count");
                String month = rs.getString("month");
                String type = rs.getString("type");
                
                Appointments a = new Appointments(type, month, count);
                alist.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alist;
    }
    
    /**
     * Returns an observable list of appointments for the user appointments report feature.
     */
    public static ObservableList<Appointments> getUserAppointmentsReport(int userID) {
        ObservableList<Appointments> alist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT appointment_id, title, type, description, start, end, user_id from appointments where user_id = ?";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.setInt(1, userID);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String description = rs.getString("Description");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int ID = rs.getInt("User_ID");
                
                Appointments a = new Appointments(userID, appointmentID, title, type, description, start, end);
                alist.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alist;
    }
    
    /**
     * Returns an observable list of appointments for the contact appointments reporting feature. 
     */
    public static ObservableList<Appointments> getContactAppointmentsReport(int contactID) {
        ObservableList<Appointments> alist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT appointment_id, title, type, description, start, end, customer_id from appointments where customer_id = ?";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.setInt(1, contactID);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String description = rs.getString("Description");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                
                Appointments a = new Appointments(appointmentID, title, type, description, start, end, customerID);
                alist.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alist;
    }
    
    /**
     * Checks that an appointment doesn't interfere with another appointment scheduled for the same customer.
     */
    public static boolean checkForOverlappingAppointments(int customerID, String startTime, String startDate, String endTime, String endDate) {
        ObservableList<Appointments> alist = FXCollections.observableArrayList();
        
        boolean overlapping = false;
        
        try {
            String sql = "SELECT appointment_id, customer_id, start, end from appointments where (customer_id = ? AND ((start <= ? AND end> ?) OR (start <= ? AND end > ?)))";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            String start = startDate + " " + startTime + ":00";
            String end = endDate + " " + endTime + ":00";
            ps.setInt(1, customerID);
            ps.setString(2, start);
            ps.setString(3, start);
            ps.setString(4, end);
            ps.setString(5, end);
            
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                int customer = rs.getInt("Customer_ID");
                Timestamp startTimestamp = rs.getTimestamp("Start");
                Timestamp endTimestamp = rs.getTimestamp("End");
                
                Appointments a = new Appointments(appointmentID, startTimestamp, endTimestamp, customer);
                alist.add(a);
            }
            if (!alist.isEmpty()) 
                overlapping = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return overlapping;
    }
  
    /**
     * Returns an observable list of appointments objects that also contain customer names.
     */
    public static ObservableList<Appointments> getAllAppointmentsWithNames() {
        ObservableList<Appointments> clist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT appointment_id, title, description, location, type, start, end, customer_id, user_id, appointments.contact_id, contacts.contact_name FROM appointments left join contacts on appointments.contact_id = contacts.contact_id;";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                Appointments a = new Appointments(appointmentID, title, description, location, type, start, end, customerID, userID, contactID, contactName);
                clist.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clist;
    }
    
    /**
     * Adds a new appointment to the database using raw data input as the parameters.
     */
    public static void dbAddAppointment(String title, String description, String location, String type, String startTime, String startDate, String endTime, String endDate, int customerID, int userID, int contactID) throws SQLException {
        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            String start = startDate + " " + startTime;
            String end = endDate + " " + endTime;
            Timestamp startTimestamp = Timestamp.valueOf(start + ":00");
            Timestamp endTimestamp = Timestamp.valueOf(end + ":00");
            
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, startTimestamp);
            ps.setTimestamp(6, endTimestamp);
            ps.setInt(7, customerID);
            ps.setInt(8, userID);
            ps.setInt(9, contactID);
           
            
            ps.executeUpdate();
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    /**
     * Adds a new appointment to the database with an appointment object as the parameter.
     */
    public static void dbAddAppointment(Appointments a) throws SQLException {
        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.setString(1, a.getTitle());
            ps.setString(2, a.getDescription());
            ps.setString(3, a.getLocation());
            ps.setString(4, a.getType());
            ps.setTimestamp(5, a.getStart());
            ps.setTimestamp(6, a.getEnd());
            ps.setInt(7, a.getCustomerID());
            ps.setInt(8, a.getUserID());
            ps.setInt(9, a.getContactID());
           
            
            ps.executeUpdate();
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    /**
     * Deletes an appointment from the database.
     */
    public static void dbDeleteAppointment(Appointments a) throws SQLException {
        try {
            String sql = "DELETE from appointments where Appointment_ID = " + a.getAppointmentID();
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.executeUpdate();
            
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    }
    
    /**
     * Updates an existing appointment in the database.
     */
    public static void dbUpdateAppointment(int appointmentID, String title, String description, String location, String type, String startTime, String startDate, String endTime, String endDate, int customerID, int userID, int contactID) throws SQLException {
        try {
            String sql = "UPDATE appointments set title = ?, description = ?, location = ?, type = ?, start = ?, end = ?, customer_id = ?, user_id = ?, contact_id = ? where appointment_id = ?";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            String start = startDate + " " + startTime;
            String end = endDate + " " + endTime;
            Timestamp startTimestamp = Timestamp.valueOf(start + ":00");
            Timestamp endTimestamp = Timestamp.valueOf(end + ":00");
            
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, startTimestamp);
            ps.setTimestamp(6, endTimestamp);
            ps.setInt(7, customerID);
            ps.setInt(8, userID);
            ps.setInt(9, contactID);
            ps.setInt(10, appointmentID);
            
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    /**
     * Returns a list of appointments scheduled between current time and a week from then.
     */
    public static ObservableList<Appointments> getAllAppointmentsThisWeek() {
        ObservableList<Appointments> clist = FXCollections.observableArrayList();
        //ContactNameInterface name = id -> Contacts.getName(id);
        
        try {
            String sql = "SELECT appointment_id, title, description, location, type, start, end, customer_id, user_id, appointments.contact_id, contacts.contact_name FROM appointments left join contacts on appointments.contact_id = contacts.contact_id where start between current_date() and current_date() + 7;";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                Appointments a = new Appointments(appointmentID, title, description, location, type, start, end, customerID, userID, contactID, contactName);
                clist.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clist;
    }
    
    /**
     * Returns a list of all appointments scheduled between the current time and until a month from then.
     */
    public static ObservableList<Appointments> getAllAppointmentsThisMonth() {
        ObservableList<Appointments> clist = FXCollections.observableArrayList();
        //ContactNameInterface name = id -> Contacts.getName(id);
        
        try {
            String sql = "SELECT appointment_id, title, description, location, type, start, end, customer_id, user_id, appointments.contact_id, contacts.contact_name FROM appointments left join contacts on appointments.contact_id = contacts.contact_id where start between current_date() and DATE_ADD(current_date(), INTERVAL 1 MONTH);";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                Appointments a = new Appointments(appointmentID, title, description, location, type, start, end, customerID, userID, contactID, contactName);
                clist.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clist;
    }

}
