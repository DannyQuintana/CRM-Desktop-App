package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.util.converter.LocalDateTimeStringConverter;
import model.Customer;
import model.Divisions;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static dao.DBConnection.openConnection;

public class DBCustomer  {

    public static ObservableList<Customer> getAllCustomers(){

        ObservableList<Customer> customerList = FXCollections.observableArrayList();


        try {
            String sql = "SELECT * from customers";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");

                Timestamp createDate = rs.getTimestamp("Create_Date");
                LocalDateTime createDateTime = createDate.toLocalDateTime();

                String createdBy = rs.getString("Created_By");
                Timestamp last_update = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divisionId = rs.getInt("Division_ID");

                Customer cc = new Customer(id, name, address, postalCode, phone, createDateTime, createdBy, last_update,
                        lastUpdatedBy, divisionId);

                customerList.add(cc);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return customerList;
    }


    public static String getCustomersName(long ID){

        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        String name = "";

        try {
            String sql = "SELECT Customer_Name FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setLong(1, ID);

            ResultSet rs = ps.executeQuery();
            name = rs.toString();

        } catch (SQLException e){
            e.printStackTrace();
        }
        return name;
    }


    public static void addCustomer(String name, String address, String postalCode, String phone, int divisionID){

        Timestamp createDate = Timestamp.valueOf(LocalDateTime.now());


        String createdBy = "User";

        try {
            String sql = "INSERT into customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, " +
                    "Last_Update, Last_Updated_By, Division_ID)VALUES ('"+name+"', '"+address+"', '"+postalCode+"', " +
                    "'"+phone+"', '"+createDate+"', '"+createdBy+"', '"+createDate+"', '"+createdBy+"', '"+divisionID+"')";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateCustomer(int id, String name, String address, String postalCode, String phone, int divisionID){

        Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());

        String createdBy = "User";

        try{
            String sql = "UPDATE customers SET Customer_Name= ?, Address= ?, Postal_Code = ?, " +
                    "Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ?   WHERE Customer_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setTimestamp(5, lastUpdate);
            ps.setString(6, createdBy);
            ps.setInt(7, divisionID);
            ps.setInt(8, id);

            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }



    public static void deleteCustomer(int ID){

        try {
            if (ID > 0) {
                String sql = "DELETE FROM customers WHERE Customer_ID = " + ID;

                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                ps.executeUpdate();
                
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static int getCustomer(int customerID) {
        try {
            String sql = "SELECT Customer_ID FROM appointments WHERE Customer_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, customerID);

        } catch (SQLException e){
            e.printStackTrace();
        }

        return customerID;
    }

}
