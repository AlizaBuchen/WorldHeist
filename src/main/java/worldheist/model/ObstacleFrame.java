package worldheist.model;

import worldheist.dodgegame.GameFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ObstacleFrame extends JFrame {

    public ObstacleFrame() {
        worldheist.dodgegame.GameFrame frame = new GameFrame();
        frame.setVisible(true);
    }
}
