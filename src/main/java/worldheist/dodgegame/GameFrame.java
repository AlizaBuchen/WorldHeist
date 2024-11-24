package worldheist.dodgegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GameFrame extends JFrame {

    public GameFrame() {
        setSize(1000, 800);
        setTitle("World Heist");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        Avatar avatar = new Avatar(500, 750);
        List<Obstacle> obstacles = createObstacles(15, 30, 30);

        GameComponent component = new GameComponent(avatar, obstacles);
        component.setBounds(0, 0, 1000, 800);
        add(component);

        JLabel person = new JLabel();
        person.setOpaque(true);
        person.setBackground(Color.BLUE);
        person.setBounds(avatar.getX(), avatar.getY(), avatar.getWidth(), avatar.getHeight());
        add(person);

        Controller controller = new Controller(avatar, obstacles, component, this);
        final boolean[] start = {false};

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.play();
                start[0] = true;
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (start[0]) {
                    switch (keyCode) {
                        case KeyEvent.VK_LEFT:
                            avatar.setLocation(avatar.getX() - 5, avatar.getY()); // Move left
                            break;
                        case KeyEvent.VK_RIGHT:
                            avatar.setLocation(avatar.getX() + 5, avatar.getY()); // Move right
                            break;
                        case KeyEvent.VK_UP:
                            avatar.setLocation(avatar.getX(), avatar.getY() - 5); // Move up
                            break;
                        case KeyEvent.VK_DOWN:
                            avatar.setLocation(avatar.getX(), avatar.getY() + 5); // Move down
                            break;
                        default:
                            break;
                    }
                    person.setLocation(avatar.getX(), avatar.getY());
                    component.repaint();
                }
            }
        });

        setFocusable(true);
        component.repaint();
    }

    private List<Obstacle> createObstacles(int numObstacles, int obstacleWidth, int obstacleHeight) {
        List<Obstacle> obstacles = new ArrayList<>();
        int x = 0;
        int y = 0;
        while (obstacles.size() < numObstacles) {
            for (Obstacle o : obstacles) {
                x = (int) o.updateX();
                y = (int) o.updateY();

            }
            obstacles.add(new Obstacle(90, obstacleWidth, obstacleHeight, x, y));
        }
        return obstacles;
    }
}
