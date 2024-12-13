package snake;

public class Snake {

    private int length;
    private int score = 0;
    private final int[] x;
    private final int[] y;
    private final int unitSize;

    public Snake(int x, int y, int unitSize, int length) {
        this.length = length;
        this.unitSize = unitSize;
        this.x = new int[x];
        this.y = new int[y];
    }

    public int getLength() {
        return length;
    }

    public int getScore() {
        return score;
    }

    public int[] getX() {
        return x;
    }

    public int[] getY() {
        return y;
    }

    public void grow() {
        length++;
        score++;
    }

    public boolean isWinner() {
        return score == 15;
    }

    public void move(Direction direction) {
        for (int i = length; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case LEFT -> x[0] -= unitSize;
            case RIGHT -> x[0] += unitSize;
            case UP -> y[0] -= unitSize;
            case DOWN -> y[0] += unitSize;
            default -> {
            }
        }
    }

    public boolean checkCollision(int width, int height) {
        boolean collision = false;

        for (int i = length; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                collision = true;
                break;
            }
        }
        if (x[0] < 0 || x[0] >= width || y[0] < 0 || y[0] >= height)
        {
            collision = true;
        }

        return collision;
    }
}