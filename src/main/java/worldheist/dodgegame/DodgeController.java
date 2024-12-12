package worldheist.dodgegame;

import worldheist.general.Lives;
import worldheist.general.*;

import javax.swing.*;
import java.util.List;

public class DodgeController {
    private final Avatar avatar;
    private final List<Ball> balls;
    private final GameComponent view;
    private final GameFrame frame;
    private Timer timer;
    private boolean gameOver;
    private int avatarHits = 0;

    public DodgeController(Avatar avatar, List<Ball> balls, GameComponent view, GameFrame frame) {
        this.avatar = avatar;
        this.balls = balls;
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

            for (Ball ball : balls) {
                double newX = ball.updateX();
                double newY = ball.updateY();
                ball.setPosition(newX, newY);
                ball.checkBounds(frame.getWidth(), frame.getHeight());
                view.repaint();

                if (ball.hitsAvatar(avatar)) {
                    avatarHits++;
                    if (avatarHits >= 3) {
                        frame.gameOver();
                        JOptionPane.showMessageDialog(frame, "Game Over! You lose a life :(");
                        Lives.lives--;
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