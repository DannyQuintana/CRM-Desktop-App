package controller;

import dao.DBAppointment;
import dao.DBCountries;
import dao.DBCustomer;
import dao.DBDivisions;
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
import model.Countries;
import model.Customer;
import model.Divisions;
import schedulingapp.c195advancejavaproject.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The Customer controller interacts with the view and DB operations, to display, and Perform CRUD operations on
 * the customer, country, and division tables.
 */
public class CustomerController implements Initializable {
    public TextField nameField;
    public TextField addressField;
    public TextField postalCodeField;
    public TextField phoneField;
    public ComboBox<Countries> country;
    public ComboBox<Divisions> state;

    public ObservableList<Countries> countries = DBCountries.getAllCountries();
    public TableView customerTable;
    public TableColumn customerIdCol;
    public TableColumn customerNameCol;
    public TableColumn customerAddressCol;
    public TableColumn customerPostalCol;
    public TableColumn customerPhoneCol;
    public TextField customerIDField;
    public Button upDateButton;
    public Button submitButton;
    public TableColumn divisionID;

    /**
     * Creates new customer record.
     * Validates user input then calls DBCustomer.addCustomer() method.
     */
    public void submitCustomerBtn(ActionEvent actionEvent) throws IOException {
        String name = nameField.getText();
        String address = addressField.getText();
        String postalCode = postalCodeField.getText();
        String phone = phoneField.getText();
        int division = state.getSelectionModel().getSelectedItem().getDivisionId();

        try {
            if (name.isBlank() || address.isBlank() || postalCode.isBlank() || phone.isBlank() || state.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("All forms must be complete before submitting.");
                alert.showAndWait();
            } else {
                DBCustomer.addCustomer(name, address, postalCode, phone, division);
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setContentText("Customer entry saved!");
                alert1.showAndWait();
                customerTable.setItems(DBCustomer.getAllCustomers());
                clearAllFields();
            }
        } catch (RuntimeException error){
            error.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("All forms must be complete before submitting.");
            alert.showAndWait();
        }
    }

    /**
     * Retrives and populates data.
     * Lambda Expression - The lambda expression is used to automatically update the customer info boxes and
     * also enables the use of the update button. This expression is not only a quality of life feature for users,
     * but also avoids adding a button, and action event item to the view.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        country.setItems(countries);
        country.setVisibleRowCount(5);
        int countryID = country.getSelectionModel().getSelectedIndex();
        if (Objects.equals(countryID, 1)) {
            state.setItems(DBDivisions.getAllUSA());
        }

        if (Objects.equals(countryID, 2)) {
            state.setItems(DBDivisions.getAllUK());
        }

        if (Objects.equals(countryID, 3)) {
            state.setItems(DBDivisions.getAllCA());
        };



        customerTable.setItems(DBCustomer.getAllCustomers());

        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionID.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        //Lambda Expression - Automatically populates fields when clicked in tableview.
        customerTable.getSelectionModel().selectedItemProperty().addListener((obs, previousSelection, currentSelection) -> {
            if (currentSelection != null){
                updateCustomerFields();
            }
        });

    }

    /**
     * Populates state combo box.
     * @param actionEvent select country with a click
     */
    public void countryClick(ActionEvent actionEvent) {
        int countryID = country.getSelectionModel().getSelectedIndex();

        if (Objects.equals(countryID, 0)) {
            state.setItems(DBDivisions.getAllUSA());
        }

        if (Objects.equals(countryID, 1)) {
            state.setItems(DBDivisions.getAllUK());
        }

        if (Objects.equals(countryID, 2)) {
            state.setItems(DBDivisions.getAllCA());
        }


    }

    /**
     * Populates text fields with selected customer.
     */
    public void updateCustomerFields(){
        Customer pickedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        submitButton.setDisable(true);
        upDateButton.setDisable(false);

        customerIDField.setText(Integer.toString(pickedCustomer.getCustomerId()));
        nameField.setText(pickedCustomer.getCustomerName());
        addressField.setText(pickedCustomer.getAddress());
        postalCodeField.setText(pickedCustomer.getPostalCode());
        phoneField.setText(pickedCustomer.getPhone());
        country.getSelectionModel().select(DBCountries.getCountry(getCountry()));
        state.getSelectionModel().select(DBDivisions.getDivision(getStateProvidence()));

    }

    /**
     * The getStateProvidence returns the divisionId.
     * @return division
     */
    public int getStateProvidence(){
        ObservableList<Divisions> us = DBDivisions.getAllUSA();
        ObservableList<Divisions> uk = DBDivisions.getAllUK();
        ObservableList<Divisions> canada = DBDivisions.getAllCA();

        Customer pickedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        int divisionId = DBDivisions.getDivision(pickedCustomer.getDivisionId());

        int division = 0;
        int ukCount = 101;
        int caCount = 60;

        if(divisionId > 0 && divisionId <= 54) {
            for(int i = 0; i < us.size(); i++){
                if(i == divisionId){
                    division = i - 1;
                    break;
                }
            }
        }

        if(divisionId >= 101 && divisionId <= 104){
            for(int i = 0; i < uk.size(); i++){
                ukCount++;
                if(ukCount == divisionId){
                    division = i + 1;
                    break;
                }
            }
        }

        if (divisionId >= 60 && divisionId < 73){
            for(int i = 0; i < canada.size(); i++){
                caCount++;
                if(caCount == divisionId){
                    division = i + 1;
                    break;
                }
            }
        }
        System.out.println(division);
        return division;
    }

    /**
     * Selects the country ID.
     * @return country ID
     */
    public int getCountry(){

        Customer pickedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        int divisionId = DBDivisions.getDivision(pickedCustomer.getDivisionId());

        int countryID = -1;

        if(divisionId > 0 && divisionId <= 54){
            countryID = 0;
        }
        if(divisionId >= 101 && divisionId <= 104){
            countryID = 1;
        }

        if (divisionId >= 60 && divisionId < 73){
            countryID = 2;
        }

        return countryID;
    }

    /**
     * Verifies if customer can be deleted.
     * @param customerID selects customer by ID.
     */

    private boolean verifyAppointment(int customerID){
            ObservableList<Appointment> customerAppointments = DBAppointment.getAppointmentByCustomerId(customerID);
            return customerAppointments.size() < 1;
    }


    /**
     * Deletes selected customer record.
     * @param actionEvent deletes customer record on click
     */
    public void deleteCustomer(ActionEvent actionEvent) {

        Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("No customer selected");
            alert.showAndWait();

        } else {
            String toString = String.valueOf(customerTable.getSelectionModel().getSelectedItem());
            String parseID = toString.substring(toString.indexOf('[') + 1, toString.indexOf(']'));
            int customerID = Integer.parseInt(parseID);

            if(verifyAppointment(selectedCustomer.getCustomerId())){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Are you sure you want to delete customer entry?");
                Optional<ButtonType> select = alert.showAndWait();

                if (select.get() == ButtonType.OK) {
                    DBCustomer.deleteCustomer(customerID);

                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setContentText("Customer ID: " + customerID + " has been deleted.");
                    alert1.showAndWait();
                    clearAllFields();
                    customerTable.setItems(DBCustomer.getAllCustomers());

                }
            }else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setContentText("Customer has scheduled appointments. Appointments must be removed prior to deleting customer record.");
                alert2.showAndWait();
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
        DateTimeUtilities.appointmentAlarmMenuReturn();
    }

    /**
     * Updates customer record.
     * The upDateClicked method parses the information from the text-fields and combo-boxes, and passes the fields as
     * arguments for the DBCustomer.updateCustomer() method.
     * @param actionEvent updates customer record on click
     */
    public void upDateClicked(ActionEvent actionEvent) {
        int id = Integer.parseInt(customerIDField.getText());
        String name = nameField.getText();
        String address = addressField.getText();
        String postalCode = postalCodeField.getText();
        String phone = phoneField.getText();
        int division = state.getSelectionModel().getSelectedItem().getDivisionId();

        DBCustomer.updateCustomer(id, name, address, postalCode, phone, division);

        customerTable.setItems(DBCustomer.getAllCustomers());

        clearAllFields();
    }

    /**
     * The clearFields resets each field on the customer form.
     */
    private void clearAllFields(){
        submitButton.setDisable(false);
        upDateButton.setDisable(true);
        customerIDField.clear();
        customerIDField.setPromptText("Auto Gen ID");
        nameField.clear();
        addressField.clear();
        postalCodeField.clear();
        phoneField.clear();
        country.valueProperty().setValue(null);
        state.valueProperty().setValue(null);
    }

    public void clearFields(ActionEvent actionEvent) {
        clearAllFields();
    }
}