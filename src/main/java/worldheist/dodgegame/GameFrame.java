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
    private final boolean[] start;
    private List<Ball> balls;
    private Timer timer;
    private final Random rand = new Random();

    public GameFrame() {
        setSize(1500, 800);
        setTitle("World Heist");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        getContentPane().setBackground(Color.BLACK);

        avatar = new Avatar(getWidth() / 2, getHeight() - 60, 45, 50);
        lives = 3;

        balls = createBalls(10, 30, 30);

        component = new GameComponent(avatar, balls);
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

        DodgeController controller = new DodgeController(avatar, balls, component, this);
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

    private void moveAvatar(Avatar avatar, boolean[] start) {
        for (KeyListener keyListener : getKeyListeners()) {
            removeKeyListener(keyListener);
        }

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!start[0]) {
                    return;
                }

                int keyCode = e.getKeyCode();
                int step = 10;
                int newX = (int) avatar.getX();
                int newY = (int) avatar.getY();

                switch (keyCode) {
                    case KeyEvent.VK_LEFT:
                        newX = (newX >= 0) ? newX - step : (int) (getWidth() - avatar.getWidth());
                        break;
                    case KeyEvent.VK_RIGHT:
                        newX = (newX + step <= getWidth()) ? newX + step : 0;
                        break;
                    case KeyEvent.VK_UP:
                        newY = (newY >= 0) ? newY - step : (int) (getHeight() - avatar.getHeight());
                        break;
                    case KeyEvent.VK_DOWN:
                        newY = (newY + step <= getHeight()) ? newY + step : 0;
                        break;
                    default:
                        break;
                }

                avatar.setLocation(newX, newY);
                person.setLocation(newX, newY);
                component.repaint();
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
            for (Ball o : balls) {
                o.setPosition(rand.nextInt(1500), rand.nextInt(400));
            }
            gameOver = false;
            start[0] = false;
        } else {
            gameOver();
        }
    }

    private List<Ball> createBalls(int numBalls, int ballWidth, int ballHeight) {
        List<Ball> balls = new ArrayList<>();
        while (balls.size() < numBalls) {
            balls.add(new Ball(rand.nextInt(300) + 45, ballWidth, ballHeight,
                    rand.nextInt(1500), rand.nextInt(400)));
        }
        return balls;
    }

    public static void main(String[] args) {
        new GameFrame().setVisible(true);
    }
}