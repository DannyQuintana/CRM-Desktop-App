package controller;

import dao.DBAppointment;
import dao.DBContact;
import dao.DBCustomer;
import dao.DBUsers;
import helper.DateTimeUtilities;
import javafx.collections.ObservableList;
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
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.*;
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
    public TableView<Appointment> AppointmentTableView;


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

        LocalTime start = LocalTime.of(1, 0);
        LocalTime end = LocalTime.of(23, 45);

        LocalTime endStart = LocalTime.of(1, 0);
        LocalTime endEnd = LocalTime.of(23, 45);

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
        LocalDateTime startDateTime = LocalDateTime.of(date, start);
        LocalDateTime endDateTime = LocalDateTime.of(date, end);

        int customer = customerIDBox.getSelectionModel().getSelectedItem().getCustomerId();
        int user = userIDBox.getSelectionModel().getSelectedItem().getUserId();
            if (title.isBlank() || description.isBlank() || location.isBlank() || type.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Fields can not be left blank, please check the form to ensure all fields have values.");
                alert.showAndWait();
            } else if (startDateTime.isAfter(endDateTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Start time needs to be before end time.");
                alert.showAndWait();
            } else if (endDateTime.isBefore(startDateTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("End time must be greater than start time.");
                alert.showAndWait();
            } else if (startDateTime.isEqual(endDateTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Start and End time can not be equal");
                alert.showAndWait();
            } else {

                if (checkAppointmentOverlap(customer, startDateTime, endDateTime) && checkIfESTHours(startDateTime, endDateTime, date)) {
                    DBAppointment.addAppointment(title, description, location, type, DateTimeUtilities.convertToUTC(startDateTime), DateTimeUtilities.convertToUTC(endDateTime), customer, user, contact);
                    AppointmentTableView.setItems(DBAppointment.getAllAppointments());
                    clearAllFields();
                } else if (!checkIfESTHours(startDateTime, endDateTime, date)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("The selected time are outside of Business hours. The business hours must be between 8:00am to 10:00pm EST");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Customer is already scheduled for another appointment between the selected times: " + startDateTime + " and " + endDateTime + ". Please Select another time.");
                    alert.showAndWait();
                }
            }
    }

    public void upDateClicked(ActionEvent actionEvent) {
        int id = Integer.parseInt(appIDField.getText());
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

        boolean overlapConfirm = checkAppointmentOverlap(customerId,startDateTime, endDateTime);
        boolean checkBusinessHours = checkIfESTHours(startDateTime, endDateTime, date);

        if(overlapConfirm && !checkBusinessHours) {
            DBAppointment.updateAppointment(id, title, description, location, type, startDateTime, endDateTime, customerId, userId, contactId);
            AppointmentTableView.setItems(DBAppointment.getAllAppointments());
            clearAllFields();
        } else if (!overlapConfirm) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Overlapping Appointments, please select another time");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Business Hours must be between 8:00am to 10:00pm EST Monday to Friday, please select another Date or time");
            alert.showAndWait();
        }
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
            startTimeBox.getSelectionModel().select(pickedAppointment.getStart().toLocalTime());
            endTimeBox.getSelectionModel().select(pickedAppointment.getEnd().toLocalTime());
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
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public void clearClicked(ActionEvent actionEvent) {
        appIDField.setPromptText("Auto Gen ID");
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


    private boolean checkIfESTHours(LocalDateTime proposedStart, LocalDateTime proposedEnd, LocalDate date){
        //First we will create the hours parameters of 8:00am to 10:00pm EST Time
        LocalDateTime openBusinessHours = LocalDateTime.of(date, LocalTime.of(8, 0));
        LocalDateTime closedBusinessHours = LocalDateTime.of(date, LocalTime.of(22, 0));

        //Next convert the proposedStart and ProposedEnd to EST time
        proposedStart.atZone(ZoneId.of("America/New_York"));
        proposedEnd.atZone(ZoneId.of("America/New_York"));

        //Defining work week
        DayOfWeek saturday = DayOfWeek.SATURDAY;
        DayOfWeek sunday = DayOfWeek.SUNDAY;

        //next Compare the business hours to the start and end
        if(proposedStart.isBefore(openBusinessHours) || proposedStart.isAfter(closedBusinessHours) || (date.getDayOfWeek() == saturday || date.getDayOfWeek() == sunday)){
            return false;
        } else {
            return !proposedEnd.isBefore(openBusinessHours) && !proposedEnd.isAfter(closedBusinessHours);
        }
    }

    private boolean checkAppointmentOverlap(int customerId ,LocalDateTime proposedStart, LocalDateTime proposedEnd) {
        //Create a list of each appointment that a customer is attached too. The customer ID variable will come from the combo box.
        ObservableList<Appointment> customerAppList = DBAppointment.getAppointmentByCustomerId(customerId);
        System.out.println(customerAppList);

        //Check if List is empty. If the list has items we need toLoop through each item and compare the start and end times to the proposedStart and endTimes.
        if(!customerAppList.isEmpty()) {
            for (Appointment app : customerAppList) {
                LocalDateTime startTime = app.getStart();
                LocalDateTime endTime = app.getEnd();
                System.out.println("Start Time:" + startTime);
                System.out.println("End Time: " + endTime);

                //If startTime is before or equal (proposed >= start AND start < End)
                if( (proposedStart.isAfter(startTime) || proposedStart.isEqual(startTime)) && proposedStart.isBefore(endTime)) {
                    return false;
                }
                //proposed end is After the start time but is before the end of the meeting
                if(proposedEnd.isAfter(startTime) && (proposedEnd.isBefore(endTime) || proposedEnd.isEqual(endTime)) ){
                    return false;
                }
                //proposed time is before and after the start time
                if((proposedStart.isBefore(startTime) || proposedStart.isEqual(startTime)) && (proposedEnd.isAfter(endTime) || proposedEnd.isEqual(endTime))){
                    return false;
                }
            }
        }
        return true;
    }



}
