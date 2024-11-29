package worldheist.maze;

import worldheist.general.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameFrame extends JFrame {
    Random rand = new Random();

    public GameFrame() {
        setSize(1500, 800);
        setTitle("World Heist");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        Avatar avatar = new Avatar(750, 750, 55, 60);
        List<Wall> walls = createMaze();

        GameComponent component = new GameComponent(avatar, walls);
        component.setBounds(0, 0, 1500, 800);
        add(component);

        JLabel person = new JLabel();
        person.setOpaque(true);
        person.setBackground(Color.BLUE);
        person.setBounds((int) avatar.getX(), (int) avatar.getY(), (int) avatar.getWidth(), (int) avatar.getHeight());
        add(person);

        MazeController controller = new MazeController(avatar, walls, component);
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
                            avatar.setLocation((int) (avatar.getX() - 5), (int) avatar.getY());
                            break;
                        case KeyEvent.VK_RIGHT:
                            avatar.setLocation((int) (avatar.getX() + 5), (int) avatar.getY());
                            break;
                        case KeyEvent.VK_UP:
                            avatar.setLocation((int) avatar.getX(), (int) (avatar.getY() - 5));
                            break;
                        case KeyEvent.VK_DOWN:
                            avatar.setLocation((int) avatar.getX(), (int) (avatar.getY() + 5));
                            break;
                        default:
                            break;
                    }
                    person.setLocation((int) avatar.getX(), (int) avatar.getY());
                    component.repaint();
                }
            }
        });

        setFocusable(true);
        component.repaint();
    }

    private List<Wall> createMaze() {
        List<Wall> walls = new ArrayList<>();
        int x;
        int y = 0;

        while (walls.size() < 50) {
            int width = rand.nextInt(500) + 200;
            int lOrR = rand.nextInt(2);
//            x = lOrR == 0 ? 0 : getWidth() - width;
            x = rand.nextInt(getWidth());
            y += 50;

            boolean overlap = false;
            for (Wall w : walls) {
                if (w.intersects(new Rectangle(x, y, width, 50))) {
                    overlap = true;
                    break;
                }
            }

            if (!overlap) {
                walls.add(new Wall(x, y, width, 50));
            }
        }
        return walls;
    }
}