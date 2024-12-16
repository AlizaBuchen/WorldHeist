package worldheist.snake;

import worldheist.general.Lives;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeGameComponent extends JComponent implements ActionListener {

    private static final int WIDTH = 1500;
    private static final int HEIGHT = 750;
    private static final int UNIT_SIZE = 20;
    private static final int TOTAL_CELLS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private final Snake snake;
    private final Food food;
    private Direction direction = Direction.RIGHT;
    private boolean running = false;
    private Timer timer;
    private final JFrame frame;

    public SnakeGameComponent(JFrame frame) {
        this.snake = new Snake(TOTAL_CELLS, TOTAL_CELLS, UNIT_SIZE, 5);
        this.food = new Food(UNIT_SIZE, WIDTH, HEIGHT);
        this.frame = frame;
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    private void startGame() {
        running = true;
        timer = new Timer(80, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        if (running && !(snake.isWinner())) {
            g.setColor(Color.RED);
            g.fillOval(food.getX(), food.getY(), UNIT_SIZE, UNIT_SIZE);

            g.setColor(Color.CYAN);
            g.fillRect(snake.getX()[0], snake.getY()[0], UNIT_SIZE, UNIT_SIZE);

            for (int i = 1; i < snake.getLength(); i++) {
                g.setColor(Color.BLUE);
                g.fillRect(snake.getX()[i], snake.getY()[i], UNIT_SIZE, UNIT_SIZE);
            }

            g.setColor(Color.BLACK);
            g.setFont(new Font("Sans serif", Font.BOLD, 20));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + snake.getScore(),
                    (WIDTH - metrics.stringWidth("Score: " + snake.getScore())) / 2,
                    g.getFont().getSize());
        } else {
            showGameOver(g);
        }
    }

    private void showGameOver(Graphics g) {
        if (snake.isWinner())
        {
            g.setColor(Color.CYAN);
            g.setFont(new Font("Sans serif", Font.BOLD, 100));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("WINNER!!!", (WIDTH - metrics.stringWidth("WINNER!!!")) / 2, HEIGHT / 2);
        } else
        {
            g.setColor(Color.RED);
            g.setFont(new Font("Sans serif", Font.BOLD, 50));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Game Over! You lose a life :(",
                    (WIDTH - metrics.stringWidth("Game Over! You lose a life :(")) / 2, HEIGHT / 2);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Sans serif", Font.BOLD, 25));
            metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + snake.getScore(),
                    (WIDTH - metrics.stringWidth("Score: " + snake.getScore())) / 2,
                    HEIGHT / 2 + g.getFont().getSize());
            Lives.lives--;
        }
        new Timer(2000, e -> {
            frame.dispose();
            ((Timer) e.getSource()).stop();
        }).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            snake.move(direction);
            checkFoodCollision();
            if (snake.checkCollision(WIDTH, HEIGHT) || snake.isWinner()) {
                running = false;
                timer.stop();
            }
        }
        repaint();
    }

    private void checkFoodCollision() {
        if (snake.getX()[0] == food.getX() && snake.getY()[0] == food.getY()) {
            snake.grow();
            food.generateNewLocation();
        }
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> {
                    if (direction != Direction.RIGHT) {
                        direction = Direction.LEFT;
                    }
                }
                case KeyEvent.VK_RIGHT -> {
                    if (direction != Direction.LEFT) {
                        direction = Direction.RIGHT;
                    }
                }
                case KeyEvent.VK_UP -> {
                    if (direction != Direction.DOWN) {
                        direction = Direction.UP;
                    }
                }
                case KeyEvent.VK_DOWN -> {
                    if (direction != Direction.UP) {
                        direction = Direction.DOWN;
                    }
                }
                default -> {
                }
            }
        }
    }
}