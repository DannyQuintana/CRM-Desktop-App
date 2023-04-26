/**
 * Provides CRUD operations for the appointments mySQL table.
 */

package dao;

import helper.DateTimeUtilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.*;
import java.time.LocalDateTime;

public class DBAppointment {

    /**
     * Returns a List of appointments.
     * @return ObservableList Appointment
     */
    public static ObservableList<Appointment> getAllAppointments(){

        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");

                Timestamp tsStart = rs.getTimestamp("Start");
                LocalDateTime start = tsStart.toLocalDateTime();

                Timestamp tsEnd = rs.getTimestamp("End");
                LocalDateTime end = tsEnd.toLocalDateTime();

                Timestamp tsCreateDate = rs.getTimestamp("Create_Date");
                LocalDateTime createDate = tsCreateDate.toLocalDateTime();

                String createdBy = rs.getString("Created_By");

                Timestamp tsLastUpdate = rs.getTimestamp("Last_Update");

                String lastUpdatedBy = rs.getString("Last_Updated_By");

                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment aa = new Appointment(id, title, description, location, type, DateTimeUtilities.convertToLocalTime(start), DateTimeUtilities.convertToLocalTime(end), createDate, createdBy,
                        tsLastUpdate, lastUpdatedBy, customerID, userID, contactId);

                appointmentList.add(aa);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return appointmentList;
    }

    /**
     * Filters appointments collection by months.
     * @param month int
     * @return ObservableList
     */
    public static ObservableList<Appointment> getMonthAppointments(int month){

        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try{
            String sql = "select * from client_schedule.appointments where month(Start) = ?;  ";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, month);
            ResultSet rs = ps.executeQuery();


            while(rs.next()){
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");

                Timestamp tsStart = rs.getTimestamp("Start");
                LocalDateTime start = tsStart.toLocalDateTime();

                Timestamp tsEnd = rs.getTimestamp("End");
                LocalDateTime end = tsEnd.toLocalDateTime();

                Timestamp tsCreateDate = rs.getTimestamp("Create_Date");
                LocalDateTime createDate = tsCreateDate.toLocalDateTime();

                String createdBy = rs.getString("Created_By");

                Timestamp tsLastUpdate = rs.getTimestamp("Last_Update");

                String lastUpdatedBy = rs.getString("Last_Updated_By");

                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment aa = new Appointment(id, title, description, location, type, start, end, createDate, createdBy,
                        tsLastUpdate, lastUpdatedBy, customerID, userID, contactId);

                appointmentList.add(aa);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return appointmentList;
    }

    /**
     * Filters Filters appointments collection by week
     * @param week int
     * @return ObservableList
     */
    public static ObservableList<Appointment> getWeekAppointments(int week){

        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();


        try{
            String sql = "select * from client_schedule.appointments where weekofyear(Start) = ?;  ";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, week);
            ResultSet rs = ps.executeQuery();


            while(rs.next()){
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");

                Timestamp tsStart = rs.getTimestamp("Start");
                LocalDateTime start = tsStart.toLocalDateTime();

                Timestamp tsEnd = rs.getTimestamp("End");
                LocalDateTime end = tsEnd.toLocalDateTime();

                Timestamp tsCreateDate = rs.getTimestamp("Create_Date");
                LocalDateTime createDate = tsCreateDate.toLocalDateTime();

                String createdBy = rs.getString("Created_By");

                Timestamp tsLastUpdate = rs.getTimestamp("Last_Update");

                String lastUpdatedBy = rs.getString("Last_Updated_By");

                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment aa = new Appointment(id, title, description, location, type, start, end, createDate, createdBy,
                        tsLastUpdate, lastUpdatedBy, customerID, userID, contactId);

                appointmentList.add(aa);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return appointmentList;
    }

    /**
     * Create a appointment record.
     * @param title String
     * @param description String
     * @param location String
     * @param type String
     * @param start LocalDateTime
     * @param end LocalDateTime
     * @param customerID int
     * @param userID int
     * @param contactID int
     */
    public static void addAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID,  int contactID){

        Timestamp createDate = Timestamp.valueOf(LocalDateTime.now());

        String createdBy = "User";

        try {

            String sql = "INSERT into appointments (Title, Description, Location, Type, Start, End, " +
                    "Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)VALUES ('"+title+"', '"+description+"', '"+location+"', '"+type+"', " +
                    "'"+start+"', '"+end+"', '"+createDate+"', '"+createdBy+"', '"+createDate+"', '"+createdBy+"', '"+customerID+"','"+userID+"', '"+contactID+"' )";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Deletes appointment by Appointment ID number.
     * @param ID int
     */
    public static void deleteAppointment(int ID) {
        try {
            if (ID > 0) {
                String sql = "DELETE FROM appointments WHERE Appointment_ID= " + ID;

                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                ps.executeUpdate();

            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Updates existing appointment record.
     * @param appID int
     * @param title String
     * @param description String
     * @param location String
     * @param type String
     * @param start LocalDateTime
     * @param end LocalDateTime
     * @param customerID int
     * @param userID int
     * @param contactID int
     */
    public static void updateAppointment(int appID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID,  int contactID){
        try{
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Type = ?, Start = ?, End= ?, Customer_ID = ?, User_ID =?, Contact_ID = ? Where Appointment_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(4, Timestamp.valueOf(start));
            ps.setTimestamp(5, Timestamp.valueOf(end));
            ps.setInt(6, customerID);
            ps.setInt(7, userID);
            ps.setInt(8, contactID);
            ps.setInt(9, appID);

            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    /**
     * Filters appointments by customer ID
     * @param customerId
     * @return
     */
    public static ObservableList<Appointment> getAppointmentByCustomerId(int customerId) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE Customer_ID= " + customerId;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");

                Timestamp tsStart = rs.getTimestamp("Start");
                LocalDateTime start = tsStart.toLocalDateTime();

                Timestamp tsEnd = rs.getTimestamp("End");
                LocalDateTime end = tsEnd.toLocalDateTime();

                Timestamp tsCreateDate = rs.getTimestamp("Create_Date");
                LocalDateTime createDate = tsCreateDate.toLocalDateTime();

                String createdBy = rs.getString("Created_By");

                Timestamp tsLastUpdate = rs.getTimestamp("Last_Update");

                String lastUpdatedBy = rs.getString("Last_Updated_By");

                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment aa = new Appointment(id, title, description, location, type, DateTimeUtilities.convertToLocalTime(start), DateTimeUtilities.convertToLocalTime(end), createDate, createdBy,
                        tsLastUpdate, lastUpdatedBy, customerID, userID, contactId);

                appointmentList.add(aa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Returns a filtered table that includes Month and Appointment type. The count() is used to see number of occurrences.
     * @return ObservableList Appointment
     */
    public static ObservableList<Appointment> getTypeMonthReport(){

        ObservableList<Appointment> typeMonthReportList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT MONTHNAME(Start) \"Month\", Type as \"Type\", count(Start) as \"Total\"FROM client_schedule.appointments GROUP by Month, Type;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();


            while(rs.next()){
                String month = rs.getString("Month");
                String type = rs.getString("Type");
                int total = rs.getInt("Total");

                Appointment aa = new Appointment(month, type, total);
                typeMonthReportList.add(aa);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return typeMonthReportList;
    }

    /**
     * Returns a filtered table that counts the number of occurrences of appointment Location by CustomerID.
     * @return ObservableList
     */
    public static ObservableList<Appointment> getAppLocationReport(){

        ObservableList<Appointment> appLocationReportList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT Customer_ID, Location, count(Location) as \"Total\" from client_schedule.appointments group by Customer_ID, Location;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();


            while(rs.next()){
                int customerID = rs.getInt("Customer_ID");
                String location = rs.getString("Location");
                int total = rs.getInt("Total");

                Appointment aa = new Appointment(customerID, location, total);
                appLocationReportList.add(aa);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return appLocationReportList;
    }

    /**
     * Returns a filtered List of appointments by contactID
     * @param contactID
     * @return
     */
    public static ObservableList<Appointment> getAppointmentByContactId(int contactID) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE Contact_ID= " + contactID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");

                Timestamp tsStart = rs.getTimestamp("Start");
                LocalDateTime start = tsStart.toLocalDateTime();

                Timestamp tsEnd = rs.getTimestamp("End");
                LocalDateTime end = tsEnd.toLocalDateTime();

                Timestamp tsCreateDate = rs.getTimestamp("Create_Date");
                LocalDateTime createDate = tsCreateDate.toLocalDateTime();

                String createdBy = rs.getString("Created_By");

                Timestamp tsLastUpdate = rs.getTimestamp("Last_Update");

                String lastUpdatedBy = rs.getString("Last_Updated_By");

                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment aa = new Appointment(id, title, description, location, type, DateTimeUtilities.convertToLocalTime(start), DateTimeUtilities.convertToLocalTime(end), createDate, createdBy,
                        tsLastUpdate, lastUpdatedBy, customerID, userID, contactId);

                appointmentList.add(aa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentList;
    }


}
