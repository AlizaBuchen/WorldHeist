package worldheist.wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static worldheist.wordle.CharResult.*;

public class WordleGame {
    private final String wordleWord;
    private final Set<String> validWords;
    private int guesses = 0;

    public WordleGame() throws FileNotFoundException {
        this.validWords = loadWords();
        this.wordleWord = chooseWord();
    }

    private Set<String> loadWords() throws FileNotFoundException {
        Set<String> wordSet = new HashSet<>();
        String fileName = "src/main/java/worldheist/wordle/words.txt";
        File file = new File(fileName);
        Scanner input = new Scanner(file);

        while (input.hasNextLine()) {
            wordSet.add(input.nextLine().trim().toUpperCase());
        }
        input.close();
        return wordSet;
    }

    private String chooseWord() {
        ArrayList<String> wordList = new ArrayList<>(validWords);
        Random random = new Random();
        return wordList.get(random.nextInt(wordList.size()));
    }

    public CharResult[] guess(String guessString) {
        String guess = guessString.toUpperCase();

        if (guess.length() != 5 || !validWords.contains(guess)) {
            throw new IllegalArgumentException("Invalid word: " + guessString);
        }

        guesses++;

        CharResult[] result = new CharResult[5];
        boolean[] matched = new boolean[wordleWord.length()];

        for (int i = 0; i < 5; i++) {
            if (guess.charAt(i) == wordleWord.charAt(i)) {
                result[i] = Correct;
                matched[i] = true;
            }
        }

        for (int i = 0; i < 5; i++) {
            if (result[i] == null) {
                for (int j = 0; j < 5; j++) {
                    if (!matched[j] && guess.charAt(i) == wordleWord.charAt(j)) {
                        result[i] = Present;
                        matched[j] = true;
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            if (result[i] == null) {
                result[i] = Incorrect;
            }
        }

        return result;
    }


    public boolean isWinner(String guessString) {
        return wordleWord.equalsIgnoreCase(guessString);
    }

    public boolean isGameOver() {
        return guesses >= 6;
    }

    public int getGuessCount() {
        return guesses;
    }

    public String getWordleWord() {
        return wordleWord;
    }
}
