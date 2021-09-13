/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;

/**
 * Class used to proved alerts throughout the application.
 * @author indya
 */
public class Alerts {
    public static void alerts (int alertCase) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        switch (alertCase) {
            case 1:
                ResourceBundle rsb = ResourceBundle.getBundle("schedulingapplication/Nat", Locale.getDefault());
                a.setTitle(rsb.getString("Error"));
                a.setHeaderText(rsb.getString("Unsuccessful Login"));
                a.setContentText(rsb.getString("Password incorrect, please try again."));
                a.showAndWait();
                break;
            case 2:
                ResourceBundle rsa = ResourceBundle.getBundle("schedulingapplication/Nat", Locale.getDefault());
                a.setTitle(rsa.getString("Error"));
                a.setHeaderText(rsa.getString("Unsuccessful Login"));
                a.setContentText(rsa.getString("Username incorrect, please try again."));
                a.showAndWait();
                break;
            case 3:
                a.setTitle("Error");
                a.setHeaderText("Unsuccessful Login");
                a.setContentText("Password incorrect, please try again.");
                a.showAndWait();
                break;
            case 4:
                a.setTitle("Error");
                a.setHeaderText("Unsuccessful Login");
                a.setContentText("Username incorrect, please try again.");
                a.showAndWait();
                break;
            case 5:
                a.setTitle("Error");
                a.setHeaderText("Could not save appointment");
                a.setContentText("This appointment time conflicts with another appointment for the selected customer.");
                a.showAndWait();
                break;
            case 6:
                a.setTitle("Error");
                a.setHeaderText("Could not save appointment");
                a.setContentText("The times you selected are not within business hours. Business hours are from 8:00 AM - 10:00 PM EST.");
                a.showAndWait();
                break;
            case 7:
                a.setTitle("Error");
                a.setHeaderText("Could not save customer.");
                a.setContentText("You must fill out all fields in the form.");
                a.showAndWait();
                break;
            case 8:
                a.setTitle("Error");
                a.setHeaderText("Could not save appointment.");
                a.setContentText("You must fill out all fields in the form.");
                a.showAndWait();
                break;
        }
    }
}
