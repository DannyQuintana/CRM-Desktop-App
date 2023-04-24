package helper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class LoginTrackerUtility {

    public static void loginActivity(LocalDateTime ldt, boolean wasSuccessful) throws IOException {
        String filename = "src/main/activity/login_activity.txt", item;

        FileWriter fw = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fw);

        outputFile.println("Login Attempt made on: " + ldt + " Login successful?: " + wasSuccessful);
        outputFile.close();
    }
}
