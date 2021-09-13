/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DBUsers;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Users;
import static model.Users.setCurrentUser;
import utils.Alerts;
import utils.LoginTracker;

/**
 * FXML Controller class
 * Controller for the login screen
 * @author indya
 */
public class LoginController implements Initializable {

    @FXML
    private Label loginLabel;
    @FXML
    private Label userLocationLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Label userNameLabel;
    @FXML
    private TextField userNameTextField;
    
    public static boolean showMessage = true;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ZoneId zone = ZoneId.systemDefault();
        String zoneID = zone.toString();
        try {
            ResourceBundle rsb = ResourceBundle.getBundle("schedulingapplication/Nat", Locale.getDefault());
        
            if (Locale.getDefault().getLanguage().equals("fr")) 
                loginButton.setText(rsb.getString("Login"));
                userNameLabel.setText(rsb.getString("Username"));
                userLocationLabel.setText(zoneID);
                passwordLabel.setText(rsb.getString("Password"));
                loginLabel.setText(rsb.getString("Login"));
            
            } catch (Exception e) {
                userLocationLabel.setText(zoneID);
            }
    }    
    
    /**
     * Takes user input and determines whether the login credentials are valid and displays a custom message based on user input.
     */
    @FXML
    private void loginButtonEvent(ActionEvent event) throws IOException {
        String user = userNameTextField.getText();
        String userPassword = passwordTextField.getText();
        ObservableList<Users> userList = DBUsers.getAllUsers();
        
        
        for (Users u: userList) {
            try {
            if (user.equals(u.getUserName()) && userPassword.equals(u.getPassword())) {
                setCurrentUser(user);
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Main Screen");
                stage.show();
                LoginTracker.logAttempt("Yes", userNameTextField.getText(), passwordTextField.getText());
                break;
                }
            else if (user.equals(u.getUserName()) && !userPassword.equals(u.getPassword())){
                if (Locale.getDefault().getLanguage() == "fr") {
                    Alerts.alerts(1);
                    LoginTracker.logAttempt("No", userNameTextField.getText(), passwordTextField.getText());
                    break;
                }
                else {
                    Alerts.alerts(3);
                    LoginTracker.logAttempt("No", userNameTextField.getText(), passwordTextField.getText());
                    break;
                }
            }
            else if (!user.equals(u.getUserName()) && userPassword.equals(u.getPassword())) {
                if (Locale.getDefault().getLanguage() == "fr") {
                    Alerts.alerts(2);
                    LoginTracker.logAttempt("No", userNameTextField.getText(), passwordTextField.getText());
                    break;
                }
                else {
                    Alerts.alerts(4);
                    LoginTracker.logAttempt("No", userNameTextField.getText(), passwordTextField.getText());
                    break;
                }
            }
            else {
                if (Locale.getDefault().getLanguage() == "fr") {
                    Alerts.alerts(1);
                    Alerts.alerts(2);
                    LoginTracker.logAttempt("No", userNameTextField.getText(), passwordTextField.getText());
                    break;
                }
                else {
                    Alerts.alerts(3);
                    Alerts.alerts(4);
                    LoginTracker.logAttempt("No", userNameTextField.getText(), passwordTextField.getText());
                    break;
                }
            }
            } catch (Exception e) {
                e.printStackTrace();
                
            }
        }
    }
}
    
