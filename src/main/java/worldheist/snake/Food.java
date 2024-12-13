package worldheist.snake;

import java.util.Random;

public class Food {

    private int x;
    private int y;
    private final int unitSize;
    private final int width;
    private final int height;
    private final Random random = new Random();

    public Food(int unitSize, int width, int height) {
        this.unitSize = unitSize;
        this.width = width;
        this.height = height;
        generateNewLocation();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void generateNewLocation() {
        x = random.nextInt(width / unitSize) * unitSize;
        y = random.nextInt(height / unitSize) * unitSize;
    }
}
