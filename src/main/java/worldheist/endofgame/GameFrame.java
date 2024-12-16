package worldheist.endofgame;

import worldheist.general.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class GameFrame extends JFrame {
    private final JLabel person;
    private boolean gameOver;
    private JLabel clock;
    private final int[] countDown;
    private boolean[] start;
    private Timer timer;
    private Wall getaway;
    private Item object;
    private JLabel car;
    private Avatar avatar;
    private Hammer hammer;
    private GameComponent component;
    private boolean movePerson;
    private boolean moveCar = false;

    public GameFrame() {
        setSize(1500, 800);
        setTitle("World Heist");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        getContentPane().setBackground(new Color(20, 20, 20));

        avatar = new Avatar((getWidth() / 2) - 22, 700, 45, 50);
        Glass glass = new Glass(getWidth() / 2 - 100, getHeight() / 2 - 100, 200, 200);
        getaway = new Wall(50, getHeight() - 100, 250, 60);
        object = new Item(glass.getCenterX() - 8, glass.getCenterY() - 10, 16, 20);
        hammer = new Hammer(new Rectangle((int) (avatar.getX() + avatar.getWidth()),
                (int) avatar.getY(), 10, 60));
        component = new GameComponent(avatar, glass, getaway, object, hammer, this);
        component.setBounds(0, 0, getWidth(), getHeight());
        add(component);

        person = new JLabel();
        person.setOpaque(true);
        person.setBackground(Color.BLUE);
        person.setBounds((int) avatar.getX(), (int) avatar.getY(), (int) avatar.getWidth(), (int) avatar.getHeight());
        add(person);
        movePerson = true;

        car = new JLabel();
        car.setOpaque(true);
        car.setBackground(Color.RED);
        car.setBounds((int) getaway.getX(), (int) getaway.getY(), (int) getaway.getWidth(), (int) getaway.getHeight());

        clock = new JLabel();
        clock.setFont(new Font("Arial", Font.BOLD, 16));
        clock.setText("Time: 30");
        clock.setBackground(new Color(20, 20, 20));
        clock.setForeground(new Color(205, 205, 205));
        clock.setBounds(getWidth() - 100, 10, 100, 25);
        add(clock);

        start = new boolean[]{false};
        countDown = new int[]{30};

        timer = new Timer(1000, e -> {
            clock.setText("Time: " + countDown[0]);

            if (countDown[0] <= 0 || gameOver) {
                timer.stop();
                start[0] = false;
            } else if (movePerson) {
                moveAvatar();
                countDown[0]--;
            } else if (moveCar) {
                moveGetaway();
                countDown[0]--;
            }
        });

        EndingController controller = new EndingController(avatar, glass, getaway, object, this, component, hammer);

        controller.play();
        start[0] = true;
        timer.start();

        setFocusable(true);
        revalidate();
        repaint();
    }

    private void moveAvatar() {
        for (KeyListener keyListener : getKeyListeners()) {
            removeKeyListener(keyListener);
        }

        if (movePerson) {
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int keyCode = e.getKeyCode();
                    int step = 5;
                    int newX = (int) avatar.getX();
                    int newY = (int) avatar.getY();
                    boolean moveHammer = true;

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
                        case KeyEvent.VK_SPACE:
                            hammer.reverse();
                            moveHammer = false;
                            break;
                        default:
                            break;
                    }

                    avatar.setLocation(newX, newY);
                    person.setLocation(newX, newY);
                    if (moveHammer) {
                        hammer.setPosition((int) (avatar.getX() + avatar.getWidth()),
                                (int) avatar.getY());
                    }
                    component.repaint();
                }
            });
        }
    }

    public void moveGetaway() {
        add(car);
        for (KeyListener keyListener : getKeyListeners()) {
            removeKeyListener(keyListener);
        }
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                int step = 10;
                int newX = (int) getaway.getX();
                int newY = (int) getaway.getY();

                if (keyCode == KeyEvent.VK_RIGHT) {
                    newX += step;
                }

                getaway.setLocation(newX, newY);
                car.setLocation(newX, newY);
                avatar.setLocation((int) (getaway.getCenterX() - avatar.getWidth() / 2),
                        (int) (getaway.getY() - avatar.getHeight() / 2));
                person.setLocation((int) (getaway.getCenterX() - avatar.getWidth() / 2),
                        (int) (getaway.getY() - avatar.getHeight() / 2));
                object.setPosition((avatar.getCenterX() - avatar.getWidth() / 2),
                        (avatar.getY() + avatar.getHeight() / 2));
                component.repaint();
            }
        });
    }

    public void setCar() {
        getContentPane().setBackground(new Color(166, 162, 154));
        clock.setBackground(new Color(166, 162, 154));
        clock.setForeground(new Color(20, 20, 20));
        movePerson = false;
        moveCar = true;
    }

    public void removeListeners() {
        for (KeyListener keyListener : getKeyListeners()) {
            removeKeyListener(keyListener);
        }
    }

    public void gameOver() {
        gameOver = true;
    }

    public int getCountDown() {
        return countDown[0];
    }

    public void setWinner() {
        removeListeners();
        remove(clock);
        Timer endTimer = new Timer(12, e -> {
            int newX = (int) getaway.getX() + 4;
            if (newX + getaway.getWidth() > getWidth()) {
                newX = 0;
            }
            getaway.setLocation(newX, (int) getaway.getY());
            car.setLocation((int) getaway.getX(), (int) getaway.getY());
            avatar.setLocation((int) (getaway.getCenterX() - avatar.getWidth() / 2),
                    (int) (getaway.getY() - avatar.getHeight() / 2));
            person.setLocation((int) (getaway.getCenterX() - avatar.getWidth() / 2),
                    (int) (getaway.getY() - avatar.getHeight() / 2));
        });
        endTimer.start();
    }
}