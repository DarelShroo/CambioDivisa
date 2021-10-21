module dad.cambiodivisa2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens dad.cambiodivisa to javafx.fxml;
    exports dad.cambiodivisa;
}