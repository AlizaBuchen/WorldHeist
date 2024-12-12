package worldheist.model;

import javax.swing.*;
import java.awt.*;

public abstract class Obstacle {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public Obstacle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void displayObstacle(Graphics g);
}
