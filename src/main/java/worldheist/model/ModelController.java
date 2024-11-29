package worldheist.model;

import worldheist.general.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ModelController {
    private final Avatar avatar;
    private final List<Wall> walls;
    private final GameComponent view;
    private Timer timer;

    public ModelController(Avatar avatar, List<Wall> walls, GameComponent view) {
        this.avatar = avatar;
        this.walls = walls;
        this.view = view;
    }

    public void play() {
        timer = new Timer(1000 / 60, e -> {
            hitWall();
        });
        timer.start();
    }

    private void hitWall() {
        for (Wall wall : walls) {
            if (!wall.isHit() && wall.getBounds().intersects(avatar.getBounds())) {
                wall.setHit(true);
                new ObstacleFrame().setVisible(true);
                view.repaint();
                break;
            }
        }
        if (avatar.getY() + avatar.getHeight() <= 0) {
            timer.stop();
            new ObstacleFrame().setVisible(true);
        }
    }
}
