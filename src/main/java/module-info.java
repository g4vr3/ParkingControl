module dam.parkingcontrol {
    requires javafx.controls;
    requires javafx.fxml;


    opens dam.parkingcontrol to javafx.fxml;
    exports dam.parkingcontrol;
    exports dam.parkingcontrol.controller;
    opens dam.parkingcontrol.controller to javafx.fxml;
}