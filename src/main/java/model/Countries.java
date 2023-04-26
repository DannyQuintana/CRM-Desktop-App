package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The Countries model contains the fields and methods to be used for Countries objects.
 */
public class Countries {
    private int countryId;
    private String country;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;

    public Countries(){}

    /**
     * Constructor
     * @param countryId country ID
     * @param country country name
     * @param createDate country created on date
     * @param createdBy country created by
     * @param lastUpdated country last updated by date
     * @param lastUpdatedBy country last updated by user
     */
    public Countries(int countryId, String country, LocalDateTime createDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy) {
        this.countryId = countryId;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    //Getters and Setters
    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Override
    public String toString(){
        return country;
    }
}
