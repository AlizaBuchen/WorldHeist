package rockPaperScissors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RockPaperScissors extends JFrame implements ActionListener {
    private int userWins = 0;
    private int compWins = 0;
    private int rounds = 0;
    private int points = 10; // User starts with this amount of points
    private final int WINNING_POINTS = 50; // amount of points required for game over and user to win
    private JLabel status, pointsLabel, userScore, compScore;
    private JTextField betField;
    private Random random;

    public RockPaperScissors() {
        setTitle("Rock, Paper, Scissors");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        random = new Random();
        JPanel topPanel = new JPanel();
        JPanel middlePanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        status = new JLabel("Place your bet and make a choice:", SwingConstants.CENTER);
        status.setFont(new Font("Arial", Font.BOLD, 15));
        pointsLabel = new JLabel("Points: " + points, SwingConstants.CENTER);
        pointsLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        userScore = new JLabel("Your Wins: 0");
        compScore = new JLabel("Computer Wins: 0");

        JButton rockButton = new JButton("Rock"); // buttons for user to choose
        JButton paperButton = new JButton("Paper");
        JButton scissorsButton = new JButton("Scissors");
        rockButton.addActionListener(this);
        paperButton.addActionListener(this);
        scissorsButton.addActionListener(this);

        JLabel betLabel = new JLabel("Bet (1-10): "); // betting field
        betField = new JTextField(5);


        topPanel.setLayout(new GridLayout(2, 1));
        topPanel.add(status);
        topPanel.add(pointsLabel);

        middlePanel.add(betLabel);
        middlePanel.add(betField);

        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(rockButton);
        bottomPanel.add(paperButton);
        bottomPanel.add(scissorsButton);

        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
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
                status.setText("Invalid bet! Enter a number between 1 and 10.");
                return;
            }
        } catch (NumberFormatException ex) {
            status.setText("Invalid bet! Enter a valid number.");
            return;
        }

        // result::
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
            points += bet; // each win, increments the points
        } else {
            result = "I win!";
            compWins++;
            points -= bet; // each loss, points go down
        }

        rounds++;
        status.setText(String.format("You chose %s, I chose %s. %s", userChoice, compChoice, result));
        pointsLabel.setText("Points: " + points);
        userScore.setText("Your Wins: " + userWins);
        compScore.setText("Computer Wins: " + compWins);

        // to end the game::
        if (points <= 0) {
            JOptionPane.showMessageDialog(this, String.format("Game Over!\nRounds: %d\nYour Wins: %d\nComputer Wins: %d\nFinal Points: %d",
                    rounds, userWins, compWins, points));
            resetGame();
        }

        if (points >= WINNING_POINTS) {
            JOptionPane.showMessageDialog(this, String.format("Congratulations, You Win!\nRounds: %d\nYour Wins: %d\nComputer Wins: %d\nFinal Points: %d",
                    rounds, userWins, compWins, points));
            resetGame();
        }
    }

    private void resetGame() {
        userWins = 0;
        compWins = 0;
        rounds = 0;
        points = 10; // Reset points to start amount
        status.setText("Place your bet and make a choice!");
        pointsLabel.setText("Points: " + points);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RockPaperScissors game = new RockPaperScissors();
            game.setVisible(true);
        });
    }
}
