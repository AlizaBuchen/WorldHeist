package worldheist.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        setSize(1000, 800);
        setTitle("World Heist");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        Avatar avatar = new Avatar(500, 750);
        Walls wall = new Walls(30, 18);
        wall.populateWalls();

        GameComponent component = new GameComponent(avatar, wall);
        component.setBounds(0, 0, 800, 600);
        add(component);

        JLabel person = new JLabel();
        person.setOpaque(true);
        person.setBackground(Color.BLUE);
        person.setBounds(avatar.getX(), avatar.getY(), avatar.getWidth(), avatar.getHeight());
        add(person);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
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
                }
                person.setLocation(avatar.getX(), avatar.getY());
                component.repaint();
            }
        });

        setFocusable(true); // Ensure the frame can receive key events
        component.repaint();
    }
}
