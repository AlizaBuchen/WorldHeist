package worldheist.model;

import java.awt.*;
import java.util.Random;

public class Walls extends Rectangle {
    private final Random rand = new Random();
    private final int width;
    private final int height;
    private final int rows = 12;
    private final int cols = 20;
    private static final int NUM_WALLS = 20;
    private final int [][] walls;

    public Walls(int width, int height) {
        this.width = width;
        this.height = height;
        this.walls = new int[width][height];
    }

    public void populateWalls() {
        int placedBricks = 0;

        while (placedBricks < NUM_WALLS) {
            int x = rand.nextInt(cols);
            int y = rand.nextInt(rows);

            if (walls[x][y] == 0) {
                walls[x][y] = 1;
                placedBricks++;
            }
        }
    }

    public void hitBrick(int x, int y) {
        walls[x][y] = 0;
    }

    public boolean isBrick(int x, int y) {
        return walls[x][y] == 1;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}