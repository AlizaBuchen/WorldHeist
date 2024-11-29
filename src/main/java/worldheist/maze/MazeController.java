package worldheist.maze;

import worldheist.general.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MazeController {
    private final Avatar avatar;
    private final List<Wall> walls;
    private final GameComponent view;
    private boolean gameOver;
    private Timer timer;

    public MazeController(Avatar avatar, List<Wall> walls, GameComponent view) {
        this.avatar = avatar;
        this.walls = walls;
        this.view = view;
        this.gameOver = false;
    }

    public void play() {
        timer = new Timer(1000 / 60, e -> {
            hitWall();
            if (gameOver) {
                timer.stop();
            }
        });
        timer.start();
    }

    private void hitWall() {
        for (Wall wall : walls) {
            if (!wall.isHit() && wall.getBounds().intersects(avatar.getBounds())) {
                gameOver = true;
                break;
            }
        }
    }
}
