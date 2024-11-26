package worldheist.model;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Controller {
    private final Avatar avatar;
    private final List<Wall> walls;
    private final GameComponent view;

    public Controller(Avatar avatar, List<Wall> walls, GameComponent view) {
        this.avatar = avatar;
        this.walls = walls;
        this.view = view;
    }

    public void play() {
        Timer timer = new Timer(1000 / 60, e -> {
            hitWall();
        });
        timer.start();
    }

    private void hitWall() {
        Rectangle avatarBounds = new Rectangle(avatar.getX(),
                avatar.getY(),
                avatar.getWidth(), avatar.getHeight());
        for (Wall wall : walls) {
            if (!wall.isHit() && wall.getBounds().intersects(avatarBounds)) {
                wall.setHit(true);
                new ObstacleFrame().setVisible(true);
                view.repaint();
                break;
            }
        }
    }
}
