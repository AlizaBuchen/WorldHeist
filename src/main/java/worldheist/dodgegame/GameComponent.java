package worldheist.dodgegame;

import worldheist.general.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameComponent extends JComponent {
    private final Avatar avatar;

    private final List<Ball> balls;

    public GameComponent(Avatar avatar, List<Ball> balls) {
        this.avatar = avatar;
        this.balls = balls;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLUE);
        g.fillRect((int) avatar.getX(), (int) avatar.getY(), (int) avatar.getWidth(), (int) avatar.getHeight());

        for (Ball ball : balls) {
            g.setColor(ball.getColor());
            g.fillOval((int) ball.getX(), (int) ball.getY(), (int) ball.getWidth(), (int) ball.getHeight());
        }
    }
}
