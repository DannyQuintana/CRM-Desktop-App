package schedulingapp.c195advancejavaproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import static dao.DBConnection.*;

/**
 * Entry point to the program*/
public class Main extends Application {
    /**
     * Starts JavaFX.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 500);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Starts program JDBC and JavaFX
     * */
    public static void main(String[] args) {

        openConnection();
        launch();

        closeConnection();
    }
}