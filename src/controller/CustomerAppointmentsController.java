/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static dao.DBAppointments.getCustomerAppointmentsReport;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;

/**
 * FXML Controller class
 * Controller for customer appointment reports.
 * @author indya
 */
public class CustomerAppointmentsController implements Initializable {

    @FXML
    private TableView<Appointments> appointmentsTable;
    @FXML
    private TableColumn<Appointments, String> monthColumn;
    @FXML
    private TableColumn<Appointments, String> typeColumn;
    @FXML
    private TableColumn<Appointments, Integer> countColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        appointmentsTable.setItems(getCustomerAppointmentsReport());
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
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
    
}
