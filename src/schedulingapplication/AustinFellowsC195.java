/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulingapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import dao.DBConnection;
import java.sql.SQLException;
import java.sql.Connection;

/**
 * Main Class to run application.
 * @author indya
 */
public class AustinFellowsC195 extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {

        DBConnection.startConnection();
        Connection conn = DBConnection.getConnection();
        
        
        launch(args);
        DBConnection.closeConnection();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
    
}
