package spbstu.screenviewer.client;

import java.io.IOException;

final class VideoView {

    static void setVideoView() throws IOException {
        try {
            ViewClient.screenWidth = Client.in.readInt() / 4 * 3;
            ViewClient.screenHeight = Client.in.readInt() / 4 * 3;
        } catch (IOException e) {
           setVideoView();
        }
        ViewClient.stage.setWidth(ViewClient.screenWidth + 10);
        ViewClient.stage.setHeight(ViewClient.screenHeight + 35);
        ViewClient.stage.centerOnScreen();
        ViewClient.setVideoRoot();
    }
}
