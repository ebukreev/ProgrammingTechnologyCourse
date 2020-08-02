package spbstu.screenviewer.client;

import java.io.*;
import java.net.Socket;

final class Client {

    private static Client instance;
    private static final int PORT = 8080;
    private static Socket socket;
    static ObjectInputStream in;
    static BufferedWriter out;

    private Client() {}

    static void stopClient() {
        VideoController.task.cancel();
        downService();
    }

    static void startClient(String addr) {
        if (instance == null) {
            try {
                socket = new Socket(addr, PORT);
                in = new ObjectInputStream(socket.getInputStream());
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            } catch (IOException e) {
                downService();
            }
            instance = new Client();
        }
    }

    private static void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException ignored) {}
    }
}
