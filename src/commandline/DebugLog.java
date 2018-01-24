package commandline;

import java.util.ArrayList;

/**
 * Class to generate the debug log
 */
public class DebugLog {
    IO inputOutput;

    public DebugLog(IO io) {
        inputOutput = io;
    }

    /**
     * Prints deck to log file
     * @param deck Arraylist of deck to be printed
     * @param deckType int type of deck; -1 unshuffled deck,
     *                 -2 shuffled deck, 0 human's deck,
     *                 1-4 AI player deck
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

        for (int i = 0; i < 25; i++) { //this will probably have to be changed - magic number
            deckLog.append("=");
        }
        deckLog.append("\n");

        inputOutput.writeLog(deckLog.toString());
    }
}
