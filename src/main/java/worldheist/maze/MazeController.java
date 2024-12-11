package worldheist.maze;

import worldheist.general.*;
import worldheist.model.ObstacleFrame;

import javax.swing.*;
import java.util.List;

public class MazeController {
    private final Avatar avatar;
    private final List<Wall> walls;
    private boolean gameOver;
    private Timer timer;
    private GameFrame frame;

    public MazeController(Avatar avatar, List<Wall> walls, GameFrame frame) {
        this.avatar = avatar;
        this.walls = walls;
        this.frame = frame;
        this.gameOver = false;
    }

    public void play() {
        timer = new Timer(1000 / 60, e -> {
            if (avatar.getY() <= 0 && frame.getCountDown() > 0) {
                frame.gameOver();
                gameOver = true;
                frame.dispose();
                new worldheist.endofgame.GameFrame().setVisible(true);
            }
            else if (frame.getCountDown() <= 0) {
                timer.stop();
                if (!gameOver) {
                    frame.gameOver();
                    JOptionPane.showMessageDialog(frame, "You didn't get to the end fast enough. You lose :(");
                    gameOver = true;
                    frame.dispose();
                }
            }
            hitWall();
            if (gameOver) {
                timer.stop();
            }
        });
        timer.start();
    }

    private void hitWall() {
        for (Wall wall : walls) {
            if (wall.getBounds().intersects(avatar.getBounds())) {
                frame.gameOver();
                JOptionPane.showMessageDialog(frame, "Game Over! You lose:(");
                frame.dispose();
                gameOver = true;
                break;
            }
        }
    }
}
