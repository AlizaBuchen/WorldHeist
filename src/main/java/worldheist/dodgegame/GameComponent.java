package worldheist.dodgegame;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class GameComponent extends JComponent {
    private final Avatar avatar;

    private final List<Obstacle> obstacles;

    public GameComponent(Avatar avatar, List<Obstacle> obstacles) {
        this.avatar = avatar;
        this.obstacles = obstacles;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
       // setBackground(Color.BLACK);
        //g.setColor(getBackground());
        //setOpaque(true);

        for (Obstacle obstacle : obstacles) {
            g.setColor(obstacle.getColor());
            g.fillOval((int) obstacle.getX() - 10, (int) obstacle.getY() - 10, 30, 30);  // Draw the obstacle as a filled oval
        }


        g.setColor(Color.BLUE);
        g.fillRect(avatar.getX(), avatar.getY(), avatar.getWidth(), avatar.getHeight());
    }
}
