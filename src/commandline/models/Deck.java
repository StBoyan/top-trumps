package commandline.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * This class models the game deck used for a game of
 * Top Trumps. It contains all the Card objects, and the
 * category names, as well as methods to create the
 * deck, shuffle it, access the Card objects, and the
 * category names.
 */
public class Deck {
    private ArrayList<Card> gameDeck;
    private String[] catLabels;

    /**
     * Creates a Deck object and initialises the class
     * attributes by calling createDeck().
     * @throws FileNotFoundException if deck file is not found
     */
    public Deck(String deckFileName) throws FileNotFoundException{
        createDeck(deckFileName); // to be removed
//        gameDeck = new ArrayList<Card>();
    }

    /**
     * Creates a String array with the category labels and an
     * ArrayList of Card object as read from a deck file.
     * @throws FileNotFoundException if deck file is not found
     */
    private void createDeck(String deckFileName) throws FileNotFoundException {
        final File DECK_FILE = new File(deckFileName);
        gameDeck = new ArrayList<Card>();
        FileReader reader = new FileReader(DECK_FILE);
        Scanner in = new Scanner(reader);

        /* Removes "Description" from the first line of the deck String
         * and puts the labels in the catLabels array */
        catLabels = in.nextLine().replace("Description ", "").split(" ");

        while (in.hasNext()) {
            String[] line = in.nextLine().split(" ");
            gameDeck.add(new Card(line));
        }

        in.close();
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
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
     * Returns the Card object at index n.
     * @param pos index of card
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

