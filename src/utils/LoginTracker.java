/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class used for login reporting.
 * @author indya
 */
public class LoginTracker {
    
    String successfulLogin;
    Date attemptTime;
    
    static String filename = "loginAttempts.txt", item;
    
    public LoginTracker(String successfulLogin, Date attemptTime) {
        this.successfulLogin = successfulLogin;
        this.attemptTime = attemptTime;
    }
    
    public static void logAttempt(String success, String username, String password) throws FileNotFoundException, IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        FileWriter logFile = new FileWriter(filename, true);
        PrintWriter pwLog = new PrintWriter(logFile);
        pwLog.print("Successful Login: " + success + " Username Input: " + username + " Password Input: " + password + " Time: " + formatter.format(date));
        pwLog.print(System.getProperty("line.separator"));
        pwLog.close();
    }
    
}
