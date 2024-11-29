package worldheist.general;

import java.awt.*;

public class Wall extends Rectangle {
    private boolean hit;

    public Wall(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.hit = false;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public boolean contains(int ballCenterX, int ballCenterY) {
        return ballCenterX >= x && ballCenterX <= x + width
                && ballCenterY >= y && ballCenterY <= y + height;
    }
}