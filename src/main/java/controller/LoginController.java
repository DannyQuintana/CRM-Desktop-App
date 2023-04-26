package controller;

import dao.DBConnection;
import dao.DBUsers;
import helper.DateTimeUtilities;
import helper.LoginTrackerUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Users;
import schedulingapp.c195advancejavaproject.Main;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Manages the interaction between the database, view and models.
 */
public class LoginController implements Initializable {
    @FXML private Label timeZoneField;
    @FXML private Label loginTitleLabel;
    @FXML private Label userNameLabel;
    @FXML private Label passwordLabel;
    @FXML private Button login;
    @FXML private Button resetButton;
    @FXML private TextField passWordField;
    @FXML private TextField userNameField;
    @FXML private Label timeZoneLabel;

    /**
     * Retrieves and displays users timezone.
     */
    public void getTimeZone(){
        ZonedDateTime timeZone = ZonedDateTime.now();
        String result = timeZone.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.FULL));
        timeZoneField.setText(result);

    }

    /**
     * Validates user credentials and either rejects or accepts login.
     * @param actionEvent validates customer login with click
     */
    public void loginClicked(ActionEvent actionEvent) {
        try{
            String userName = userNameField.getText();
            String password = passWordField.getText();

            Users user = DBUsers.authorizeUser(DBConnection.getConnection(),userName, password);
            if(user != null){
                LoginTrackerUtility.loginActivity(LocalDateTime.now(), true, userName);
                System.out.println("Access granted");
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainView.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), 1300, 575);
                stage.setTitle("Appointment Scheduling Application");
                stage.setScene(scene);
                stage.show();
                DateTimeUtilities.appointmentAlarm();
            } else {
                LoginTrackerUtility.loginActivity(LocalDateTime.now(), false , userName);
                ResourceBundle bundle = ResourceBundle.getBundle("Login");
                String errorMessage = bundle.getString("errorMessage");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(errorMessage);
                alert.showAndWait();
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Resets user input.
     * @param actionEvent resets field data.
     */
    public void resetClicked(ActionEvent actionEvent) {
        userNameField.setText("");
        passWordField.setText("");
    }


    /**
     * Loads resource bundle for French or English.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResourceBundle bundle = ResourceBundle.getBundle("Login", Locale.getDefault());
        loginTitleLabel.setText(bundle.getString("title"));
        userNameLabel.setText(bundle.getString("userName"));
        passwordLabel.setText(bundle.getString("password"));
        login.setText(bundle.getString("login"));
        resetButton.setText(bundle.getString("resetButton"));
        timeZoneLabel.setText(bundle.getString("timeZone"));
        userNameField.setPromptText(bundle.getString("userNameField"));
        passWordField.setPromptText(bundle.getString("passWordField"));
        getTimeZone();
    }


}