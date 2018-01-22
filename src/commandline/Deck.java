package commandline;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class to model the deck of cards
 */
public class Deck {
    private IO inputOutput;
    private DebugLog log;
    private ArrayList<Card> gameDeck;
    public final int DECK_SIZE = 40;

    /**
     * Default constructor
     * @param io IO object
     * @throws FileNotFoundException if deck file is not found
     */
    public Deck(IO io) throws FileNotFoundException{
        inputOutput = io;
        createDeck(inputOutput.readDeck());

    }

    /**
     * Constructor in debug mode
     * @param io IO object
     * @param dl DebugLog object
     * @throws FileNotFoundException if file is not found
     */
    public Deck(IO io, DebugLog dl) throws FileNotFoundException{ //need to log initial and shuffled deck
        inputOutput = io;
        log = dl;
        createDeck(inputOutput.readDeck());
    }

    /**
     * Creates an unshuffled deck.
     * @param d a 2d arrray of the card attributes
     */
    private void createDeck(String[][] d) {
        String[][] deck = d;
        String[] card = new String[inputOutput.DECK_FILE_ROWS];

        for (int i = 0; i < inputOutput.DECK_FILE_ROWS; i++) {
            for (int j = 0; j < inputOutput.DECK_FILE_COLUMNS;j++) {
                card[j] = deck[i][j];
            }
        Card c = new Card(card);
        gameDeck.add(i, c);
        }
    }

    /**
     * Shuffles the deck
     */
    private void shuffleDeck() {
        Collections.shuffle(gameDeck);
    }

    public Card getCardAt(int pos) {
        return gameDeck.get(pos);
    }
}
