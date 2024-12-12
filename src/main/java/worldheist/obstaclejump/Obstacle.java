package worldheist.obstaclejump;

import worldheist.general.*;

import java.awt.*;

public class Obstacle extends Rectangle {
    private final int frameWidth;
    private String type;

    public Obstacle(int x, int y, int width, int height, int frameWidth) {
        super(x, y, width, height);
        this.frameWidth = frameWidth;
    }

    public void move() {
        this.x -= 7;
    }

    public boolean hitsAvatar(Avatar avatar) {
        return this.getBounds().intersects(avatar.getBounds());
    }

    public boolean inFrame() {
        return this.width <= frameWidth;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
