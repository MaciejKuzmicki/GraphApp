module com.example.jaguar {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jfr;


    opens com.example.jaguar to javafx.fxml;
    exports com.example.jaguar;
}