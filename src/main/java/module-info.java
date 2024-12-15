module com.example.recepter {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires kernel;
    requires layout;
    requires jbcrypt;


    opens com.example.Coachify to javafx.fxml;
    exports com.example.Coachify;
}