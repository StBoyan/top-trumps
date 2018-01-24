package commandline;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class to model the deck of cards
 */
public class Deck {
    private IO inputOutput;
    private ArrayList<Card> gameDeck;
    private String[] statDescr = new String[5]; //needs to be changed; magic number and initialised before construction
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
     * Creates an unshuffled deck.
     * @param deck a 2d arrray of the card attributes
     */
    private void createDeck(String[][] deck) {
        gameDeck = new ArrayList<Card>(DECK_SIZE);
        String[] card = new String[inputOutput.DECK_FILE_ROWS];

        for(int i = 1; i < inputOutput.DECK_FILE_COLUMNS; i++) {    //may need to be changed
            statDescr[i - 1] = deck[0][i];
        }

        for (int i = 1; i < inputOutput.DECK_FILE_ROWS; i++) {
            for (int j = 0; j < inputOutput.DECK_FILE_COLUMNS;j++) {
                card[j] = deck[i][j];
            }
        Card c = new Card(card);
        gameDeck.add(c);
        }
    }

    /**
     * Shuffles the deck
     */
    public void shuffleDeck() {

        Collections.shuffle(gameDeck);
    }

    /**
     * Gets card at position n.
     * @param pos position of card
     * @return Card object
     */
    public Card getCardAt(int pos) {

        return gameDeck.get(pos);
    }

    /**
     * Returns the current game deck.
     * @return ArrayList<Card> containg deck
     */
    public ArrayList<Card> getGameDeck() {

        return gameDeck;
    }

    /**
     * Chooses the category with the highest stat
     * from the first card of the deck.
     * @return int position of best category
     */
    public int getPosOfHighestStat() {
        return gameDeck.get(0).getPosOfHighestStat();
    }

    /**
     * Returns the stat descriptions of the deck
     * @return String[] with stat descriptions
     */
    public String[] getStatDescr() {
        return statDescr;
    }
}
