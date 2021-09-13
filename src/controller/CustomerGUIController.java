/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static dao.DBCountries.getDistinctCountries;
import dao.DBCustomers;
import static dao.DBCustomers.dbDeleteCustomerAppointments;
import static dao.DBCustomers.getAllCustomers;
import static dao.DBDivisions.getCanadianDivisions;
import static dao.DBDivisions.getUKDivisions;
import static dao.DBDivisions.getUSDivisions;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Countries;
import static model.Countries.getCountry;
import model.Customers;
import static model.Customers.checkForAppointments;
import static model.Customers.deleteCustomer;
import model.Divisions;
import static model.Divisions.getDivision;
import utils.Alerts;
import java.util.stream.*;

/**
 * FXML Controller class
 * Controller for Customer GUI
 * @author indya
 */
public class CustomerGUIController implements Initializable {

    @FXML
    private TableView<Customers> customerTable;
    @FXML
    private TableColumn<Customers, Integer> customerIDColumn;
    @FXML
    private TableColumn<Customers, String> customerNameColumn;
    @FXML
    private TableColumn<Customers, String> addressColumn;
    @FXML
    private TableColumn<Customers, Integer> postalCodeColumn;
    @FXML
    private TableColumn<Customers, String> phoneNumberColumn;
    @FXML
    private TableColumn<Customers, Integer> divisionIDColumn;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField postalCodeTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private ComboBox<Countries> countryComboBox;
    @FXML
    private ComboBox<Divisions> firstLevelDivisionComboBox;
    
    private boolean modified = false;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)  {

        customerTable.setItems(getAllCustomers());
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        divisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        
        ObservableList<Countries> countryNameList = FXCollections.observableArrayList();

        getDistinctCountries().stream().forEach((c) -> {
            countryNameList.add(c);
        });
        countryComboBox.setItems(countryNameList);
        
        firstLevelDivisionComboBox.setPromptText("You must first select a country");
    }

    
    /**
     * Allows the user to modify a customer based on their selection in the table.
     */
    @FXML
    private void modifyButton(ActionEvent event) {
        Customers selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        idTextField.setText(Integer.toString(selectedCustomer.getId()));
        nameTextField.setText(selectedCustomer.getCustomerName());
        addressTextField.setText(selectedCustomer.getAddress());
        postalCodeTextField.setText(selectedCustomer.getPostalCode());
        phoneNumberTextField.setText(selectedCustomer.getPhoneNumber());
        Divisions d = getDivision(selectedCustomer.getDivisionID());
        firstLevelDivisionComboBox.setValue(d);
        Countries c = getCountry(d);
        countryComboBox.setValue(c);
        modified = true;
    }
    
    /**
     * Allows the user to delete the customer they have selected in the table.
     */
    @FXML
    private void deleteButton(ActionEvent event) throws SQLException {
        Customers selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Alert nullAlert = new Alert(Alert.AlertType.ERROR);
            nullAlert.setTitle("Cannot remove customer");
            nullAlert.setHeaderText("No customer selected");
            nullAlert.setContentText("You must select a customer in order to remove them");
            nullAlert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Customer Confirmation");
            alert.setHeaderText("Are you sure you would like to remove this customer? This will also remove any associated appointments.");
            alert.setContentText("Press OK if you would like to remove this customer, otherwise press Cancel");
            alert.showAndWait();
        
            if (alert.getResult() == ButtonType.OK) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Confirmation");
                alert2.setHeaderText("This customer and all associated appointments have been deleted.");
                alert2.setContentText("Press OK to continue.");
                alert2.showAndWait();
                    if (checkForAppointments(selectedCustomer) == true) {
                    //delete appointments associated to the selected customer
                    dbDeleteCustomerAppointments(selectedCustomer);
                    }
                Customers.deleteCustomer(selectedCustomer);
                customerTable.setItems(DBCustomers.getAllCustomers());
            } else {
                alert.close();
            }
        }
        
    }
    
    /**
     * Allows the user to save a new customer or a modified customer.
     */
    @FXML
    private void saveButton(ActionEvent event) throws IOException, SQLException {
        try {
            String name = nameTextField.getText();
            String address = addressTextField.getText();
            String postalCode = postalCodeTextField.getText();
            String phoneNumber = phoneNumberTextField.getText();
            String country = countryComboBox.getValue().getCountryName();
            int division = firstLevelDivisionComboBox.getValue().getDivisionID();
                if (modified == false) {
                    Customers.addCustomer(new Customers(name, address, postalCode, phoneNumber, division));
                    } else {
                        int customerID = Integer.parseInt(idTextField.getText());
                        DBCustomers.dbUpdateCustomer(customerID, name, address, postalCode, phoneNumber, division);
                        modified = false;
                        
                    }
            } catch (Exception e) {
                //e.printStackTrace();
                Alerts.alerts(7);
            }
        idTextField.clear();
        nameTextField.clear();
        addressTextField.clear();
        postalCodeTextField.clear();
        phoneNumberTextField.clear();
        firstLevelDivisionComboBox.setValue(null);
        countryComboBox.setValue(null);
        customerTable.setItems(getAllCustomers());
    }
    
    /**
     * Used to determine which divisions to include in the first level divisions combo box.
     */
    @FXML
    private void countryComboBoxAction(ActionEvent event) {
        try {
        firstLevelDivisionComboBox.setPromptText("");
        int selectedCountry = countryComboBox.getValue().getCountryID();
        if (selectedCountry == 1) {
            firstLevelDivisionComboBox.setItems(getUSDivisions());
        }
        else if (selectedCountry == 2) {
            firstLevelDivisionComboBox.setItems(getUKDivisions());
        }
        else if (selectedCountry == 3) {
            firstLevelDivisionComboBox.setItems(getCanadianDivisions());
        }
        else {
        }
        } catch (Exception e) {
            
        }
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
