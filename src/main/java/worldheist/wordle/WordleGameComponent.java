package worldheist.wordle;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class WordleGameComponent extends JComponent {
    private final JTextField[][] fields;
    private final WordleGame game;

    public WordleGameComponent(WordleGame game) {
        this.game = game;
        this.fields = new JTextField[6][5];
        setLayout(new GridLayout(6, 5, 10, 10));
        initializeGrid();
    }


    private void initializeGrid() {

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                JTextField field = new JTextField();
                field.setHorizontalAlignment(JTextField.CENTER);
                field.setFont(new Font("Arial", Font.BOLD, 30));
                field.setEditable(false);
                field.setBackground(Color.LIGHT_GRAY);

                AbstractDocument doc = (AbstractDocument) field.getDocument();
                doc.setDocumentFilter(new CharacterInputFilter(row, col));

                field.setPreferredSize(new Dimension(100, 100));

                fields[row][col] = field;
                add(field);
            }
        }
        setRowEditable(0, true);
    }

    public void processGuess(String guess, CharResult[] results, boolean isWinner, boolean isGameOver) {
        updateGrid(guess, results);

        if (isWinner) {
            JOptionPane.showMessageDialog(this, "Congratulations! You guessed the word.", "Winner", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        } else if (isGameOver) {
            JOptionPane.showMessageDialog(this, "Game Over. The word was: " + game.getWordleWord(), "Game Over", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        } else {
            setRowEditable(game.getGuessCount() -1, false);
            setRowEditable(game.getGuessCount(), true);
        }
    }

    private void updateGrid(String guess, CharResult[] results) {
        for (int col = 0; col < 5; col++) {
            fields[game.getGuessCount() - 1][col].setText(String.valueOf(guess.charAt(col)));
            switch (results[col]) {
                case Correct -> fields[game.getGuessCount() - 1][col].setBackground(Color.GREEN);
                case Present -> fields[game.getGuessCount() - 1][col].setBackground(Color.YELLOW);
                case Incorrect -> fields[game.getGuessCount() - 1][col].setBackground(Color.GRAY);
            }
            fields[game.getGuessCount() - 1][col].setOpaque(true);
        }
    }

    private void setRowEditable(int row, boolean editable) {
        for (int col = 0; col < 5; col++) {
            fields[row][col].setEditable(editable);
            if (editable && col == 0) {
                fields[row][col].requestFocus();
            }
        }
    }

    public JTextField getField(int row, int col) {
        return fields[row][col];
    }

    private class CharacterInputFilter extends DocumentFilter {
        private final int row;
        private final int col;

        public CharacterInputFilter(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string != null && string.length() == 1 && Character.isLetter(string.charAt(0))) {
                super.insertString(fb, offset, string.toUpperCase(), attr);
                moveToNextField();
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException {
            if (string != null && string.length() == 1 && Character.isLetter(string.charAt(0))) {
                super.replace(fb, offset, length, string.toUpperCase(), attr);
                moveToNextField();
            }
        }

        private void moveToNextField() {
            if (col < 4) {
                fields[row][col + 1].requestFocus();
            }
        }
    }
}
