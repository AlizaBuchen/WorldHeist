package worldheist.model;

import worldheist.general.Lives;
import worldheist.general.*;

import javax.swing.*;
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

    public ModelController(Avatar avatar, List<Wall> walls, GameComponent view, GameFrame frame) {
        this.avatar = avatar;
        this.walls = walls;
        this.view = view;
        this.frame = frame;
        this.gameFrameCreated = false;
        this.gameOver = false;
        createFramesList();
        lives = Lives.lives;
    }

    private void createFramesList() {
        frames.add(new worldheist.dodgegame.GameFrame());
        frames.add(new worldheist.obstaclejump.GameFrame());
        frames.add(new worldheist.tictactoe.TicTacToe());
        frames.add(new worldheist.rockpaperscissors.RockPaperScissors());
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
                if (!frames.isEmpty()) {
                    int frame = rand.nextInt(frames.size());
                    frames.get(frame).setVisible(true);
                    frames.remove(frames.get(frame));
                    view.repaint();
                    break;
                }
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