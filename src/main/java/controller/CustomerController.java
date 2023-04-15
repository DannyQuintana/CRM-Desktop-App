package controller;

import dao.DBCountries;
import dao.DBCustomer;
import dao.DBDivisions;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Countries;
import model.Customer;
import model.Divisions;
import schedulingapp.c195advancejavaproject.Main;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

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

    public void submitCustomerBtn(ActionEvent actionEvent) throws IOException {
        String name = nameField.getText();
        String address = addressField.getText();
        String postalCode = postalCodeField.getText();
        String phone = phoneField.getText();
        int division = state.getSelectionModel().getSelectedItem().getDivisionId();


        DBCustomer.addCustomer(name, address, postalCode, phone, division);

        customerTable.setItems(DBCustomer.getAllCustomers());
        clearAllFields();

    }

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
        }

        customerTable.setItems(DBCustomer.getAllCustomers());

        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionID.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

    }

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

    public void updateCustomerClicked(ActionEvent actionEvent) {
        Customer pickedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();

        if (pickedCustomer != null) {

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
    }

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

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete customer entry?");
            Optional<ButtonType> select = alert.showAndWait();

            if (select.get() == ButtonType.OK) {
                DBCustomer.deleteCustomer(customerID);
                customerTable.setItems(DBCustomer.getAllCustomers());
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

    public void clearFields(ActionEvent actionEvent) {
        clearAllFields();
    }

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
}