package spbstu.screenviewer.server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    private ObjectInputStream in;
    private BufferedWriter out;
    private Socket socket;

    @BeforeEach
    void setUp() {
        Server.startServer();
        try {
            socket = new Socket("localhost", 8080);
            in = new ObjectInputStream(socket.getInputStream());
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMouse() throws IOException, InterruptedException {
        //do not move the mouse until test results are received, please
        out.write(55);
        out.write(55);
        out.write(0);
        out.flush();
        Thread.sleep(250);
        Point point = MouseInfo.getPointerInfo().getLocation();
        assertTrue(point.x == 55 && point.y == 55);
    }

    @AfterEach
    void tearDown() {
        Server.stopServer();
        try {
            socket.close();
            in.close();
            out.close();
        } catch (Exception ignored) {}
    }
}