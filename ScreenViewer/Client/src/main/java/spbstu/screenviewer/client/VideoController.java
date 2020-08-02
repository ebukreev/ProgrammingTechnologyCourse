package spbstu.screenviewer.client;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public final class VideoController {

    @FXML
    private ImageView imageView;

    @FXML
    void initialize() {
        startScreenView();
    }

    static Task<Void> task;

    public static void mouseHandling(MouseEvent event) {
        int mouseX = (int) event.getSceneX();
        int mouseY = (int) event.getSceneY();
        int click = 0;
        if (event.getEventType()
                .equals(MouseEvent.MOUSE_PRESSED)) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                click = 1;
            } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                click = 3;
            }
        } else if (event.getEventType()
                .equals(MouseEvent.MOUSE_RELEASED)) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                click = 2;
            } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                click = 4;
            }
        }
        try {
            Client.out.write(mouseX * 4 / 3);
            Client.out.write(mouseY * 4 / 3);
            Client.out.write(click);
            Client.out.flush();
        } catch (IOException ignored) {}
    }

    void startScreenView() {
        task = new Task<>() {
            @Override
            public Void call() {
                while (!isCancelled()) {
                    imageView
                            .setImage(new Image(
                                    Client.in,
                                    ViewClient.screenWidth,
                                    ViewClient.screenHeight,
                                    false, false));
                }
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }
}
