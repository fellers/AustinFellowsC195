/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static dao.DBAppointments.getContactAppointmentsReport;
import static dao.DBContacts.getAllContacts;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import java.sql.Timestamp;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 * Controller for contact schedule reports.
 * @author indya
 */
public class ContactSchedulesController implements Initializable {
    
    @FXML
    private TableView<Appointments> appointmentsTable;
    @FXML
    private TableColumn<Appointments, Integer> appointmentIDColumn;
    @FXML
    private TableColumn<Appointments, String> titleColumn;
    @FXML
    private TableColumn<Appointments, String> typeColumn;
    @FXML
    private TableColumn<Appointments, String> descriptionColumn;
    @FXML
    private TableColumn<Appointments, Timestamp> startColumn;
    @FXML
    private TableColumn<Appointments, Timestamp> endColumn;
    @FXML
    private TableColumn<Appointments, Integer> customerIDColumn;
    @FXML
    private ComboBox<Contacts> contactComboBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        contactComboBox.setItems(getAllContacts());
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }    

    /**
     * Returns the user to the main screen. 
     */
    @FXML
    private void backButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Main Screen");
        stage.show();
    }

    /**
     * Populates the table according to the users selection. 
     */
    @FXML
    private void showAppointmentsButton(ActionEvent event) {
        try {
            Contacts c = contactComboBox.getValue();
            int cID = c.getContactID();
            appointmentsTable.setItems(getContactAppointmentsReport(cID));
        } catch (Exception e) {
            
        }  
    }
}
