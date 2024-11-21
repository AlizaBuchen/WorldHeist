package worldheist.model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ObstacleFrame extends JFrame {
    private String result = "";

    public ObstacleFrame() {
        setSize(1000, 800);
        setTitle("Obstacle");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // put the game in here and get print either you win or you lose
                JLabel messageLabel = new JLabel("Game Over. Exit to Continue Heist.");
                messageLabel.setBounds(200, 250, 400, 30);
                add(messageLabel);
                repaint();
                setResult(messageLabel.getText());
            }
        });
        //timer.setRepeats(false);
        timer.start();
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
