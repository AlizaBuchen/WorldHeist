package worldheist.obstaclejump;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ObstacleFactory {
    private final int numObstacles;
    private final int obstacleWidth;
    private final int obstacleHeight;
    private final int frameWidth;
    private int width;
    private final int frameHeight;
    private final Random rand = new Random();

    public ObstacleFactory(int numObstacles, int obstacleWidth, int obstacleHeight, int width, int frameHeight) {
        this.numObstacles = numObstacles;
        this.obstacleWidth = obstacleWidth;
        this.obstacleHeight = obstacleHeight;
        this.frameWidth = width;
        this.width = width;
        this.frameHeight = frameHeight;
    }

    public List<Obstacle> createObstacles() {
        List<Obstacle> obstacles = new ArrayList<>();
        while (obstacles.size() < numObstacles) {
            Obstacle obstacle = new Obstacle(width, frameHeight - obstacleHeight - 50,
                    obstacleWidth, obstacleHeight, frameWidth);
            if (rand.nextInt(2) == 1) {
                obstacle.setType("Rectangle");
            } else {
                obstacle.setType("Oval");
            }
            obstacles.add(obstacle);
            width += obstacleWidth + (frameWidth / (rand.nextInt(5) + 2));
        }
        return obstacles;
    }
}
