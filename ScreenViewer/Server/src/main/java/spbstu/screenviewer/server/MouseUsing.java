package spbstu.screenviewer.server;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.IOException;


final class MouseUsing extends Thread {

    @Override
    public void run() {
        int mouseX;
        int mouseY;
        MouseButtons click;
        try {
            Robot robot = new Robot();
            robot.setAutoWaitForIdle(true);
            while (!isInterrupted()) {
                try {
                    mouseX = Server.in.read();
                    mouseY = Server.in.read();
                    click = MouseButtons.toMouseButton(Server.in.read());
                    robot.mouseMove(mouseX, mouseY);
                    switch (click) {
                        case PRIMARY_BUTTON_PRESS:
                            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                            break;
                        case PRIMARY_BUTTON_RELEASE:
                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                            break;
                        case SECONDARY_BUTTON_PRESS:
                            robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                            break;
                        case SECONDARY_BUTTON_RELEASE:
                            robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                            break;
                    }
                } catch (IOException e) {
                    break;
                }
            }
        } catch(AWTException ignored) {}
    }
}
