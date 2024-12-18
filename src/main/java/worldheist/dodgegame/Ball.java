package worldheist.dodgegame;

import worldheist.general.Avatar;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Ball extends Ellipse2D.Double {
    private static final int MIN_VELOCITY = 5;
    private static final int MAX_VELOCITY = 10;
    private final int velocity;
    private int angle;
    private final Color color;

    public Ball(int angle, int width, int height, double x, double y) {
        super(x, y, width, height);
        this.angle = angle;
        this.velocity = new Random().nextInt(MAX_VELOCITY - MIN_VELOCITY + 1) + MIN_VELOCITY;
        this.color = generateRandomColor();
    }

    private Color generateRandomColor() {
        Random rand = new Random();
        return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));  // RGB values from 0 to 255
    }

    public void setPosition(double newX, double newY) {
        this.x = newX;
        this.y = newY;
    }

    public double updateX() {
        return getX() + Math.cos(Math.toRadians(angle)) * velocity;
    }

    public double updateY() {
        return getY() + Math.sin(Math.toRadians(angle)) * velocity;
    }

    public void checkBounds(int screenWidth, int screenHeight) {
        Random rand = new Random();
        int r = rand.nextInt(2);
        if (getX() < 0 || getX() + getWidth() > screenWidth) {
            if (r == 0) {
                angle = 180 - angle;
            } else {
                angle = 270 - angle;
            }
        }
        if (getY() < 0 || getY() + getHeight() > screenHeight) {
            if (r == 0) {
                angle = -angle;
            } else {
                angle = -angle + rand.nextInt(360);
            }
        }
    }

    public boolean hitsAvatar(Avatar avatar) {
        return this.getBounds().intersects(avatar.getBounds());
    }

    public Color getColor() {
        return color;
    }
}