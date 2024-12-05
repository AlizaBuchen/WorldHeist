package worldheist.maze;

import worldheist.general.*;
import java.util.*;

public class MazeGenerator {
    private static final int GRID_SIZE = 30;  // Size of each grid cell
    private final int width;
    private final int height;

    public MazeGenerator (int width, int height) {
        this.width = width;
        this.height = height;
    }

    public List<Wall> createMaze() {
        Random rand = new Random();
        List<Wall> walls = new ArrayList<>();

        // Adjust the grid size to fit the frame, ensuring a clean division by GRID_SIZE
        int cols = width / GRID_SIZE;  // Number of columns
        int rows = height / GRID_SIZE; // Number of rows

        // Create a grid to track visited cells, initially setting all cells as walls
        boolean[][] grid = new boolean[cols][rows];

        // Initialize all grid cells as walls
        for (int i = 0; i < cols; i++) {
            for (int j = 1; j < rows - 2; j++) {
                grid[i][j] = true;  // Set all cells as walls
            }
        }

        // Entrance is 3 rows up from the bottom to avoid blocking the bottom
        int entranceRow = rows - 5;
        int entranceWidth = 3;  // Width of the entrance
        int entranceColStart = rand.nextInt(cols - entranceWidth);  // Random starting column for the entrance

        // Make the cells for the entrance open (false) in the grid
        for (int i = entranceColStart; i < entranceColStart + entranceWidth; i++) {
            grid[i][entranceRow] = false;  // Mark these cells as open (entrance path)
        }

        // Choose random column for the exit at the top row
        int exitCol = rand.nextInt(cols);      // Random column for exit at the top row
        int exitRow = 0;                        // Exit is at the top row
        grid[exitCol][exitRow] = false;         // Mark the exit position as open

        // Start the maze generation from the entrance at the bottom
        carvePath(entranceColStart, entranceRow, grid, rand);

        // Convert grid into Wall objects for rendering
        for (int y = 1; y < rows - 2; y++) {
            for (int x = 0; x < cols; x++) {
                if (grid[x][y]) {  // If it's a wall (true), create a wall object
                    walls.add(new Wall(x * GRID_SIZE, y * GRID_SIZE, GRID_SIZE, GRID_SIZE));
                }
            }
        }

        return walls;
    }

    // Depth-First Search (DFS) to carve a path in the grid
    private void carvePath(int x, int y, boolean[][] grid, Random rand) {
        // Directions for moving in the maze (right, down, left, up)
        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};

        // Shuffle directions to randomize the path generation
        int[] directions = {0, 1, 2, 3};
        shuffleArray(directions, rand);

        // Mark the current cell as open (false)
        grid[x][y] = false;

        // Explore each direction
        for (int dir : directions) {
            int nx = x + dx[dir] * 2;  // Move two steps to create a wider gap
            int ny = y + dy[dir] * 2;

            // Ensure the next cell is within bounds and hasn't been visited
            if (nx >= 0 && ny >= 0 && nx < grid.length && ny < grid[0].length && grid[nx][ny]) {
                // Open the next cell (2 steps ahead)
                grid[nx][ny] = false;
                // Open the wall between current and next cell (2 steps)
                grid[x + dx[dir]][y + dy[dir]] = false;
                // Recursively carve the path
                carvePath(nx, ny, grid, rand);
            }
        }
    }

    // Shuffle the directions array to make the path generation random
    private void shuffleArray(int[] array, Random rand) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}

