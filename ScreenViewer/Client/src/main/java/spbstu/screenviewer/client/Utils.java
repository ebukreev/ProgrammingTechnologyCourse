package spbstu.screenviewer.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

final class Utils {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    static boolean checkIP(String IP) {
        try {
            InetAddress.getByName(IP);
        } catch (UnknownHostException e) {
            return false;
        }
        return true;
    }
}
