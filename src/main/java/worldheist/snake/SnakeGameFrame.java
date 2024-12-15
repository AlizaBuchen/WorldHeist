package worldheist.snake;

import javax.swing.*;
import java.awt.*;

public class SnakeGameFrame extends JFrame {

    public SnakeGameFrame() {
        SnakeGameComponent component = new SnakeGameComponent(this);
        this.add(component);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        getContentPane().setBackground(new Color(0, 160, 10));
    }
}