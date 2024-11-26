package worldheist.dodgegame;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class Controller {
    private final Avatar avatar;
    private final List<Obstacle> obstacles;
    private final GameComponent view;
    private final GameFrame frame;
    private Timer timer;
    private boolean gameOver;
    private int elapsedTime;

    public Controller(Avatar avatar, List<Obstacle> walls, GameComponent view, GameFrame frame) {
        this.avatar = avatar;
        this.obstacles = walls;
        this.view = view;
        this.frame = frame;
        gameOver = false;
        this.elapsedTime = 0;
    }

    public void play() {
        Random rand = new Random();
        for (Obstacle obstacle : obstacles) {
            double randomX = rand.nextInt(frame.getWidth());
            double randomY = rand.nextInt(frame.getHeight() + 100);
            obstacle.setPosition(randomX, randomY);
        }

        timer = new Timer(1000 / 60, e -> {
            elapsedTime++;

            if (frame.getCountDown() <= 0) {
                timer.stop();
                if (!gameOver) {
                    frame.gameOver();
                    JOptionPane.showMessageDialog(frame, "You Win!");
                    gameOver = true;
                    frame.dispose();
                }
            }

            Obstacle curr = obstacles.get(rand.nextInt(obstacles.size()));
            double newX = curr.updateX();
            double newY = curr.updateY();

            curr.setPosition(newX, newY);
            curr.checkBounds(frame.getWidth(), frame.getHeight());
            view.repaint();

            hitsAvatar(curr);
        });
        timer.start();
    }

    private void hitsAvatar(Obstacle obstacle) {
        Rectangle avatarBounds = new Rectangle(avatar.getX(), avatar.getY(),
                avatar.getWidth(), avatar.getHeight());
        Rectangle obstacleBounds = new Rectangle((int) obstacle.getX(), (int) obstacle.getY(),
                30, 30);
        if (avatarBounds.intersects(obstacleBounds)) {
            if (!gameOver) {
                frame.gameOver();
                JOptionPane.showMessageDialog(frame, "Game Over! You lose a life :(");
                frame.dispose();
                gameOver = true;
            }
        }
    }
}
