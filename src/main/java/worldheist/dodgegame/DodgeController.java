package worldheist.dodgegame;

import worldheist.general.*;

import javax.swing.*;
import java.util.List;

public class DodgeController {
    private final Avatar avatar;
    private final List<Obstacle> obstacles;
    private final GameComponent view;
    private final GameFrame frame;
    private Timer timer;
    private boolean gameOver;
    private int avatarHits = 0;

    public DodgeController(Avatar avatar, List<Obstacle> walls, GameComponent view, GameFrame frame) {
        this.avatar = avatar;
        this.obstacles = walls;
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
                    JOptionPane.showMessageDialog(frame, "You Win!");
                    gameOver = true;
                    frame.dispose();
                }
            }

            for (Obstacle obstacle : obstacles) {
                double newX = obstacle.updateX();
                double newY = obstacle.updateY();
                obstacle.setPosition(newX, newY);
                obstacle.checkBounds(frame.getWidth(), frame.getHeight());
                view.repaint();

                if (obstacle.hitsAvatar(avatar)) {
                    avatarHits++;
                    if (avatarHits >= 3) {
                        frame.gameOver();
                        JOptionPane.showMessageDialog(frame, "Game Over! You lose a life :(");
                        frame.dispose();
                        gameOver = true;
                    } else {
                        frame.nextAvatar(avatarHits);
                    }
                    timer.stop();
                }
            }
        });
        timer.start();
    }
}