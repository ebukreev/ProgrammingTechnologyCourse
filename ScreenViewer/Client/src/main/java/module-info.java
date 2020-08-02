module Client {
    requires javafx.controls;
    requires java.desktop;
    requires javafx.fxml;

    opens spbstu.screenviewer.client to javafx.fxml;
    exports spbstu.screenviewer.client;
}