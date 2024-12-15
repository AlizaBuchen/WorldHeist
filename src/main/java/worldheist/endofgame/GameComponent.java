package worldheist.endofgame;

import worldheist.general.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Iterator;

public class GameComponent extends JComponent {
    private final Avatar avatar;
    private Glass glass;
    private final Wall getaway;
    private final Item object;
    private final Hammer hammer;
    private final JFrame frame;
    private ImageIcon image;
    private boolean winner;
    private Timer fallTimer;

    public GameComponent(Avatar avatar, Glass glass, Wall getaway, Item object, Hammer hammer, JFrame frame) {
        this.avatar = avatar;
        this.glass = glass;
        this.getaway = getaway;
        this.object = object;
        this.hammer = hammer;
        this.frame = frame;
        winner = false;
        fallTimer = new Timer(16, e -> repaint());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (glass != null) {
            if (glass.getHits() > 0 && !glass.isShattered()) {
                drawGlassWithCracks(g, glass);
            } else if (!glass.isShattered()) {
                drawGlass(g, glass);
            }

            if (glass.isShattered()) {
                g.setColor(Color.BLACK);
                hammer.setPosition(getWidth() / 2, getHeight());
                drawShatteredGlass(g, glass);
                fallTimer.start();
            }
        }

        if (!getaway.isHit()) {
            g.setColor(Color.RED);
            g.fillRect((int) getaway.getX(), (int) getaway.getY(), (int) getaway.getWidth(), (int) getaway.getHeight());
        }

        g.setColor(Color.BLUE);
        g.fillRect((int) avatar.getX(), (int) avatar.getY(), (int) avatar.getWidth(), (int) avatar.getHeight());

        g.setColor(Color.WHITE);
        Rectangle r1 = hammer.getBottom();
        g.fillRect((int) r1.getX(), (int) r1.getY(), (int) r1.getWidth(), (int) r1.getHeight());
        Rectangle r2 = hammer.getTop();
        g.fillRect((int) r2.getX(), (int) r2.getY(), (int) r2.getWidth(), (int) r2.getHeight());

        if (object.isVisible()) {
            g.setColor(Color.GRAY);
            g.fillOval((int) (avatar.getCenterX() - object.getWidth() / 2),
                    (int) (avatar.getCenterY() - object.getHeight() / 2),
                    (int) object.getWidth(), (int) object.getHeight());
        } else {
            g.setColor(Color.GRAY);
            g.fillOval((int) (object.getX()), (int) (object.getY()),
                    (int) object.getWidth(), (int) object.getHeight());
        }

        if (winner) {
            drawWinner(g);
        }
    }

    private void drawGlass(Graphics g, Glass glass) {
        g.setColor(Color.LIGHT_GRAY);

        List<Glass.Line> lines = glass.getLines();

        for (Glass.Line line : lines) {
            g.drawLine(line.x1, line.y1, line.x2, line.y2);
        }
    }

    private void drawGlassWithCracks(Graphics g, Glass glass) {
        g.setColor(Color.LIGHT_GRAY);

        List<Glass.Line> lines = glass.getLines();
        for (Glass.Line line : lines) {
            g.drawLine(line.x1, line.y1, line.x2, line.y2);
        }

        g.setColor(Color.LIGHT_GRAY);
        List<Glass.Line> cracks = glass.getShatterLines();
        for (Glass.Line crack : cracks) {
            g.drawLine(crack.x1, crack.y1, crack.x2, crack.y2);
        }
    }

    private void drawShatteredGlass(Graphics g, Glass glass) {
        g.setColor(Color.lightGray);

        List<Shard> shatteredPieces = glass.getShards();

        Iterator<Shard> iterator = shatteredPieces.iterator();
        while (iterator.hasNext()) {
            Shard shard = iterator.next();
            shard.fall();

            if (shard.isOffScreen(getHeight())) {
                iterator.remove();
            } else {
                g.drawRect(shard.getX(), shard.getY(), shard.getWidth(), shard.getHeight());
            }
        }
    }

    public void setWinner() {
        winner = true;
    }

    public void drawWinner(Graphics g) {
        frame.getContentPane().setBackground(new Color	(187,220,245));
        g.setFont(new Font("Sans serif", Font.BOLD, 100));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.setColor(new Color(140,200,255));
        g.drawString("You Win!", (frame.getWidth() - metrics.stringWidth("You Win!")) / 2
                , frame.getHeight() / 2);
        g.setColor(Color.RED);
        g.fillRect((int) getaway.getX(), (int) getaway.getY(), (int) getaway.getWidth(), (int) getaway.getHeight());
        g.setColor(Color.BLUE);
        g.fillRect((int) avatar.getX(), (int) avatar.getY(), (int) avatar.getWidth(), (int) avatar.getHeight());
        g.setColor(Color.GRAY);
        g.fillOval((int) (avatar.getCenterX() - object.getWidth() / 2),
                (int) (avatar.getCenterY() - object.getHeight() / 2),
                (int) object.getWidth(), (int) object.getHeight());
    }
}