package worldheist.general;

import java.awt.*;

public class Avatar extends Rectangle {
    private final int startX;
    private final int startY;
    private double velocityY = 0;
    private final double gravity = 1;
    private final int jumpStrength = -14;
    private boolean jumping = false;

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

    public void jump() {
        if (!jumping) {
            jumping = true;
            velocityY = jumpStrength;
        }
    }

    public void update() {
        if (jumping) {
            y += velocityY;
            velocityY += gravity;

            if (y >= startY) {
                y = startY;
                velocityY = 0;
                jumping = false;
            }
        }
    }
}