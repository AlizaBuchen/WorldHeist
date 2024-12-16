package worldheist.obstaclejump;

import worldheist.general.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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

        g.setColor(Color.BLUE);
        g.fillRect((int) avatar.getX(), (int) avatar.getY(), (int) avatar.getWidth(), (int) avatar.getHeight());

        for (Obstacle obstacle : obstacles) {
            if (obstacle.inFrame()) {
                if (obstacle.getType().equals("Rectangle")) {
                    g.setColor(new Color(83, 53, 10));
                    g.fillRect((int) obstacle.getX(), (int) obstacle.getY(),
                            (int) obstacle.getWidth(), (int) obstacle.getHeight());
                } else if (obstacle.getType().equals("Oval")) {
                    g.setColor(new Color(42, 126, 25));
                    g.fillOval((int) obstacle.getX(), (int) obstacle.getY(),
                            (int) obstacle.getWidth(), (int) obstacle.getHeight());
                }
            }
        }
    }
}
