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
        LocalDateTime startDateTime = DateTimeUtilities.convertToUTC(date, start);
        LocalDateTime endDateTime = DateTimeUtilities.convertToUTC(date, end);

        int customer = customerIDBox.getSelectionModel().getSelectedItem().getCustomerId();
        int user = userIDBox.getSelectionModel().getSelectedItem().getUserId();

       boolean overlapConfirm = checkAppointmentOverlap(customer,startDateTime, endDateTime);
        System.out.println(overlapConfirm);
       boolean checkBusinessHours = checkIfESTHours(startDateTime, endDateTime, date);
        System.out.println(checkBusinessHours);

       if(overlapConfirm && checkBusinessHours) {
           DBAppointment.addAppointment(title, description, location, type, startDateTime, endDateTime, customer, user, contact);
           AppointmentTableView.setItems(DBAppointment.getAllAppointments());
           clearAllFields();
           System.out.println("confirmed and ran");
       } else if (!overlapConfirm) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setContentText("Overlapping Appointments, please select another time");
           alert.showAndWait();
       }else {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setContentText("Business Hours must be between 8:00am to 10:00pm EST, please select another time");
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
        stage.setTitle("Main Menu");
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

        boolean overlapConfirm = checkAppointmentOverlap(customerId,startDateTime, endDateTime);
        boolean checkBusinessHours = checkIfESTHours(startDateTime, endDateTime, date);

        if(overlapConfirm && checkBusinessHours) {
            DBAppointment.addAppointment(title, description, location, type, startDateTime, endDateTime, customerId, userId, contactId);
            AppointmentTableView.setItems(DBAppointment.getAllAppointments());
            clearAllFields();
        } else if (!overlapConfirm) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Overlapping Appointments, please select another time");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Business Hours must be between 8:00am to 10:00pm EST, please select another time");
            alert.showAndWait();
        }

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

    private boolean checkIfESTHours(LocalDateTime startDT, LocalDateTime endDT, LocalDate date){
        //Set to ZonedDateTime to be able to evaluate time offset.
        ZonedDateTime startZDT = ZonedDateTime.of(startDT, ZoneId.systemDefault());
        ZonedDateTime endZDT = ZonedDateTime.of(endDT, ZoneId.systemDefault());

        ZonedDateTime openBusinessHours = ZonedDateTime.of(date, LocalTime.of(8,0), ZoneId.of("America/New_York"));
        ZonedDateTime closedBusinessHours = ZonedDateTime.of(date, LocalTime.of(22,0), ZoneId.of("America/New_York"));

        return !startZDT.isBefore(openBusinessHours) &&
                !startZDT.isAfter(closedBusinessHours) &&
                !endZDT.isBefore(openBusinessHours) &&
                !endZDT.isAfter(closedBusinessHours) &&
                !startZDT.isAfter(endZDT);
    }

    private boolean checkAppointmentOverlap(int customerId ,LocalDateTime sLDT, LocalDateTime eLDT) {
        ObservableList<Appointment> customerAppList = DBAppointment.getAppointmentByCustomerId(customerId);
        System.out.println(customerAppList);
        if (!customerAppList.isEmpty()) {
            for (Appointment app : customerAppList) {
                //convert both the times zones into UTC time to be compared.
                LocalDateTime startTime = DateTimeUtilities.convertToUTC(app.getStart());
                LocalDateTime endTime = DateTimeUtilities.convertToUTC(app.getEnd());

                if ((startTime.isBefore(sLDT) || startTime.isEqual(sLDT)) & (endTime.isAfter(eLDT) || endTime.isEqual(eLDT))) {
                    return false;
                }
                if (startTime.isBefore(eLDT) & startTime.isAfter(sLDT)) {
                    return false;
                } else {
                    return !(endTime.isBefore(eLDT) & endTime.isAfter(sLDT));
                }
            }
        }
        return true;
    }
}
