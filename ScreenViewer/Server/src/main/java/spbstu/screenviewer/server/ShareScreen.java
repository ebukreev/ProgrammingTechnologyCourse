package spbstu.screenviewer.server;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

final class ShareScreen extends Thread {

    private static final Dimension screenSize =
            Toolkit.getDefaultToolkit().getScreenSize();

    private static final Rectangle screenRect =
            new Rectangle(screenSize);

    private static BufferedImage getScreenshot() throws AWTException {
        return new Robot().createScreenCapture(screenRect);
    }

    private BufferedImage previousImage;

    static void sendScreenSize() {
        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Server.out.writeInt(screenSize.width);
            Server.out.writeInt(screenSize.height);
            Server.out.flush();
        } catch (IOException e) {
            sendScreenSize();
        }
    }

    @Override
    public void run() {
        try {
            previousImage = getScreenshot();
        } catch (AWTException ignored) {}
        while (!isInterrupted()) {
            if (Server.out == null)
                continue;
            try {
                BufferedImage screenshot = getScreenshot();
                if (!compareImages(previousImage, screenshot)) {
                    ImageIO.write(screenshot, "jpg", Server.out);
                    Server.out.flush();
                }
                previousImage = screenshot;
            } catch (IOException e) {
                break;
            } catch (AWTException ignored) {}
        }
    }

    /**
     * Compares two images pixel by pixel.
     *
     * @param imgA the first image.
     * @param imgB the second image.
     * @return whether the images are both the same or not.
     */
    //taken from https://stackoverflow.com/questions/11006394/is-there-a-simple-way-to-compare-bufferedimage-instances
    static boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
        int width  = imgA.getWidth();
        int height = imgA.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }
}