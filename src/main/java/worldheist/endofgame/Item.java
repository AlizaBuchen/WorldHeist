package worldheist.endofgame;

import worldheist.general.Avatar;

import java.awt.geom.Ellipse2D;

public class Item extends Ellipse2D.Double {
    private boolean visible;

    public Item(double x, double y, int width, int height) {
        super(x, y, width, height);
        visible = false;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void appear() {
        visible = true;
    }

    public boolean isVisible() {
        return visible;
    }
}
