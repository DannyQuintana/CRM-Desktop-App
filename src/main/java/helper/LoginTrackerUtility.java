package helper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class LoginTrackerUtility {

    public static void loginActivity(LocalDateTime ldt, boolean wasSuccessful, String username) throws IOException {
        String filename = "src/login_activity.txt";

        FileWriter fw = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fw);
        if(wasSuccessful) {
            outputFile.println("User: " + username + " successfully logged in on the date of: " + DateTimeUtilities.convertToUTC(ldt) + " UTC.");
            outputFile.close();
        }else {
            outputFile.println("User: " + username + " had a failed login attempt on the date of: " + DateTimeUtilities.convertToUTC(ldt) + " UTC.");
            outputFile.close();
        }
    }
}
