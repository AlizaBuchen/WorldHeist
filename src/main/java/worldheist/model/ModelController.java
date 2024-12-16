package worldheist.model;

import worldheist.general.Lives;
import worldheist.general.*;
import worldheist.snake.Snake;
import worldheist.snake.SnakeGameFrame;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModelController {
    private final Avatar avatar;
    private final List<Wall> walls;
    private final GameComponent view;
    private final GameFrame frame;
    private Timer timer;
    private Random rand = new Random();
    private List<JFrame> frames = new ArrayList<>();
    private boolean gameFrameCreated;
    private boolean gameOver;
    private int lives;
    private int numFrames = 6;
    private boolean snake;

    public ModelController(Avatar avatar, List<Wall> walls, GameComponent view, GameFrame frame)
            throws FileNotFoundException {
        this.avatar = avatar;
        this.walls = walls;
        this.view = view;
        this.frame = frame;
        this.gameFrameCreated = false;
        this.gameOver = false;
        createFramesList();
        lives = Lives.lives;
        snake = false;
    }

    private void createFramesList() throws FileNotFoundException {
        frames.add(new worldheist.dodgegame.GameFrame());
        frames.add(new worldheist.obstaclejump.GameFrame());
        frames.add(new worldheist.tictactoe.TicTacToe());
        frames.add(new worldheist.rockpaperscissors.RockPaperScissors());
        frames.add(new worldheist.wordle.WordleGameFrame());
    }

    public void play() {
        timer = new Timer(1000 / 60, e -> {
            if (lives <= 0 && !gameOver) {
                timer.stop();
                gameOver = true;
                JOptionPane.showMessageDialog(view, "You got caught. You Lose :(");
                frame.dispose();
            } else if (avatar.getY() + avatar.getHeight() <= 0) {
                timer.stop();
                endOfGame();
            } else {
                hitWall();
            }
        });
        timer.start();
    }

    private void hitWall() {
        for (Wall wall : walls) {
            if (!wall.isHit() && wall.getBounds().intersects(avatar.getBounds())) {
                wall.setHit(true);
                int frame = rand.nextInt(numFrames);
                if (frame == numFrames - 1 && !snake) {
                    new SnakeGameFrame();
                    snake = true;
                } else if (!frames.isEmpty()) {
                    frames.get(frame).setVisible(true);
                    frames.remove(frames.get(frame));
                }
                numFrames--;
                view.repaint();
                break;
            }
            lives = Lives.lives;
            frame.resetLives();
            view.repaint();
        }
    }

    private void endOfGame() {
        if (!gameFrameCreated) {
            new worldheist.maze.GameFrame().setVisible(true);
            gameFrameCreated = true;
            frame.dispose();
        }
    }

    public int getLives() {
        return lives;
    }
}