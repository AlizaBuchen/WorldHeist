package worldheist.general;

import java.awt.*;

public class Avatar extends Rectangle {
    private final int startX;
    private final int startY;

    public Avatar(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.startX = x;
        this.startY = y;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }
}
