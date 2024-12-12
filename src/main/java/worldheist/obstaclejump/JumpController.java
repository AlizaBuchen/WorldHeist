package worldheist.obstaclejump;

import worldheist.general.*;

import javax.swing.*;
import java.util.List;

public class JumpController {
    private final Avatar avatar;
    private final List<Obstacle> obstacles;
    private final GameComponent view;
    private final GameFrame frame;
    private Timer timer;
    private boolean gameOver;

    public JumpController(Avatar avatar, List<Obstacle> obstacles, GameComponent view, GameFrame frame) {
        this.avatar = avatar;
        this.obstacles = obstacles;
        this.view = view;
        this.frame = frame;
        gameOver = false;
    }

    public void play() {
        timer = new Timer(1000 / 60, e -> {
            if (frame.getCountDown() <= 0) {
                timer.stop();
                if (!gameOver) {
                    frame.gameOver();
                    obstacles.clear();
                    view.repaint();
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showMessageDialog(frame, "You Win!");
                    gameOver = true;
                    frame.dispose();
                }
            }

            for (Obstacle obstacle : obstacles) {
                obstacle.move();
                view.repaint();

                if (obstacle.hitsAvatar(avatar)) {
                    timer.stop();
                    frame.gameOver();
                    JOptionPane.showMessageDialog(frame, "Game Over! You lose a life :(");
                    frame.dispose();
                    gameOver = true;
                }
            }
        });
        timer.start();
    }
}
