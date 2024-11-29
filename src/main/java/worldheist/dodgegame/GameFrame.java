package worldheist.dodgegame;

import worldheist.general.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameFrame extends JFrame {
    private int[] countDown;
    private boolean gameOver;
    private final GameComponent component;
    private JLabel person;
    private JLabel person1;
    private JLabel person2;
    private JLabel person3;
    private Avatar avatar;
    private int lives;
    private boolean[] start;
    private List<Obstacle> obstacles;
    private Timer timer;
    private final Random rand = new Random();

    public GameFrame() {
        setSize(1500, 800);
        setTitle("World Heist");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        getContentPane().setBackground(Color.BLACK);

        avatar = new Avatar(750, 750, 55, 60);
        lives = 3;

        obstacles = createObstacles(10, 30, 30);

        component = new GameComponent(avatar, obstacles);
        component.setBounds(0, 0, 1500, 800);
        add(component);

        person1 = new JLabel();
        person1.setOpaque(true);
        person1.setBackground(Color.BLUE);
        person1.setBounds((int) avatar.getX(), (int) avatar.getY(), (int) avatar.getWidth(), (int) avatar.getHeight());
        add(person1);

        person2 = new JLabel();
        person2.setOpaque(true);
        person2.setBackground(Color.BLUE);
        person2.setBounds(60, 750, (int) avatar.getWidth(), (int) avatar.getHeight());
        add(person2);

        person3 = new JLabel();
        person3.setOpaque(true);
        person3.setBackground(Color.BLUE);
        person3.setBounds(0, 750, (int) avatar.getWidth(), (int) avatar.getHeight());
        add(person3);

        JLabel clock = new JLabel();
        clock.setOpaque(true);
        clock.setBackground(Color.BLACK);
        clock.setForeground(Color.WHITE);
        clock.setFont(new Font("Arial", Font.BOLD, 16));
        clock.setText("Time: 60");
        clock.setBounds(getWidth() - 110, 10, 100, 30);
        add(clock);

        person = person1;

        countDown = new int[]{59};

        DodgeController controller = new DodgeController(avatar, obstacles, component, this);
        start = new boolean[]{false};

        timer = new Timer(1000, e -> {
            clock.setText("Time: " + countDown[0]);

            if (countDown[0] <= 0 || gameOver) {
                timer.stop();
                start[0] = false;
            } else {
                moveAvatar(avatar, start);
                countDown[0]--;
            }
        });

        person.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.play();
                start[0] = true;
                timer.start();
            }
        });

        setFocusable(true);
        revalidate();
        repaint();
    }

    private List<Obstacle> createObstacles(int numObstacles, int obstacleWidth, int obstacleHeight) {
        List<Obstacle> obstacles = new ArrayList<>();
        while (obstacles.size() < numObstacles) {
            obstacles.add(new Obstacle(rand.nextInt(300) + 45, obstacleWidth, obstacleHeight,
                    rand.nextInt(1500), rand.nextInt(400)));
        }
        return obstacles;
    }

    private void moveAvatar(Avatar avatar, boolean[] start) {
        for (KeyListener keyListener : getKeyListeners()) {
            removeKeyListener(keyListener);
        }

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (start[0]) {
                    switch (keyCode) {
                        case KeyEvent.VK_LEFT:
                            avatar.setLocation((int) (avatar.getX() - 10), (int) avatar.getY());
                            break;
                        case KeyEvent.VK_RIGHT:
                            avatar.setLocation((int) (avatar.getX() + 10), (int) avatar.getY());
                            break;
                        case KeyEvent.VK_UP:
                            avatar.setLocation((int) avatar.getX(), (int) (avatar.getY() - 10));
                            break;
                        case KeyEvent.VK_DOWN:
                            avatar.setLocation((int) avatar.getX(), (int) (avatar.getY() + 10));
                            break;
                        default:
                            break;
                    }
                    person.setLocation((int) avatar.getX(), (int) avatar.getY());
                    component.repaint();
                }
            }
        });
    }

    public int getCountDown() {
        return countDown[0];
    }

    public void gameOver() {
        gameOver = true;
    }

    public void nextAvatar(int numTimes) {
        lives--;
        timer.stop();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (numTimes == 1) {
            person2.setVisible(false);
        } else if (numTimes == 2) {
            person3.setVisible(false);
        }
        if (lives > 0) {
            avatar.setLocation(avatar.getStartX(), avatar.getStartY());
            person.setLocation((int) avatar.getX(), (int) avatar.getY());
            for (Obstacle o : obstacles) {
                o.setPosition(rand.nextInt(1500), rand.nextInt(400));
            }
            gameOver = false;
            start[0] = false;
        } else {
            gameOver();
        }
    }
}