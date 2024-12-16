package worldheist.model;

import java.awt.*;

public class PhysicalObstacle extends Obstacle {

    public PhysicalObstacle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void displayObstacle(Graphics g) {
        g.setColor(Color.GREEN); // Placeholder color
        g.fillRect(x, y, width, height); // Draw box as a placeholder
    }
}