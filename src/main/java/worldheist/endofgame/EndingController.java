package worldheist.endofgame;

import worldheist.general.Lives;
import worldheist.general.*;
import javax.swing.*;

public class EndingController {
    private final Avatar avatar;
    private Glass glass;
    private final Wall getaway;
    private final Item object;
    private Hammer hammer;
    private boolean gameOver;
    private Timer timer;
    private final GameFrame frame;
    private final GameComponent view;

    public EndingController(Avatar avatar, Glass glass, Wall getaway,
                            Item object, GameFrame frame, GameComponent view, Hammer hammer) {
        this.avatar = avatar;
        this.glass = glass;
        this.getaway = getaway;
        this.object = object;
        this.hammer = hammer;
        this.frame = frame;
        this.view = view;
        this.gameOver = false;
        getaway.setHit(true);
    }

    public void play() {
        timer = new Timer(1000 / 60, e -> {
            if (frame.getCountDown() <= 0) {
                timer.stop();
                if (!gameOver) {
                    frame.gameOver();
                    JOptionPane.showMessageDialog(frame, "You got caught :(");
                    gameOver = true;
                    frame.dispose();
                }
            }
            if (getaway.getX() >= frame.getWidth()) {
                frame.gameOver();
                timer.stop();
                JOptionPane.showMessageDialog(frame, "You have successfully obtained the object!\nYou Win!");
                frame.dispose();
                gameOver = true;
            }
            if (glass != null) {
                hitWall();
            } else if (avatar.getBounds().intersects(object.getBounds())) {
                object.appear();
                object.setPosition((avatar.getCenterX() - avatar.getWidth() / 2),
                        (avatar.getY() + avatar.getHeight() / 2));
            }
            if (avatar.getBounds().intersects(getaway.getBounds())) {
                frame.setCar();
            }
        });
        timer.start();
    }

    private void hitWall() {
        if (hammer.getTop().getBounds().intersects(glass.getBounds())) {
            glass.hit();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            hammer.setPosition((int) (avatar.getX() + avatar.getWidth()), (int) avatar.getY());
            view.repaint();
        }
        if (glass.isShattered()) {
            frame.removeListeners();
            glass.shatter();
            glass = null;
            getaway.setHit(false);
        }
        view.repaint();
    }
}
