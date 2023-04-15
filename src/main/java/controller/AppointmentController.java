package controller;

import dao.DBAppointment;
import dao.DBContact;
import dao.DBCustomer;
import dao.DBUsers;
import helper.DateTimeUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.Users;
import schedulingapp.c195advancejavaproject.Main;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {
    public TextField appIDField;
    public TextField appTitleField;
    public TextField descriptionField;
    public TextField locationField;
    public Button submitButton;
    public ComboBox<LocalTime> startTimeBox;
    public ComboBox<LocalTime> endTimeBox;
    public TableColumn appIdCol;
    public TableColumn titleCol;
    public TableColumn descripCol;
    public TableColumn locationCol;
    public TableColumn contactCol;
    public TableColumn typeCol;
    public TableColumn startDateTimeCol;
    public TableColumn endDateTimeCol;
    public TableColumn customerIDCol;
    public TableColumn userIDCol;
    public Button upDateButton;
    public TextField typeField;
    public DatePicker datePickerBox;
    public ComboBox<Customer> customerIDBox;
    public ComboBox<Users> userIDBox;
    public ComboBox<Contact> contactBox;
    public TableView AppointmentTableView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Setting Table view
        AppointmentTableView.setItems(DBAppointment.getAllAppointments());

        appIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descripCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        //Setting combo boxes.
        customerIDBox.setItems(DBCustomer.getAllCustomers());
        userIDBox.setItems(DBUsers.getAllUsers());
        contactBox.setItems(DBContact.getAllContacts());

        // Populating localTime boxes
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(20, 15);

        LocalTime  endStart = LocalTime.of(8, 0);
        LocalTime endEnd = LocalTime.of(20, 15);

        while(start.isBefore(end)){
            startTimeBox.getItems().add(start);
            start = start.plusMinutes(15);
        }

        while(endStart.isBefore(endEnd)){
            endTimeBox.getItems().add(endStart);
            endStart = endStart.plusMinutes(15);
        }


    }


    //methods
    public void submitAppointmentClicked(ActionEvent actionEvent) {
        String title = appTitleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        int contact = contactBox.getSelectionModel().getSelectedItem().getContactId();
        String type = typeField.getText();

        //get date and start and end times.
        LocalDate date = datePickerBox.getValue();
        LocalTime start = startTimeBox.getSelectionModel().getSelectedItem();
        LocalTime end = endTimeBox.getSelectionModel().getSelectedItem();


        // Create date times to pass as arguments to DBAppointment converting to UTC time
        LocalDateTime startDateTime = DateTimeUtilities.convertToUTC(date, start);
        LocalDateTime endDateTime = DateTimeUtilities.convertToUTC(date, end);

        int customer = customerIDBox.getSelectionModel().getSelectedItem().getCustomerId();
        int user = userIDBox.getSelectionModel().getSelectedItem().getUserId();

        DBAppointment.addAppointment(title, description, location, type, startDateTime, endDateTime, customer, user, contact);

        AppointmentTableView.setItems(DBAppointment.getAllAppointments());
        clearAllFields();
    }


    public void updateAppointment(ActionEvent actionEvent) {
        Appointment pickedAppointment = (Appointment) AppointmentTableView.getSelectionModel().getSelectedItem();


        if (pickedAppointment != null) {

            submitButton.setDisable(true);
            upDateButton.setDisable(false);

            appIDField.setText(Integer.toString(pickedAppointment.getAppointmentId()));
            appTitleField.setText(pickedAppointment.getTitle());
            descriptionField.setText(pickedAppointment.getDescription());
            locationField.setText(pickedAppointment.getLocation());
            contactBox.getSelectionModel().select(DBContact.getContact(pickedAppointment.getContactId() - 1));
            typeField.setText(pickedAppointment.getType());
            datePickerBox.setValue(DateTimeUtilities.convertToLocalTime(pickedAppointment.getStart()).toLocalDate());
            startTimeBox.getSelectionModel().select(DateTimeUtilities.convertToLocalTime(pickedAppointment.getStart()).toLocalTime());
            endTimeBox.getSelectionModel().select(DateTimeUtilities.convertToLocalTime(pickedAppointment.getEnd()).toLocalTime());
            customerIDBox.getSelectionModel().select(DBCustomer.getCustomer(pickedAppointment.getCustomerId() - 1));
            userIDBox.getSelectionModel().select(DBUsers.getUser(pickedAppointment.getUserId() - 1));


        }
    }



    public void deleteCustomer(ActionEvent actionEvent) {
        Appointment selectedAppointment = (Appointment) AppointmentTableView.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("No appointment selected");
            alert.showAndWait();
        } else {
            String toString = String.valueOf(AppointmentTableView.getSelectionModel().getSelectedItem());
            String parseID = toString.substring(toString.indexOf('[') + 1, toString.indexOf(']'));
            int appointmentID = Integer.parseInt(parseID);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete appointment entry?");
            Optional<ButtonType> select = alert.showAndWait();

            if (select.get() == ButtonType.OK) {
                DBAppointment.deleteAppointment(appointmentID);
                AppointmentTableView.setItems(DBAppointment.getAllAppointments());
            }
        }


    }

    public void toMaineMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainView.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1300, 575);
        stage.setTitle("Appointment Scheduling Application");
        stage.setScene(scene);
        stage.show();
    }

    public void clearClicked(ActionEvent actionEvent) {
        appIDField.setPromptText("Auto Gen ID");
        clearAllFields();

    }

    public void upDateClicked(ActionEvent actionEvent) {
        int appID = Integer.parseInt(appIDField.getText());
        String title = appTitleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();

        LocalDate date = datePickerBox.getValue();
        LocalTime start = startTimeBox.getSelectionModel().getSelectedItem();
        LocalTime end = endTimeBox.getSelectionModel().getSelectedItem();

        LocalDateTime startDateTime = DateTimeUtilities.convertToUTC(date, start);
        LocalDateTime endDateTime = DateTimeUtilities.convertToUTC(date, end);

        int customerId = customerIDBox.getSelectionModel().getSelectedItem().getCustomerId();
        int userId = userIDBox.getSelectionModel().getSelectedItem().getUserId();
        int contactId = contactBox.getSelectionModel().getSelectedItem().getContactId();


        DBAppointment.updateAppointment(appID, title, description, location, type, startDateTime, endDateTime, customerId, userId, contactId);

        AppointmentTableView.setItems(DBAppointment.getAllAppointments());

        clearAllFields();
    }


    private void clearAllFields(){
        submitButton.setDisable(false);
        upDateButton.setDisable(true);
        appIDField.setPromptText("Auto Gen ID");
        appTitleField.clear();
        descriptionField.clear();
        locationField.clear();
        contactBox.valueProperty().setValue(null);
        typeField.clear();
        datePickerBox.valueProperty().setValue(null);
        startTimeBox.valueProperty().setValue(null);
        endTimeBox.valueProperty().setValue(null);
        customerIDBox.valueProperty().setValue(null);
        userIDBox.valueProperty().setValue(null);
    }


    //Field Combo/Time action events
    public void startTimeClicked(ActionEvent actionEvent) {
    }

    public void endTimeClicked(ActionEvent actionEvent) {
    }

    public void datePickerClicked(ActionEvent actionEvent) {
    }

    public void customerIDBoxClicked(ActionEvent actionEvent) {
    }

    public void userIDBoxClicked(ActionEvent actionEvent) {
    }

    public void contactBoxClicked(ActionEvent actionEvent) {
    }
}
