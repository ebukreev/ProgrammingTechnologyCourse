package spbstu.screenviewer.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

final class Server extends Thread {

    private static Server instance;
    private static final int PORT = 8080;
    private static Socket socket = null;
    private static final Logger log = Logger.getLogger(Server.class);
    static ObjectOutputStream out = null;
    static BufferedReader in;

    private Server() {}

    static void startServer() {
        if (instance == null) {
            instance = new Server();
            instance.start();
            log.info("server is running");
        }
    }

    static void stopServer() {
        ShareScreen.currentThread().interrupt();
        MouseUsing.currentThread().interrupt();
        downService();
        instance.interrupt();
        log.info("server is stopped");
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            log.info("client is not connected");
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                socket = serverSocket.accept();
                try {
                    out = new ObjectOutputStream(socket.getOutputStream());
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    ShareScreen.sendScreenSize();
                    new ShareScreen().start();
                    new MouseUsing().start();
                } catch (IOException e) {
                    downService();
                }
            } catch (IOException e) {
                downService();
            }
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