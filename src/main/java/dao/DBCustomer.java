package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;


/**
 * Provides CRUD operations for the customer mySQL table.
 */
public class DBCustomer  {

    /**
     * Returns a list of customers.
     * @return List of customers.
     */
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

    /**
     * Creates a record on the customers table.
     * @param name customer name
     * @param address customer address
     * @param postalCode customer postal code
     * @param phone customer phone
     * @param divisionID customer division id.
     */
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

    /**
     * Updates a record on the customers table.
     * @param id customer id
     * @param name customer name
     * @param address customer address
     * @param postalCode customer postal code
     * @param phone customer phone
     * @param divisionID customer division ID
     */
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


    /**
     * Deletes a customer record by ID.
     * @param ID customer ID
     */
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

    /**
     * Returns a customer record by customerID.
     * @param customerID customer ID
     * @return returns a customer ID
     */
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
