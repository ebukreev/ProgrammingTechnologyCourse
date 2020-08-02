package spbstu.screenviewer.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewServer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        FlowPane rootNode =
                FXMLLoader.load(getClass().getResource("/StartView.fxml"));

        Scene scene = new Scene(rootNode, 600, 400);

        stage.setTitle("Screen Viewer");
        stage.setScene(scene);

        Server.startServer();

        stage.show();
    }

    @Override
    public void stop() throws Exception {
        Server.stopServer();
        super.stop();
        System.exit(0);
    }
}
