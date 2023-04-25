package schedulingapp.c195advancejavaproject;

import dao.DBAppointment;
import dao.DBContact;
import dao.DBCustomer;
import dao.DBDivisions;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Contact;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;

import static dao.DBConnection.*;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        openConnection();
        launch();

        closeConnection();
    }
}