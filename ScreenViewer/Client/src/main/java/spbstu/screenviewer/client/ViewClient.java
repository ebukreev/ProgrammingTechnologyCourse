package spbstu.screenviewer.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewClient extends Application {

    static Stage stage;
    private static Scene scene;
    static int screenHeight;
    static int screenWidth;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        scene = new Scene(loadFXML("StartView"), 600, 400);

        stage.setTitle("Screen Viewer");
        stage.setScene(scene);

        (ViewClient.stage = stage).show();
    }

    static void setVideoRoot() throws IOException {
        scene.setRoot(loadFXML("VideoView"));
        scene.setOnMouseMoved(VideoController::mouseHandling);
        scene.setOnMousePressed(VideoController::mouseHandling);
        scene.setOnMouseReleased(VideoController::mouseHandling);
    }

    private static FlowPane loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(ViewClient.class.getResource("/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @Override
    public void stop() throws Exception {
        Client.stopClient();
        super.stop();
        System.exit(0);
    }
}
