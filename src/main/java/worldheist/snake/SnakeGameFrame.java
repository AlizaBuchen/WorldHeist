package worldheist.snake;

import javax.swing.*;

public class SnakeGameFrame extends JFrame {

    public SnakeGameFrame() {
        SnakeGameComponent component = new SnakeGameComponent();
        this.add(component);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }
}