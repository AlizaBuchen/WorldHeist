package worldheist.model;

import worldheist.general.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModelController {
    private final Avatar avatar;
    private final List<Wall> walls;
    private final GameComponent view;
    private Timer timer;
    private Random rand = new Random();
    private List<JFrame> frames = new ArrayList<>();
    private boolean gameFrameCreated;

    public ModelController(Avatar avatar, List<Wall> walls, GameComponent view) {
        this.avatar = avatar;
        this.walls = walls;
        this.view = view;
        this.gameFrameCreated = false;
        createFramesList();
    }

    private void createFramesList() {
        frames.add(new worldheist.dodgegame.GameFrame());
        frames.add(new worldheist.obstaclejump.GameFrame());
    }

    public void play() {
        timer = new Timer(1000 / 60, e -> {
            if (avatar.getY() + avatar.getHeight() <= 0) {
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
        }
    }

    private void endOfGame() {
        if (!gameFrameCreated) {
            new worldheist.maze.GameFrame().setVisible(true);
            gameFrameCreated = true;
        }
    }
}