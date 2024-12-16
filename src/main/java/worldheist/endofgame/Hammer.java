package worldheist.endofgame;

import java.awt.*;

public class Hammer {
    private final Rectangle r1;
    private final Rectangle r2 = new Rectangle();

    public Hammer(Rectangle r1) {
        this.r1 = r1;
        setR2();
    }

    private void setR2() {
        int width = (int) (r1.getHeight() / 2);
        int height = (int) r1.getWidth();
        int x2 = (int) (r1.getX() - r1.getWidth() / 2);
        int y2 = (int) (r1.getY() + r1.getHeight());
        this.r2.setBounds(x2, y2, width, height);
    }

    public void setPosition(int x, int y) {
        r1.setLocation(x, y);
        setR2();
    }

    public Rectangle getBottom() {
        return r1;
    }

    public Rectangle getTop() {
        return r2;
    }

    public void reverse() {
        int x1 = (int) r1.getX();
        int y1 = (int) (r1.getY() - r1.getHeight());
        int x2 = (int) (r1.getX() - r1.getWidth() / 2);
        int y2 = (int) (r1.getY() - r1.getHeight());

        r1.setLocation(x1, y1);
        r2.setLocation(x2, y2);
    }
}
