package helper;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * Provides a method to document attempted logins.
 */
public class LoginTrackerUtility {

    /**
     * Saves login attempt to text file.
     * @param ldt LocalDateTime
     * @param wasSuccessful boolean
     * @param username String
     * @throws IOException  catches bad path
     */
    public static void loginActivity(LocalDateTime ldt, boolean wasSuccessful, String username) throws IOException {
        String filename = "login_activity.txt";

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
