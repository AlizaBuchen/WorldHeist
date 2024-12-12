package worldheist.model;

import java.awt.*;

public class IntellectualObstacle extends Obstacle {

    public IntellectualObstacle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void displayObstacle(Graphics g) {
        g.setColor(Color.YELLOW); // Placeholder color
        g.drawLine(x, y, x + width, y); // Draw line as a placeholder
    }
}
