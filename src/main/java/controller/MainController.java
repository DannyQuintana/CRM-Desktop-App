/**
 * Manages the interaction between the database, view and models.
 */

package controller;

import dao.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import schedulingapp.c195advancejavaproject.Main;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.temporal.WeekFields;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public AnchorPane allAppointments;
    public TableView AppointmentTableView;
    public TableColumn appIDCol;
    public TableColumn titleCol;
    public TableColumn descripCol;
    public TableColumn locationCol;
    public TableColumn contactCol;
    public TableColumn typeCol;
    public TableColumn startDateTimeCol;
    public TableColumn endDateTimeCol;
    public TableColumn customerIDCol;
    public TableColumn userIDCol;
    public Button closeBtn;

    /**
     * Loads and displays CustomerView
     * @param actionEvent onclick
     * @throws IOException
     */
    @FXML
    public void addCustomerClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addCustomer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads and displays Appointment view
     * @param actionEvent onclick
     * @throws IOException
     */
    public void AppointmentsClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addAppointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1300, 600);
        stage.setTitle("Update Customer");
        stage.setScene(scene);
        stage.show();


    }

    /**
     * Loads and displays reports view
     * @param actionEvent onclick
     * @throws IOException
     */
    public void reportsClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("reportView.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1300, 600);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Filters visible appointments on tableview.
     * Compares week of year to current date and displays the appoitnments within current week of year.
     * @param actionEvent select
     */
    public void weekAppointmentClicked(ActionEvent actionEvent) {

        LocalDate date = LocalDate.now();
        int week = date.get(WeekFields.ISO.weekOfYear());

        AppointmentTableView.setItems(DBAppointment.getWeekAppointments(week));

        appIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descripCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

    }

    /**
     * Filters visible appointments on tableview.
     * Filters appointments to current month.
     * @param actionEvent select
     */
    public void monthAppointmentClicked(ActionEvent actionEvent) {
        LocalDate current = LocalDate.now();

        AppointmentTableView.setItems(DBAppointment.getMonthAppointments(current.getMonthValue()));

        appIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descripCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userId"));


    }

    /**
     * Default view of appointments.
     * All appointments displayed.
     * @param actionEvent
     */
    public void allAppointmentsClicked(ActionEvent actionEvent) {
        AppointmentTableView.setItems(DBAppointment.getAllAppointments());

        appIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descripCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }


    /**
     * Closes DBconnection and program.
     * @param actionEvent
     */
    public void exitClicked(ActionEvent actionEvent) {
        Stage exitBtn = (Stage) closeBtn.getScene().getWindow();
        DBConnection.closeConnection();
        exitBtn.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppointmentTableView.setItems(DBAppointment.getAllAppointments());

        appIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descripCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userId"));


    }
}


