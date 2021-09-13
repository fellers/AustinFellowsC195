/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DBAppointments;
import static dao.DBAppointments.checkForBusinessHours;
import static dao.DBAppointments.checkForOverlappingAppointments;
import static dao.DBAppointments.getAllAppointments;
import static dao.DBAppointments.getAllAppointmentsThisMonth;
import static dao.DBAppointments.getAllAppointmentsThisWeek;
import static dao.DBAppointments.getAllAppointmentsWithNames;
import static dao.DBAppointments.getTimes;
import static dao.DBContacts.getAllContacts;
import static dao.DBCustomers.getCustomerIDs;
import static dao.DBUsers.getUserIDs;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import model.Appointments;
import model.Contacts;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static model.Appointments.extractDate;
import static model.Appointments.extractTime;
import utils.Alerts;
import utils.ContactNameInterface;

/**
 * FXML Controller class
 * Controller for Appointments GUI
 * @author indya
 */
public class AppointmentsGUIController implements Initializable {

    @FXML
    private TextField appointmentIDTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField locationTextField;
    @FXML
    private ComboBox<Contacts> contactComboBox;
    @FXML
    private ComboBox<Integer> userIDComboBox;
    @FXML
    private ComboBox<Integer> customerIDComboBox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private RadioButton allAppointmentsRadioButton;
    @FXML
    private RadioButton thisWeekRadioButton;
    @FXML
    private RadioButton thisMonthRadioButton;
    @FXML
    private TableColumn<Appointments, Integer> appointmentIDColumn;
    @FXML
    private TableColumn<Appointments, String> titleColumn;
    @FXML
    private TableColumn<Appointments, String> descriptionColumn;
    @FXML
    private TableColumn<Appointments, String> locationColumn;
    @FXML
    private TableColumn<Appointments, StringProperty> contactColumn;
    @FXML
    private TableColumn<Appointments, String> typeColumn;
    @FXML
    private TableColumn<Appointments, Timestamp> startColumn;
    @FXML
    private TableColumn<Appointments, Timestamp> endColumn;
    @FXML
    private TableColumn<Appointments, Integer> customerIDColumn;
    @FXML
    private ComboBox<String> startTimeComboBox;
    @FXML
    private ComboBox<String> endTimeComboBox;
    @FXML
    private TableView<Appointments> appointmentsTable;
    @FXML
    private ToggleGroup radioButtons;
    
    private boolean modified = false;
    ObservableList<String> types = FXCollections.observableArrayList();
    ObservableList<String> distinctTypes = FXCollections.observableArrayList();
    List<String> distinctList = new ArrayList<>();
    /**
     * discussion of lambda
     * A lambda expression was used here so the list contained in the type combo box would not contain duplicates available for user selection.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getAllAppointments().stream().forEach(a -> types.add(a.getType()));
        distinctList = types.stream().distinct().collect(Collectors.toList());
        distinctList.stream().forEach(t -> distinctTypes.add(t));
        
        appointmentsTable.setItems(getAllAppointmentsWithNames());
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeComboBox.setItems(distinctTypes);
        contactComboBox.setItems(getAllContacts());
        userIDComboBox.setItems(getUserIDs());
        customerIDComboBox.setItems(getCustomerIDs());
        startTimeComboBox.setItems(getTimes());
        endTimeComboBox.setItems(getTimes());
    }    
    /**
     * Changes the contents of the table to all appointments.
     */
    @FXML
    private void allAppointmentsRadioButtonAction(ActionEvent event) {
        appointmentsTable.setItems(getAllAppointmentsWithNames());
    }
    
    /**
     * Changes the contents of the table to appointments scheduled within the next week.
     */
    @FXML
    private void thisWeekRadioButtonAction(ActionEvent event) {
        appointmentsTable.setItems(getAllAppointmentsThisWeek());
    }
    
    /**
     * Changes the contents of the table to appointments scheduled within the next month.
     */
    @FXML
    private void thisMonthRadioButtonAction(ActionEvent event) {
        appointmentsTable.setItems(getAllAppointmentsThisMonth());
    }
    
    /**
     * Saves an appointment based on the users input.
     * The save function will also validate input based on the given specifications
     */
    @FXML
    private void saveButton(ActionEvent event) {
        try {
            String title = titleTextField.getText();
            String description = descriptionTextField.getText();
            String location = locationTextField.getText();
            String type = typeComboBox.getValue().toString();
            String startTime = startTimeComboBox.getValue();
            String startDate = startDatePicker.getValue().toString();
            String endTime = endTimeComboBox.getValue();
            String endDate = endDatePicker.getValue().toString();
            int customerID = customerIDComboBox.getValue();
            int userID = userIDComboBox.getValue();
            int contactID = contactComboBox.getValue().getContactID();
            if (checkForBusinessHours(startTime, startDate, endTime, endDate) == true) {
            if (modified == false) {
                if (checkForOverlappingAppointments(customerID, startTime, startDate, endTime, endDate) == true) {
                    Alerts.alerts(5);
                    } else {
                        DBAppointments.dbAddAppointment(title, description, location, type, startTime, startDate, endTime, endDate, customerID, userID, contactID);
                        appointmentsTable.setItems(getAllAppointmentsWithNames());
                    }
                } else {
                    if (checkForOverlappingAppointments(customerID, startTime, startDate, endTime, endDate) == true) {
                        Alerts.alerts(5);
                        } else {
                        int appointmentID = Integer.parseInt(appointmentIDTextField.getText());
                        DBAppointments.dbUpdateAppointment(appointmentID, title, description, location, type, startTime, startDate, endTime, endDate, customerID, userID, contactID);
                        appointmentsTable.setItems(getAllAppointmentsWithNames());
                    }
                } 
            } else {
                        Alerts.alerts(6);
                        }
            } catch (Exception e) {
                Alerts.alerts(8);
            }
        appointmentsTable.setItems(getAllAppointmentsWithNames());
        
    }

    /**
     * discussion of lambda
     * I used a lambda expression in this block of code to map an appointment to a contact.
     * This improves the readability of the code.
     */
    @FXML
    private void modifyButton(ActionEvent event) {
        Appointments selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
        ContactNameInterface getName = (a) -> {
            for (Contacts c: getAllContacts()) {
                if (c.getContactID() == a.getContactID())
                    return c;
            }
            return null;
        };
        appointmentIDTextField.setText(Integer.toString(selectedAppointment.getAppointmentID()));
        titleTextField.setText(selectedAppointment.getTitle());
        descriptionTextField.setText(selectedAppointment.getDescription());
        locationTextField.setText(selectedAppointment.getLocation());
        typeComboBox.setValue(selectedAppointment.getType());
        customerIDComboBox.setValue(selectedAppointment.getCustomerID());
        userIDComboBox.setValue(selectedAppointment.getUserID());
        contactComboBox.setValue(getName.getContactName(selectedAppointment));
        startDatePicker.setValue(extractDate(selectedAppointment.getStart()));
        endDatePicker.setValue(extractDate(selectedAppointment.getEnd()));
        startTimeComboBox.setValue(extractTime(selectedAppointment.getStart()));
        endTimeComboBox.setValue(extractTime(selectedAppointment.getEnd()));
        modified = true;
    }

    /**
     * Allows the user to delete the selected appointment in the table.
     */
    @FXML
    private void deleteButton(ActionEvent event) throws Exception {
        Appointments selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Alert nullAlert = new Alert(Alert.AlertType.ERROR);
            nullAlert.setTitle("Cannot remove appointment");
            nullAlert.setHeaderText("No appointment selected");
            nullAlert.setContentText("You must select an appointment in order to remove it");
            nullAlert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Appointment Confirmation");
            alert.setHeaderText("Are you sure you would like to remove this appointment?");
            alert.setContentText("Press OK if you would like to remove this appointment, otherwise press Cancel");
            alert.showAndWait();
        
            if (alert.getResult() == ButtonType.OK) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Confirmation");
                alert2.setHeaderText("This appointment has been deleted.");
                alert2.setContentText("Press OK to continue.");
                alert2.showAndWait();
                try {
                DBAppointments.dbDeleteAppointment(selectedAppointment);
                } catch (SQLException e) {
                }
                appointmentsTable.setItems(getAllAppointmentsWithNames());
            } else {
                alert.close();
            }
        }
    }
    
    /**
     * Returns the user back to the main screen.
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

    
}
