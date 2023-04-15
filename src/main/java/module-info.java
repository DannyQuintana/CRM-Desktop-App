module schedulingapp.c195advancejavaproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens schedulingapp.c195advancejavaproject to javafx.fxml;
    exports schedulingapp.c195advancejavaproject;
    exports controller;
    opens controller to javafx.fxml;
    exports model;
}