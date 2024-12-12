package worldheist.endofgame;

public class Shard {
    private int x;
    private int y;
    private final int width;
    private final int height;
    private final int fallSpeed;

    public Shard(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fallSpeed = 2 + (int) (Math.random() * 5);
    }

    public void fall() {
        this.y += fallSpeed;
    }

    public boolean isOffScreen(int screenHeight) {
        return y > screenHeight;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
