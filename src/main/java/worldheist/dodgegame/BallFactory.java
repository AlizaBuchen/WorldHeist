package worldheist.dodgegame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BallFactory {
    private final int numBalls;
    private final int ballWidth;
    private final int ballHeight;
    private final Random rand = new Random();

    public BallFactory(int numBalls, int ballWidth, int ballHeight) {
        this.numBalls = numBalls;
        this.ballWidth = ballWidth;
        this.ballHeight = ballHeight;
    }
    public List<Ball> createBalls() {
        List<Ball> balls = new ArrayList<>();
        while (balls.size() < numBalls) {
            balls.add(new Ball(rand.nextInt(300) + 45, ballWidth, ballHeight,
                    rand.nextInt(1500), rand.nextInt(400)));
        }
        return balls;
    }
}
