package commandline;

import java.util.ArrayList;

/**
 * This class models the Debug Log in a game of Top Trumps
 * played in debug mode. Its purpose is to format game
 * data into Strings that can be printed to a log file.
 */
public class DebugLog {                                         //TODO implement more methods to print other requirements
    IO inputOutput;

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
     * ArrayList of Cards objects to print. Also takes an
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

        for (int i = 0; i < deck.size(); i++) {
            deckLog.append(String.valueOf(deck.get(i)));
            deckLog.append("\n");
        }

        for (int i = 0; i < 25; i++) {      //TODO remove magic number
            deckLog.append("=");
        }
        deckLog.append("\n");

        inputOutput.writeLog(deckLog.toString());
    }
}
