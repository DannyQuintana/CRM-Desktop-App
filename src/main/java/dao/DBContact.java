package dao;

import dao.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides CRUD operations for the Customers mySQL Table.
 */
public class DBContact {
    /**
     * Returns a list of all contacts.
     * @return ObservableList of contacts
     */
    public static ObservableList<Contact> getAllContacts (){
        ObservableList<Contact> contactList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM contacts";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contact c = new Contact(id, name, email);
                contactList.add(c);
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
        return contactList;
    }

    /**
     * Returns a contact record by ID.
     * @param contactID contact ID
     * @return contact ID
     */

    public static int getContact(int contactID) {

        try {
            String sql = "SELECT Contact_ID FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, contactID);

        } catch (SQLException e){
            e.printStackTrace();
        }

        return contactID;
    }
}
