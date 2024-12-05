package worldheist.maze;

import worldheist.general.*;
import java.util.*;

public class MazeGenerator {
    private static final int GRID_SIZE = 30;
    private final int width;
    private final int height;

    public MazeGenerator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public List<Wall> createMaze() {
        Random rand = new Random();
        List<Wall> walls = new ArrayList<>();

        int cols = width / GRID_SIZE;
        int rows = height / GRID_SIZE;

        boolean[][] grid = new boolean[cols][rows];

        for (int i = 0; i < cols; i++) {
            for (int j = 1; j < rows - 2; j++) {
                grid[i][j] = true;
            }
        }


        int entranceRow = rows - 5;
        int entranceWidth = 3;
        int entranceColStart = rand.nextInt(cols - entranceWidth);

        for (int i = entranceColStart; i < entranceColStart + entranceWidth; i++) {
            grid[i][entranceRow] = false;
        }

        int exitCol = rand.nextInt(cols);
        int exitRow = 0;
        grid[exitCol][exitRow] = false;

        carvePath(entranceColStart, entranceRow, grid, rand);

        for (int y = 1; y < rows - 2; y++) {
            for (int x = 0; x < cols; x++) {
                if (grid[x][y]) {
                    walls.add(new Wall(x * GRID_SIZE, y * GRID_SIZE, GRID_SIZE, GRID_SIZE));
                }
            }
        }

        return walls;
    }

    private void carvePath(int x, int y, boolean[][] grid, Random rand) {
        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};

        int[] directions = {0, 1, 2, 3};
        shuffleArray(directions, rand);

        grid[x][y] = false;

        for (int dir : directions) {
            int nx = x + dx[dir] * 2;
            int ny = y + dy[dir] * 2;

            if (nx >= 0 && ny >= 0 && nx < grid.length && ny < grid[0].length && grid[nx][ny]) {
                grid[nx][ny] = false;
                grid[x + dx[dir]][y + dy[dir]] = false;
                carvePath(nx, ny, grid, rand);
            }
        }
    }

    private void shuffleArray(int[] array, Random rand) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}

