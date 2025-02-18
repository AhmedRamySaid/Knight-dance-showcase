module kyra.me.knights {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens kyra.me.knights to javafx.fxml;
    exports kyra.me.knights;
}