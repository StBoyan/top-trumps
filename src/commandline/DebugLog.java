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
    private final int LINE_SEPARATOR_LENGTH = 25;

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

        for (int i = 0; i < LINE_SEPARATOR_LENGTH; i++) {
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
}
