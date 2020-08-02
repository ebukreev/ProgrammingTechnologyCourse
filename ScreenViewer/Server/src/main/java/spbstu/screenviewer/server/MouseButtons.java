package spbstu.screenviewer.server;

enum MouseButtons {
    PRIMARY_BUTTON_PRESS,
    PRIMARY_BUTTON_RELEASE,
    SECONDARY_BUTTON_PRESS,
    SECONDARY_BUTTON_RELEASE,
    EMPTY;

    static MouseButtons toMouseButton(int number) {
        if (number == 1)
            return PRIMARY_BUTTON_PRESS;
        if (number == 2)
            return PRIMARY_BUTTON_RELEASE;
        if (number == 3)
            return SECONDARY_BUTTON_PRESS;
        if (number == 4)
            return SECONDARY_BUTTON_RELEASE;
        return EMPTY;
    }
}
