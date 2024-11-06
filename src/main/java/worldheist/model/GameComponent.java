package worldheist.model;

import worldheist.model.Avatar;

import javax.swing.*;
import java.awt.*;

public class GameComponent extends JComponent {
    private final Avatar avatar;

    private final Walls wall;

    public GameComponent(Avatar avatar, Walls wall) {
        this.avatar = avatar;
        this.wall = wall;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < wall.getCols(); i++) {
            for (int j = 0; j < wall.getRows(); j++) {
                if (wall.isBrick(i, j)) {
                    g.setColor(Color.RED);
                    int xPos = i * 30;
                    int yPos = j * 18;
                    g.fillRect(xPos, yPos, 30, 18);

                    g.setColor(Color.BLACK);
                    g.drawRect(xPos, yPos, 30, 18);
                }
            }
        }

        g.setColor(Color.BLUE);
        g.fillRect(avatar.getX(), avatar.getY(), avatar.getWidth(), avatar.getHeight());
    }
}
