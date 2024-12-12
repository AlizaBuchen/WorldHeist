package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe implements ActionListener {
    private JFrame frame;
    private JPanel headerPanel;
    private JPanel gridPanel;
    private JLabel statusLabel;
    private JButton[] gridButtons;
    private boolean isPlayerTurn;

    public TicTacToe() {
        frame = new JFrame("Tic Tac Toe Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        headerPanel = new JPanel();
        statusLabel = new JLabel("Tic Tac Toe");
        statusLabel.setFont(new Font("Verdana", Font.BOLD, 40));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(0, 102, 204));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.add(statusLabel, BorderLayout.CENTER);
        frame.add(headerPanel, BorderLayout.NORTH);

        // Creating panel
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(3, 3));
        gridPanel.setBackground(new Color(200, 200, 200));
        frame.add(gridPanel, BorderLayout.CENTER);

        // buttons
        gridButtons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            gridButtons[i] = new JButton();
            gridButtons[i].setFont(new Font("Arial", Font.BOLD, 120));
            gridButtons[i].setFocusable(false);
            gridButtons[i].addActionListener(this);
            gridPanel.add(gridButtons[i]);
        }

        startNewGame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == gridButtons[i]) {
                if (gridButtons[i].getText().equals("") && isPlayerTurn) {
                    gridButtons[i].setText("X");
                    gridButtons[i].setForeground(Color.RED);
                    isPlayerTurn = false;
                    statusLabel.setText("Computer's Turn");
                    checkWinner();
                    if (!isPlayerTurn) {
                        makeComputerMove();
                    }
                }
            }
        }
    }

    private void startNewGame() {
        isPlayerTurn = true;
        statusLabel.setText("Your Turn");
        for (JButton button : gridButtons) {
            button.setText("");
            button.setEnabled(true);
            button.setBackground(null); // Reset button background
        }
    }

    private void makeComputerMove() {
        Timer delay = new Timer(500, event -> {
            for (int i = 0; i < 9; i++) {
                if (gridButtons[i].getText().equals("")) {
                    gridButtons[i].setText("O");
                    gridButtons[i].setForeground(Color.BLUE);
                    isPlayerTurn = true;
                    statusLabel.setText("Your Turn");
                    checkWinner();
                    break;
                }
            }
            ((Timer) event.getSource()).stop();
        });
        delay.setRepeats(false);
        delay.start();
    }

    private void checkWinner() {
        int[][] winningCombinations = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
                {0, 4, 8}, {2, 4, 6}             // Diagonals
        };

        for (int[] combo : winningCombinations) {
            String a = gridButtons[combo[0]].getText();
            String b = gridButtons[combo[1]].getText();
            String c = gridButtons[combo[2]].getText();

            if (a.equals("X") && b.equals("X") && c.equals("X")) {
                displayWinner(combo, "You Win!! Congrats:)");
                return;
            } else if (a.equals("O") && b.equals("O") && c.equals("O")) {
                displayWinner(combo, "Computer Wins!");
                return;
            }
        }

        // Check for tie
        boolean tie = true;
        for (JButton button : gridButtons) {
            if (button.getText().equals("")) {
                tie = false;
                break;
            }
        }
        if (tie) {
            statusLabel.setText("It's a Tie!!!");
            disableGrid();
        }
    }

    private void displayWinner(int[] winningCombo, String message) {
        for (int index : winningCombo) {
            gridButtons[index].setBackground(Color.GREEN);
        }
        statusLabel.setText(message);
        disableGrid();
    }

    private void disableGrid() {
        for (JButton button : gridButtons) {
            button.setEnabled(false);
        }
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
