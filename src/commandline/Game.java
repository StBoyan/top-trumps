package commandline;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Class to handle a game of Top Trumps
 */
public class Game {

    /**
     * Game default constructor
     */
    public Game() throws FileNotFoundException{
        readDeck();
    }

    /**
     * Game constructor in debug mode
     * @param log DebugLog object
     */
    public Game(DebugLog log) throws FileNotFoundException{
        readDeck();
    }

    /**
     * Reads deck file
     * @return String[][] 2d array with deck info
     * @throws FileNotFoundException if deck file is not present
     */
    private String[][] readDeck() throws FileNotFoundException {
        final String DECK_FILE = "deck.txt";
        /* 1 description line and 40 card lines */
        final int DECK_FILE_ROWS = 41;
        /* card name and 5 characteristics */
        final int DECK_FILE_COLUMNS = 6;

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
