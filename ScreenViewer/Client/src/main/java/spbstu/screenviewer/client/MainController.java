package spbstu.screenviewer.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public final class MainController {

    @FXML
    private TextField needIP;

    @FXML
    private Label warning;

    @FXML
    private void readIP(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            if (Utils.checkIP(needIP.getText())) {
                Client.startClient(needIP.getText());
                VideoView.setVideoView();
            } else {
                if (!warning.isVisible()) {
                    warning.setVisible(true);
                }
            }
        }
    }
}
