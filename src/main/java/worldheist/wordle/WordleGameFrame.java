package worldheist.wordle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class WordleGameFrame extends JFrame {
    private final WordleGame game;
    private final WordleGameComponent gameComponent;

    public WordleGameFrame() throws FileNotFoundException {
        setTitle("Wordle Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1400, 775);


        this.game = new WordleGame();
        this.gameComponent = new WordleGameComponent(game);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(gameComponent, BorderLayout.CENTER);

        JButton submitButton = new JButton("Enter");
        submitButton.setPreferredSize(new Dimension(100, 40));
        submitButton.addActionListener(new SubmitActionListener());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null); // Centers frame on screen
    }

    private class SubmitActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringBuilder guessBuilder = new StringBuilder();
            for (int col = 0; col < 5; col++) {
                JTextField field = gameComponent.getField(game.getGuessCount(), col);
                String text = field.getText().toUpperCase();
                if (text.length() != 1 || !Character.isLetter(text.charAt(0))) {
                    JOptionPane.showMessageDialog(WordleGameFrame.this, "Each box must contain a valid letter.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                guessBuilder.append(text);
            }

            String guess = guessBuilder.toString();
            try {
                CharResult[] results = game.guess(guess);
                boolean isWinner = game.isWinner(guess);
                boolean isGameOver = game.isGameOver();

                gameComponent.processGuess(guess, results, isWinner, isGameOver);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(WordleGameFrame.this, ex.getMessage(), "Invalid Guess", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}