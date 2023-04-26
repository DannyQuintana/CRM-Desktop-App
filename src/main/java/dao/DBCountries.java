package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Provides CRUD operations for the Countries mySQL Table.
 */
public class DBCountries {


    /**
     * Returns a list of countries.
     * @return ObservableList of countries.
     */
    public static ObservableList<Countries> getAllCountries(){
        ObservableList<Countries> countriesList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int countryID = rs.getInt("Country_ID");
                String country = rs.getString("Country");

                Timestamp createDate = rs.getTimestamp("Create_Date");
                LocalDateTime createDateTime = createDate.toLocalDateTime();

                String createdBy = rs.getString("Created_By");
                Timestamp tsLastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                Countries c = new Countries(countryID, country, createDateTime, createdBy, tsLastUpdate, lastUpdatedBy);
                countriesList.add(c);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return countriesList;
    }

    /**
     * Returns a country record by countryID.
     * @param countryID country ID
     * @return country ID
     */

    public static int getCountry(int countryID) {

        try {
            String sql = "SELECT Country FROM Countries WHERE Country_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, countryID);

        } catch (SQLException e){
            e.printStackTrace();
        }

        return countryID;
    }
}
