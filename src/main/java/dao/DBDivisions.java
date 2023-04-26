package dao;

import dao.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Divisions;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Provides CRUD operations for divisions tables.
 */

public class DBDivisions {

    /**
     * Returns a division by ID.
     * @param divisionID division ID
     * @return division id
     */
    public static int getDivision(int divisionID){

        try {
            String sql = "SELECT Division FROM first_level_divisions WHERE Division_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, divisionID);

        } catch (SQLException e){
            e.printStackTrace();
        }

        return divisionID;
    }

    /**
     * Filters list of divisions depending on Country ID.
     * @return USA divisions
     */
    public static ObservableList<Divisions> getAllUSA(){

        ObservableList<Divisions> divisionsList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 1";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id = rs.getInt("Division_ID");
                String name = rs.getString("Division");

                Timestamp createDate = rs.getTimestamp("Create_Date");
                LocalDateTime createDateTime = createDate.toLocalDateTime();

                String createdBy = rs.getString("Created_By");
                Timestamp last_update = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryId = rs.getInt("Country_ID");

                Divisions d = new Divisions(id, name, createDateTime, createdBy, last_update, lastUpdatedBy, countryId);

                divisionsList.add(d);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return divisionsList;
    }

    /**
     * Returns filtered list of divisions depending on Country ID.
     * @return UK divisions
     */
    public static ObservableList<Divisions> getAllUK(){

        ObservableList<Divisions> divisionsList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 2";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id = rs.getInt("Division_ID");
                String name = rs.getString("Division");

                Timestamp createDate = rs.getTimestamp("Create_Date");
                LocalDateTime createDateTime = createDate.toLocalDateTime();

                String createdBy = rs.getString("Created_By");
                Timestamp last_update = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryId = rs.getInt("Country_ID");

                Divisions d = new Divisions(id, name, createDateTime, createdBy, last_update, lastUpdatedBy, countryId);

                divisionsList.add(d);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return divisionsList;
    }

    /**
     * Returns filtered list of divisions depending on Country ID.
     * @return Canada divisions.
     */
    public static ObservableList<Divisions> getAllCA(){

        ObservableList<Divisions> divisionsList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 3";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id = rs.getInt("Division_ID");
                String name = rs.getString("Division");

                Timestamp createDate = rs.getTimestamp("Create_Date");
                LocalDateTime createDateTime = createDate.toLocalDateTime();

                String createdBy = rs.getString("Created_By");
                Timestamp last_update = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryId = rs.getInt("Country_ID");

                Divisions d = new Divisions(id, name, createDateTime, createdBy, last_update, lastUpdatedBy, countryId);

                divisionsList.add(d);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return divisionsList;
    }
}
