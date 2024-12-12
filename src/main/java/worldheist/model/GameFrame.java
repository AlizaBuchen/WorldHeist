package worldheist.model;

import worldheist.general.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameFrame extends JFrame {
    private final JLabel lives;
    private final GameComponent component;
    private final ModelController controller;

    public GameFrame() {
        setSize(1500, 800);
        setTitle("World Heist");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        Avatar avatar = new Avatar(750, 750, 45, 50);
        WallFactory wallFactory = new WallFactory(12, getWidth(), 30);
        List<Wall> walls = wallFactory.createWalls();

        component = new GameComponent(avatar, walls);
        component.setBounds(0, 0, 1500, 800);
        add(component);

        JLabel person = new JLabel();
        person.setOpaque(true);
        person.setBackground(Color.BLUE);
        person.setBounds((int) avatar.getX(), (int) avatar.getY(), (int) avatar.getWidth(), (int) avatar.getHeight());
        add(person);

        controller = new ModelController(avatar, walls, component, this);

        lives = new JLabel();
        lives.setText("Lives: " + controller.getLives());
        lives.setBounds(10, 10, 100, 30);
        lives.setFont(new Font("Arial", Font.BOLD, 16));
        add(lives);

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

    public void resetLives() {
        lives.setText("Lives: " + controller.getLives());
    }
}
