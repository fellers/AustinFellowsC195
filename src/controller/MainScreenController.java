/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static dao.DBAppointments.checkForUpcomingAppointments;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Appointments;
import static model.Appointments.getUpcomingAppointment;
import static model.Users.getCurrentUser;

/**
 * FXML Controller class
 *
 * @author indya
 */
public class MainScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if (LoginController.showMessage == true) {
            LoginController.showMessage = false;
            if (checkForUpcomingAppointments(getCurrentUser()) == true) {
                Appointments apt = getUpcomingAppointment();
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Attention");
                a.setHeaderText("You have an upcoming appointment");
                a.setContentText("The appointment ID is: " + apt.getAppointmentID() + ". The appointment begins at: " + apt.getStart());
                a.showAndWait();
            } else {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Attention");
                a.setHeaderText("No appointments scheduled within the next 15 minutes");
                a.setContentText("Press ok to continue.");
                a.showAndWait();
        }
    }
}
    

    /**
     * Directs the user to the customer records screen.
     */
    @FXML
    private void customerRecordsButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerGUI.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Customers");
        stage.show();
    }

    /**
     * Directs the user to the appointments screen.
     */
    @FXML
    private void appointmentsButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsGUI.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Appointments");
        stage.show();
    }

    /**
     * Exits the application.
     */
    @FXML
    private void exitButton(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Opens the customer appointment reports screen.
     */
    @FXML
    private void customerAppointmentsButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerAppointments.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Customer Appointments by Type and Month");
        stage.show();
    }

    /**
     * Opens the contact schedules report screen. 
     */
    @FXML
    private void contactSchedulesButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ContactSchedules.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Contact Schedules");
        stage.show();
    }

    /**
     * Opens the user appointments reports screen. 
     */
    @FXML
    private void userAppointmentsButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/UserAppointments.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("User Appointments");
        stage.show();
    }
    
}
