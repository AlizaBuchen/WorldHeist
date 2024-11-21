package worldheist.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameFrame extends JFrame {

    public GameFrame() {
        setSize(1000, 800);
        setTitle("World Heist");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        Avatar avatar = new Avatar(500, 750);
        List<Wall> walls = createWalls(10, getWidth(), 30);

        GameComponent component = new GameComponent(avatar, walls);
        component.setBounds(0, 0, 800, 600);
        add(component);

        JLabel person = new JLabel();
        person.setOpaque(true);
        person.setBackground(Color.BLUE);
        person.setBounds(avatar.getX(), avatar.getY(), avatar.getWidth(), avatar.getHeight());
        add(person);

        Controller controller = new Controller(avatar, walls, component);
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

    private List<Wall> createWalls(int numWalls, int wallWidth, int wallHeight) {
        List<Wall> walls = new ArrayList<>();
        Random rand = new Random();

        int maxX = getWidth();
        int maxY = getHeight();
        int y = 20;
        while (walls.size() < numWalls) {
            int x = 0;
            y += 50;

            boolean overlap = false;
            for (Wall w : walls) {
                if (w.intersects(new Rectangle(x, y, wallWidth, wallHeight))) {
                    overlap = true;
                    break;
                }
            }

            if (!overlap) {
                walls.add(new Wall(x, y, wallWidth, wallHeight));
            }
        }
        return walls;
    }
}
