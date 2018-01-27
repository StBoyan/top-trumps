package commandline;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * This class models the game deck used for a
 * game of Top Trumps. It contains all the Card objects,
 * and the category names, as well as methods to shuffle it,
 * access the Card objects, and the category names.
 */
public class Deck {
    private ArrayList<Card> gameDeck;
    private String[] catLabels;


    /**
     * Creates a Deck object and constructs a deck exactly
     * as read from the external source.
     * @param io IO object
     * @throws FileNotFoundException if external deck file is missing
     */
    public Deck(IO io) throws FileNotFoundException{
        createDeck(io.readDeck());
    }

    /*
     * Creates Card objects and assigns them to an ArrayList
     * from a string representing the deck to be used in the game.
     * @param deck String of the deck used in the game
     */
    private void createDeck(String deck) {
        gameDeck = new ArrayList<Card>();
        Scanner in = new Scanner(deck);

        /* Removes "Description" from the first line of the deck String
         * and puts the names in the catLabels array */
        catLabels = in.nextLine().replace("Description ", "").split(" ");

        while (in.hasNext()) {
            String[] line = in.nextLine().split(" ");
            gameDeck.add(new Card(line));
        }
    }

    /**
     * Shuffles the positions of the Card objects in the
     * ArrayList to a new random order.
     */
    public void shuffleDeck() {
        Collections.shuffle(gameDeck);
    }

    /**
     * Returns the Card object at position n.
     * @param pos position of card
     * @return Card Card object
     */
    public Card getCardAt(int pos) {
        return gameDeck.get(pos);
    }

    /**
     * Returns the ArrayList of the current deck.
     * @return ArrayList<Card> containing the current deck
     */
    public ArrayList<Card> getGameDeck() {
        return gameDeck;
    }

    /**
     * Returns the array of the category labels.
     * @return String[] of category labels
     */
    public String[] getCatLabels() {
        return catLabels;
    }
}
