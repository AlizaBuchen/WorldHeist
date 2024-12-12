package worldheist.model;

import worldheist.general.Wall;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WallFactory
{
    private final int numWalls;
    private final int wallWidth;
    private final int wallHeight;

    public WallFactory(int numWalls, int wallWidth, int wallHeight) {
        this.numWalls = numWalls;
        this.wallWidth = wallWidth;
        this.wallHeight = wallHeight;
    }

    public List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        int x;
        int y = 0;
        while (walls.size() < numWalls) {
            x = 0;
            y += 150;

            boolean overlap = false;
            for (Wall w : walls) {
                if (w.intersects(new Rectangle(x, y, wallWidth, wallHeight))) {
                    overlap = true;
                    break;
                }
            }

            if (!overlap) {
                walls.add(new Wall(x, y, wallWidth, wallHeight));
            }
        }
        return walls;
    }
}
