/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static dao.DBAppointments.getUserAppointmentsReport;
import static dao.DBUsers.getAllUsers;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Appointments;
import java.sql.Timestamp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Users;

/**
 * FXML Controller class
 * Controller for user appointments reports screen.
 * @author indya
 */
public class UserAppointmentsController implements Initializable {

    @FXML
    private TableColumn<Appointments, Integer> appointmentIDColumn;
    @FXML
    private TableColumn<Appointments, String> titleColumn;
    @FXML
    private TableColumn<Appointments, String> typeColumn;
    @FXML
    private TableColumn<Appointments, Timestamp> startColumn;
    @FXML
    private TableColumn<Appointments, Timestamp> endColumn;
    @FXML
    private TableView<Appointments> appointmentsTable;
    @FXML
    private ComboBox<Users> userComboBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        userComboBox.setItems(getAllUsers());
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
    }    

    /**
     * Returns user to main screen. 
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
     * Populates the table based on the users selection.
     */
    @FXML
    private void showAppointmentsButton(ActionEvent event) {
        try {
        int id = userComboBox.getValue().getUserID();
        appointmentsTable.setItems(getUserAppointmentsReport(id));
        } catch (Exception e) {
            
        }
    }
    
}
