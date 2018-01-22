package commandline;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Class to do input and output to external files
 */
public class IO {
    final String DECK_FILE = "deck.txt";
    /* 1 description line and 40 card lines */
    final int DECK_FILE_ROWS = 41;
    /* card name and 5 characteristics */
    final int DECK_FILE_COLUMNS = 6;

    /**
     * Reads deck file
     * @return String[][] 2d array with deck info
     * @throws FileNotFoundException if deck file is not present
     */
    public String[][] readDeck() throws FileNotFoundException {
        String[][] deck = new String[DECK_FILE_ROWS][DECK_FILE_COLUMNS];
        FileReader reader = new FileReader("DECK_FILE");
        Scanner in = new Scanner(reader);

        int currentRow = 0;
        while (in.hasNext()) {
            String[] deckFileLine = in.nextLine().split(" ");

            for (int i = 0; i < DECK_FILE_COLUMNS; i++) {
                deck[currentRow][i] = deckFileLine[i];
            }

            currentRow++;
        }

        return deck;
    }
}
