package worldheist.endofgame;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Glass extends Rectangle {
    private int hits;
    private int numLines;
    private final List<Line> lines;
    private final List<Line> cracks;
    private boolean isShattered;
    private final List<Shard> shards;

    public Glass(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.hits = 0;
        this.numLines = 6;
        this.isShattered = false;
        this.lines = new ArrayList<>();
        this.cracks = new ArrayList<>();
        this.shards = new ArrayList<>();
        generateLines();
    }

    public void hit() {
        if (!isShattered) {
            hits++;
            numLines += 8;
            if (hits >= 5) {
                isShattered = true;
            } else {
                addShatterLines();
            }
        }
    }

    private void generateLines() {
        int x2 = x + width;
        int y2 = y + height;

        lines.add(new Line(x, y, x2, y));
        lines.add(new Line(x, y, x, y2));
        lines.add(new Line(x2, y, x2, y2));
        lines.add(new Line(x, y2, x2, y2));

        int depth = 10;
        lines.add(new Line(x + depth, y + depth, x2 + depth, y + depth));
        lines.add(new Line(x + depth, y + depth, x + depth, y2 + depth));
        lines.add(new Line(x2 + depth, y + depth, x2 + depth, y2 + depth));
        lines.add(new Line(x + depth, y2 + depth, x2 + depth, y2 + depth));

        lines.add(new Line(x, y, x + depth, y + depth));
        lines.add(new Line(x2, y, x2 + depth, y + depth));
        lines.add(new Line(x2, y2, x2 + depth, y2 + depth));
        lines.add(new Line(x, y2, x + depth, y2 + depth));
    }

    private void addShatterLines() {
        Random random = new Random();
        for (int i = 0; i < numLines; i++) {
            int x1 = width / 2 + x;
            int y1 = height / 2 + y;
            int x2 = random.nextInt(width) + x;
            int y2 = random.nextInt(height) + y;
            cracks.add(new Line(x1, y1, x2, y2));
        }
    }

    public boolean isShattered() {
        return isShattered;
    }

    public List<Shard> getShards() {
        return shards;
    }

    public void shatter() {
        Random random = new Random();
        for (int i = 0; i < 25; i++) {
            int shardWidth = 5 + random.nextInt(15);
            int shardHeight = 10 + random.nextInt(20);
            int shardX = x + random.nextInt(width - shardWidth);
            int shardY = y + random.nextInt(height - shardHeight);

            shards.add(new Shard(shardX, shardY, shardWidth, shardHeight));
        }
    }

    public int getHits() {
        return hits;
    }

    public List<Line> getLines() {
        return lines;
    }

    public List<Line> getShatterLines() {
        return cracks;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    static class Line {
        int x1;
        int y1;
        int x2;
        int y2;

        public Line(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }
}
