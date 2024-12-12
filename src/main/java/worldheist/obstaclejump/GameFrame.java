package worldheist.obstaclejump;

import worldheist.general.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GameFrame extends JFrame {
    private final int[] countDown;
    private boolean gameOver;
    private final GameComponent component;
    private final JLabel person;
    private final Avatar avatar;
    private final boolean[] start;
    private Timer timer;
    private final Timer gameLoopTimer;

    public GameFrame() {
        setSize(1500, 800);
        setTitle("World Heist");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        getContentPane().setBackground(new Color(255, 210, 160));

        avatar = new Avatar(75, getHeight() - 95, 45, 50);

        ObstacleFactory obstacleFactory = new ObstacleFactory(50, 35, 45, getWidth(), getHeight());
        List<Obstacle> obstacles = obstacleFactory.createObstacles();

        component = new GameComponent(avatar, obstacles);
        component.setBounds(0, 0, 1500, 800);
        add(component);

        person = new JLabel();
        person.setOpaque(true);
        person.setBackground(Color.BLUE);
        person.setBounds((int) avatar.getX(), (int) avatar.getY(), (int) avatar.getWidth(), (int) avatar.getHeight());
        add(person);

        JLabel clock = new JLabel();
        clock.setFont(new Font("Arial", Font.BOLD, 16));
        clock.setText("Time: 60");
        clock.setBounds(getWidth() - 105, 10, 100, 30);
        add(clock);

        countDown = new int[]{59};

        start = new boolean[]{false};

        timer = new Timer(1000, e -> {
            clock.setText("Time: " + countDown[0]);

            if (countDown[0] <= 0 || gameOver) {
                timer.stop();
                start[0] = false;
            } else {
                countDown[0]--;
            }
        });

        // KeyListener for controlling the jump
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!start[0]) {
                    return;
                }

                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_SPACE) {
                    avatar.jump(); // Trigger jump
                }
            }
        });

        gameLoopTimer = new Timer(16, e -> {
            avatar.update();
            person.setLocation((int) avatar.getX(), (int) avatar.getY());
            component.repaint();
        });

        JumpController controller = new JumpController(avatar, obstacles, component, this);

        person.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.play();
                start[0] = true;
                timer.start();
                gameLoopTimer.start();
            }
        });

        setFocusable(true);
        revalidate();
        repaint();
    }

    public int getCountDown() {
        return countDown[0];
    }

    public void gameOver() {
        gameOver = true;
        gameLoopTimer.stop();
    }

    public static void main(String[] args) {
        new GameFrame().setVisible(true);
    }
}
