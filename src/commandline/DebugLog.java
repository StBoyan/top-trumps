package commandline;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This class models the Debug Log in a game of Top Trumps
 * played in debug mode. Its purpose is to format game
 * data into Strings that can be printed to a log file.
 */
public class DebugLog {                                         //TODO implement more methods to print other requirements
    private IO inputOutput;
    private String[] categoryLabels;
    private final int LINE_SEPARATOR_LENGTH = 25;                           //TODO separate rounds with "=" and info within round with "-"

    /**
     * Creates a DebugLog object and assigns the IO
     * object to be used for printing to the log file.
     * @param io IO object
     */
    public DebugLog(IO io) {
        inputOutput = io;
    }

    /**
     * Formats and writes a deck to the log file. Takes an
     * ArrayList of Card objects to print. Also takes an
     * integer which is used to put a label before each
     * deck. Possible values are -2 for shuffled, -1 for
     * shuffled, and 0 to 4 for Player decks.
     * @param deck ArrayList of Card objects
     * @param deckType type of deck
     */
    public void printDeck(ArrayList<Card> deck, int deckType) {
        StringBuilder deckLog = new StringBuilder();
        if (deckType == -1)
            deckLog.append("\tUnshuffled Deck:\n");
        else if (deckType == -2)
            deckLog.append("\tShuffled Deck:\n");
        else if (deckType == 0)
            deckLog.append("\tPlayer 1 (Human) Deck:\n");
        else {
            deckLog.append("\tPlayer ");
            deckLog.append(deckType + 1);
            deckLog.append(" (AI) Deck:\n");
        }

        for (Card c : deck) {
            deckLog.append(String.valueOf(c)).append("\n");
        }

        for (int i = 0; i < LINE_SEPARATOR_LENGTH; i++) {   //TODO make private method
            deckLog.append("=");
        }
        deckLog.append("\n");

        inputOutput.writeLog(deckLog.toString());
    }

    /**
     * Formats and writes the communal pile to the log file.
     * Takes an ArrayList of Card objects to print. If the
     * ArrayList is empty an appropriate message is printed
     * instead.
     * @param commPile ArrayList of Card objects
     */
    public void printCommunalPile(ArrayList<Card> commPile) {
        StringBuilder pileLog = new StringBuilder();

        if (commPile.size() == 0) {
            pileLog.append("Communal pile is empty.\n");
        }
        else {
            pileLog.append("\tCommunal pile:\n");
            for (Card c : commPile) {
                pileLog.append(String.valueOf(c)).append("\n");
            }
        }

        for (int i = 0; i < LINE_SEPARATOR_LENGTH; i++) {
            pileLog.append("=");
        }
        pileLog.append("\n");

        inputOutput.writeLog(pileLog.toString());
    }

    /**
     * Formats and writes the cards in play in a round to the
     * log file. Takes an array of Card objects. Prints "no card"
     * if a position is empty (i.e. in case of an eliminated player).
     * @param cards cards in play Array
     */
    public void printCardsInPlay(Card[] cards) {
        StringBuilder cardsLog = new StringBuilder();

        cardsLog.append("Cards in play:\n");

        for (int i = 0; i < cards.length; i++) {
            cardsLog.append("P#").append(i + 1).append(" ");
            if (cards[i] != null)
                cardsLog.append(String.valueOf(cards[i])).append("\n");
            else
                cardsLog.append("no card\n");
        }

        for (int i = 0; i < LINE_SEPARATOR_LENGTH; i++) {
            cardsLog.append("=");
        }
        cardsLog.append("\n");

        inputOutput.writeLog(cardsLog.toString());
    }

    /**
     * Formats and writes the category chosen and the values
     * that are compared in a round to the log file. Takes an
     * integer denoting the position of the category chosen
     * and an integer array containing the values compared.
     * @param cat position of category chosen
     * @param catValues values compared
     */
    public void printCatValues(int cat, int[] catValues) {
        StringBuilder catLog = new StringBuilder();

        catLog.append("Category selected is ").append(categoryLabels[cat]);
        catLog.append("\n");

        for (int i = 0; i < catValues.length; i ++) {
            catLog.append("P#").append(i + 1).append(" ");
            if (catValues[i] != -1)
                catLog.append(catValues[i]).append("\n");
            else
                catLog.append("no value\n");
        }

        for (int i = 0; i < LINE_SEPARATOR_LENGTH; i++) {
            catLog.append("=");
        }
        catLog.append("\n");

        inputOutput.writeLog(catLog.toString());
    }

    /**
     * Sets the category labels that will be used in
     * the game.
     * @param catLabels String[] with category labels
     */
    public void setCategoryLabels(String[] catLabels) {
        categoryLabels = catLabels;
    }
}
