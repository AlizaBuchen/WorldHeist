package worldheist.general;

import java.awt.*;
import java.util.Random;

public class Wall extends Rectangle {
    int startX;
    int startY;
    private boolean hit;
    private Random rand = new Random();

    public Wall(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.hit = false;
        this.startX = x;
        this.startY = y;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public void setRandomLocation(int width, int height) {
        this.x = rand.nextInt(width);
        this.y = rand.nextInt(height);
    }

    public boolean contains(int ballCenterX, int ballCenterY) {
        return ballCenterX >= x && ballCenterX <= x + width
                && ballCenterY >= y && ballCenterY <= y + height;
    }
}