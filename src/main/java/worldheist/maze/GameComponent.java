package worldheist.maze;

import worldheist.general.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class GameComponent extends JComponent {
    private final Avatar avatar;
    private final List<Wall> walls;
    Random rand = new Random();
    Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

    public GameComponent(Avatar avatar, List<Wall> walls) {
        this.avatar = avatar;
        this.walls = walls;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Wall wall : walls) {
            g.setColor(color);
            g.fillRect(wall.x, wall.y, wall.width, wall.height);

            g.setColor(Color.BLACK);
            g.drawRect(wall.x, wall.y, wall.width, wall.height);
        }

        g.setColor(Color.BLUE);
        g.fillRect((int) avatar.getX(), (int) avatar.getY(), (int) avatar.getWidth(), (int) avatar.getHeight());
    }
}
