package worldheist.maze;

import worldheist.general.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class GameFrame extends JFrame {
    private boolean gameOver;
    private int[] countDown;
    private boolean[] start;
    private Timer timer;

    public GameFrame() {
        setSize(1500, 800);
        setTitle("World Heist");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        Avatar avatar = new Avatar(750, 750, 16, 16);
        MazeGenerator mazeGenerator = new MazeGenerator(getWidth(), getHeight());
        List<Wall> walls = mazeGenerator.createMaze();

        GameComponent component = new GameComponent(avatar, walls);
        component.setBounds(0, 0, 1500, 800);
        add(component);

        JLabel person = new JLabel();
        person.setOpaque(true);
        person.setBackground(Color.BLUE);
        person.setBounds((int) avatar.getX(), (int) avatar.getY(), (int) avatar.getWidth(), (int) avatar.getHeight());
        add(person);

        JLabel clock = new JLabel();
        clock.setFont(new Font("Arial", Font.BOLD, 16));
        clock.setText("Time: 60");
        clock.setBounds(getWidth() - 110, 5, 100, 25);
        add(clock);

        start = new boolean[]{false};
        countDown = new int[]{59};

        timer = new Timer(1000, e -> {
            clock.setText("Time: " + countDown[0]);

            if (countDown[0] <= 0 || gameOver) {
                timer.stop();
                start[0] = false;
            } else {
                countDown[0]--;
            }
        });

        MazeController controller = new MazeController(avatar, walls, this);
        controller.play();
        start[0] = true;
        timer.start();

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

    public void gameOver() {
        gameOver = true;
    }

    public int getCountDown() {
        return countDown[0];
    }
}