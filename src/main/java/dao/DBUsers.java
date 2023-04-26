package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Provides CRUD operations on the Users mySQL table.
 */
public class DBUsers {

    /**
     * Verifies user login credentials.
     * @param connection calls databse connection
     * @param userName user unique name
     * @param password user password
     * @return users information
     */
    public static Users authorizeUser(Connection connection, String userName, String password) {

        try {
            String sql = "SELECT * FROM users WHERE User_Name=? AND Password=?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            Users user = null;
            if (rs.next()) {
                user = new Users();
                user.setName(rs.getString("User_Name"));
                user.setPassword(rs.getString("Password"));
            }
            return user;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a List of users.
     * @return List of users
     */
    public static ObservableList<Users> getAllUsers() {

        ObservableList<Users> usersList = FXCollections.observableArrayList();


        try {
            String sql = "SELECT * from users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String name = rs.getString("User_Name");
                String password = rs.getString("Password");

                Timestamp createDate = rs.getTimestamp("Create_Date");
                LocalDateTime createDateTime = createDate.toLocalDateTime();

                String createdBy = rs.getString("Created_By");
                Timestamp last_update = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                Users u = new Users(id, name, password, createDateTime, createdBy, last_update, lastUpdatedBy);

                usersList.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usersList;
    }

    /**
     * Returns a users record by user ID.
     * @param userID user ID
     * @return user ID
     */
    public static int getUser(int userID) {

        try {
            String sql = "SELECT User_ID FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, userID);

        } catch (SQLException e){
            e.printStackTrace();
        }

        return userID;
    }
}
