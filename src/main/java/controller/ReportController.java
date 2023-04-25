package controller;

import dao.DBAppointment;
import dao.DBContact;
import helper.DateTimeUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import schedulingapp.c195advancejavaproject.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportController implements Initializable{
    public TableView<Appointment> appByTypeMonthTable;
    public TableColumn<?, ?> appMonth;
    public TableColumn<?,?> appType;
    public TableColumn<?,?> appTotal;
    public ComboBox<Contact> contactBox;
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
    public TableView<Appointment> contactTableView;
    public TableView<Appointment> appLocationTable;
    public TableColumn locationCustomerID;
    public TableColumn localLocation;
    public TableColumn localTotal;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appByTypeMonthTable.setItems(DBAppointment.getTypeMonthReport());
        appMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        appType.setCellValueFactory(new PropertyValueFactory<>("type"));
        appTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        contactBox.setItems(DBContact.getAllContacts());

        appLocationTable.setItems(DBAppointment.getAppLocationReport());
        locationCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        localLocation.setCellValueFactory(new PropertyValueFactory<>("Location"));
        localTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));



    }

    public void contactBoxClicked(ActionEvent actionEvent) {

        contactTableView.setItems(DBAppointment.getAppointmentByContactId(contactBox.getSelectionModel().getSelectedItem().getContactId()));
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

    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainView.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1300, 575);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
        DateTimeUtilities.appointmentAlarmMenuReturn();
    }
}
