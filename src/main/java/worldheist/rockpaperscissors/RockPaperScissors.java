package worldheist.rockpaperscissors;

import worldheist.Lives;
import worldheist.endofgame.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RockPaperScissors extends JFrame implements ActionListener {
    private int userWins = 0;
    private int compWins = 0;
    private int rounds = 0;
    private int points = 10;
    private final int winningPoints = 50;
    private JLabel statusLabel;
    private JLabel pointsLabel;
    private JTextField betField;
    private Random random;

    public RockPaperScissors() {
        setTitle("Rock, Paper, Scissors");
        setSize(1500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        random = new Random();

        JPanel topPanel = new JPanel();
        statusLabel = new JLabel("Place your bet and make a choice:", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 15));
        pointsLabel = new JLabel("Points: " + points, SwingConstants.CENTER);
        pointsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        topPanel.setLayout(new GridLayout(2, 1));
        topPanel.add(statusLabel);
        topPanel.add(pointsLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel middlePanel = new JPanel();
        JLabel betLabel = new JLabel("Bet (1-10): ");
        betField = new JTextField(5);
        middlePanel.add(betLabel);
        middlePanel.add(betField);
        add(middlePanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        JButton rockButton = new JButton("Rock");
        JButton paperButton = new JButton("Paper");
        JButton scissorsButton = new JButton("Scissors");
        rockButton.addActionListener(this);
        paperButton.addActionListener(this);
        scissorsButton.addActionListener(this);
        bottomPanel.add(rockButton);
        bottomPanel.add(paperButton);
        bottomPanel.add(scissorsButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userChoice = e.getActionCommand().toLowerCase();
        String[] choices = {"rock", "paper", "scissors"};
        int compChoiceIndex = random.nextInt(3);
        String compChoice = choices[compChoiceIndex];

        int bet;
        try {
            bet = Integer.parseInt(betField.getText());
            if (bet < 1 || bet > 10) {
                statusLabel.setText("Invalid number. Enter a number between 1 and 10.");
                return;
            }
        } catch (NumberFormatException ex) {
            statusLabel.setText("Invalid number. Enter a valid number.");
            return;
        }

        String result;
        if (userChoice.equals(compChoice)) {
            result = "It's a tie!";
        } else if (
                (userChoice.equals("rock") && compChoice.equals("scissors")) ||
                        (userChoice.equals("paper") && compChoice.equals("rock")) ||
                        (userChoice.equals("scissors") && compChoice.equals("paper"))
        ) {
            result = "You win!";
            userWins++;
            points += bet;
        } else {
            result = "I win!";
            compWins++;
            points -= bet;
        }

        rounds++;
        statusLabel.setText(String.format("You chose %s, I chose %s. %s", userChoice, compChoice, result));
        pointsLabel.setText("Points: " + points);

        if (points <= 0) {
            JOptionPane.showMessageDialog(this, String.format("Game Over!\nRounds: " +
                            "%d\nYour Wins: %d\nComputer Wins: %d\nFinal Points: %d",
                    rounds, userWins, compWins, points));
            Lives.lives--;
            this.dispose();
        }

        if (points >= winningPoints) {
            JOptionPane.showMessageDialog(this, String.format("Congratulations, You Win!\nRounds:" +
                            " %d\nYour Wins: %d\nComputer Wins: %d\nFinal Points: %d",
                    rounds, userWins, compWins, points));
            Lives.lives--;
            this.dispose();
        }
    }
}
