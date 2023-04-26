package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The Appointment model is a class that contains all the required fields and methods for Appointment Objects
 * */
public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    //Foreign keys include
    private int customerId;
    private int userId;
    private int contactId;
    //Overload fields
    private int total;
    private String month;


    /**
     * Overload used retrieve month and type.
     * This overload is used in the Reports controller
    */
    public Appointment(String month, String type, int total) {
        this.month = month;
        this.type = type;
        this.total = total;
    }

    /**
     * Overload used to retrieve customerId and meeting location. T
     * his Overload method is used in the reports' controller.
     */
    public Appointment(int customerId, String location, int total){
        this.customerId = customerId;
        this.location = location;
        this.total = total;
    }

    /**
     * Constructor
     * @param appointmentId appointment ID
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param start appointment start time
     * @param end appointment end time
     * @param createDate appointment created on date
     * @param createdBy appointment created by user
     * @param lastUpdate appointment last update time
     * @param lastUpdatedBy appoinment last updated by user
     * @param customerId customer ID
     * @param userId user ID
     * @param contactId contact ID
     */
    public Appointment(int appointmentId, String title, String description, String location, String type, LocalDateTime start,
                       LocalDateTime end, LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy,
                       int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    //Getters and setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }


    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }


    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
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

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }


    /**
     * This toString formats and returns a more decipherable String.
     * @return
     */
    @Override
    public String toString(){
        return "[" + appointmentId + "] " + title + " " + description + " " + location + " " + type + " " + start
                + " " + end + " " + createDate + " " + createdBy + " "+ lastUpdate + " " + lastUpdatedBy + " "
                + customerId + " " + userId + " " + contactId;
    }
}
