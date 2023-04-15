package helper;

import dao.DBAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Appointment;

import java.sql.SQLException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class DateTimeUtilities {


    public static LocalDateTime convertToUTC(LocalDate lDate, LocalTime lTime){
        LocalDateTime local = LocalDateTime.of(lDate, lTime);
        ZoneId localZone = ZoneId.systemDefault();
        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime zDT = ZonedDateTime.of(local, localZone);

        ZonedDateTime utcZDT = ZonedDateTime.ofInstant(zDT.toInstant(), utcZone);

        return utcZDT.toLocalDateTime();
    }

    public static LocalDateTime convertToEST (LocalDateTime lDT){
        ZoneId local = ZoneId.systemDefault();
        ZoneId estZone = ZoneId.of("EST");
        ZonedDateTime zDT = ZonedDateTime.of(lDT, local);
        ZonedDateTime etsZDT = ZonedDateTime.ofInstant(zDT.toInstant(), estZone);

        return etsZDT.toLocalDateTime();
    }

    public static LocalDateTime convertToLocalTime(LocalDateTime lDT){
        ZoneId local = ZoneId.systemDefault();
        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime zDT = ZonedDateTime.of(lDT, utcZone);
        ZonedDateTime localZDT = ZonedDateTime.ofInstant(zDT.toInstant(), local);

        return localZDT.toLocalDateTime();
    }

    public static void appointmentAlarm(){
        ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();
        try {
            for(Appointment app : DBAppointment.getAllAppointments()){
                LocalDateTime start = app.getStart();
                if(start.isAfter(( LocalDateTime.now())) && start.isBefore(start.plusMinutes(15))){
                    upcomingAppointments.add(app);
                }
            }

            if(upcomingAppointments.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("No meetings in the next 15 minutes.");
                alert.showAndWait();
            } else {
                for(Appointment app : upcomingAppointments){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("The following meeting are within 15 minutes ID: "+ app.getAppointmentId() +
                            " Start time: " +DateTimeUtilities.convertToLocalTime(app.getStart()) + " End Time " +
                            DateTimeUtilities.convertToLocalTime( app.getEnd() )+ " .");
                    alert.showAndWait();
                }
            }

        } catch(Exception e){
            e.printStackTrace();
        }


    }
}
