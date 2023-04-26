# Title and Purpose:  WGU C195 - Software II: Advanced Java

This piece of software is a desktop application for managing scheduled appointments and customer information. This program is written using Java and uses a mySQL database to manage these features.

## Dependencies

- IntelliJ Community 2022.02
- Java SE 17.0.2
- JavaFX-SDK-17.0.2
- mysql-connector-java-8.0.32

## Running the Program

1. Open IntelliJ
2. Click File -> Open and navigate to the folder
4. Locate the Main file and build and run the program.

## Navigating the Program

Once running, the application will display a login view. From here, a user may enter "test" as a username and "test" as the password. Alternatively, a user can also sign in with "admin" as the username and "admin" as the password. Once the username and password are properly filled out, click the "Login" button. If successful, the application will display the main menu, and document the login activity. If unsuccessful, an error will prompt the user to retry the password. Failed login attempts will also be documented.

Upon accessing the main menu, the user will be given a notification regarding any upcoming appointments. This notification will always occur during the initial login regardless if there are any impending appointments. After the initial login, this notification will only be displayed when returning to the main menu of the application and if an appointment is within 15 minutes. From the main menu you can view upcoming appointments, above the table view you can filter appointments by week, month, or all. On the left hand side there are four buttons labeled Customer, Appointment, Reports, and exit. If clicked, the Customer button will take you to a customer view where you can view, modify, add, and delete customer records. The Appointment menu will take you to the appointment view where you can also modify appointment records. The Report button will take you to a Report view where you can view Three different reports.

The Customer Menu has a view of all current customers, fields and form to create/modify new ones. In order for you to create a new customer a user will need to fill out each form completely and click the "Create" button. To modify a customer’s record, simply click the customer record in the table view, make the adjustments to the form and click the "Update" button. To delete customer records, click on the customer record and click the Delete button. NOTE: All existing appointments associated with the customer will also be deleted. To clear any selected customer, click the clear button. To return to the main menu, click the "Main Menu" button.

The Appointment Menu has a view of all current appointments, fields and forms to create and modify appointments. To create a new appointment, fill out every form on the left hand side and click the “create” button. To modify and update an existing record click on the record in the table view and then click the Update Appointment button located under the view. Make the changes in the fields and click the “update” button. To clear all current fields click the “clear” button. To Delete an appointment record click on the record to be deleted in the table view and click the Delete Appointment Button. To return to the main menu click “Main Menu”. Note Officer hours are set to 8:00am to 10:00pm EST, the user will need to adjust the times accordingly based on local timezone.

The Report menu displays three reports. Each report is labeled; The first report displays customer appointments by type and month and gives a total number of occurrences. The second displays customer appointments by location and displays occurrences. The last report will filter appointments by contacts. To do this click the combo-box with the prompt “Select Contact…” once selected the tableview will populate with the appointments.

