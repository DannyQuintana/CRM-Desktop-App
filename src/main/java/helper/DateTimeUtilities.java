package helper;

import dao.DBAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Appointment;

import java.time.*;
import java.time.temporal.ChronoUnit;


/**
 * The DateTimeUtilities is used to convert time zones and set meeting notification.
 */
public class DateTimeUtilities {

    /**
     * Converts LocalDateTime objects to UTC ZonedDateTime objects that is returned as a LocalDateTime object.
     * @param local selected user date time.
     * @return a local date time in UTC time.
     */
    public static LocalDateTime convertToUTC(LocalDateTime local){
        ZoneId localZone = ZoneId.systemDefault();
        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime zDT = ZonedDateTime.of(local, localZone);

        ZonedDateTime utcZDT = ZonedDateTime.ofInstant(zDT.toInstant(), utcZone);

        return utcZDT.toLocalDateTime();
    }


    /**
     * Converts LocalDateTime objects to systemDefault time zones.
     * @param lDT selected user date time
     * @return Date and time in users local time.
     */
    public static LocalDateTime convertToLocalTime(LocalDateTime lDT){
        ZoneId local = ZoneId.systemDefault();
        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime zDT = ZonedDateTime.of(lDT, utcZone);
        ZonedDateTime localZDT = ZonedDateTime.ofInstant(zDT.toInstant(), local);

        return localZDT.toLocalDateTime();
    }

    /**
     * Checks a list of appointments and notifies user with an appointment is within 15 minutes of local time.
     */
    public static void appointmentAlarm(){
        ObservableList<Appointment> upcomingAppointment = FXCollections.observableArrayList();
        boolean within15 = false;

            for(Appointment app : DBAppointment.getAllAppointments()) {
                LocalDateTime start = app.getStart();
                LocalDateTime now = LocalDateTime.now();
                long timeDifference = ChronoUnit.MINUTES.between(now, start);
                if (timeDifference > 0 && timeDifference <= 15) {
                    upcomingAppointment.add(app);
                    within15 = true;
                }

                if (within15) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("The following meeting is within the next 15 minutes. ID: " + app.getAppointmentId() +
                            " Start time: " + app.getStart() + " End Time " +
                            app.getEnd() + " .");
                    alert.showAndWait();
                }
            }

            if(upcomingAppointment.isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("No meetings in the next 15 minutes.");
                    alert.showAndWait();
                }
            }

    /**
     * Shows an alarm when returning to menu
     */
    public static void appointmentAlarmMenuReturn(){
        ObservableList<Appointment> upcomingAppointment = FXCollections.observableArrayList();
        boolean within15 = false;

        for(Appointment app : DBAppointment.getAllAppointments()) {
            LocalDateTime start = app.getStart();
            LocalDateTime now = LocalDateTime.now();
            long timeDifference = ChronoUnit.MINUTES.between(now, start);
            if (timeDifference > 0 && timeDifference <= 15) {
                upcomingAppointment.add(app);
                within15 = true;
            }

            if (within15) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("The following meeting is within the next 15 minutes. ID: " + app.getAppointmentId() +
                        " Start time: " + app.getStart() + " End Time " +
                        app.getEnd() + " .");
                alert.showAndWait();
            }
        }
    }
}
