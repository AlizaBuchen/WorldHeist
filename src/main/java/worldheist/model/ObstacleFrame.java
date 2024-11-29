package worldheist.model;

import worldheist.maze.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ObstacleFrame extends JFrame {

    public ObstacleFrame() {
        //worldheist.dodgegame.GameFrame frame = new GameFrame();
        worldheist.maze.GameFrame frame = new worldheist.maze.GameFrame();
        frame.setVisible(true);
    }
}
