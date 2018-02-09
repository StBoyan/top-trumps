package commandline.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * This class models the Debug Log in a game of Top Trumps
 * played in debug mode. Its purpose is to format game
 * data into Strings and print them to the log file.
 */
public class DebugLog {
    private PrintWriter logWriter;
    private String[] categoryLabels;

    /**
     * Creates a DebugLog object and initialises a PrintWriter
     * with the name of the file to write to.
     */
    public DebugLog() {
        File logFile = new File("toptrumps.log");
        try {
            logWriter = new PrintWriter(logFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Formats and writes a deck to the log file. Takes an
     * ArrayList of Card objects to print. Also takes an
     * integer which is used to put a label before each
     * deck. Possible values are -2 for shuffled, -1 for
     * shuffled, and 0 to 4 for Player decks (corresponding
     * to their player index).
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

        printSeparator(deckLog);

        writeLog(deckLog.toString());
    }

    /**
     * Formats and writes the communal pile to the log file.
     * Takes an ArrayList of Card objects to print. If the
     * ArrayList is empty - writes a message stating that.
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

        printSeparator(pileLog);

        writeLog(pileLog.toString());
    }

    /**
     * Formats and writes the cards in play in a round to the
     * log file. Takes an array of Card objects. Prints "no card"
     * if an index is empty (i.e. in case of an eliminated player).
     * @param cards cards in play Array
     */
    public void printCardsInPlay(Card[] cards) {
        StringBuilder cardsLog = new StringBuilder();

        cardsLog.append("\tCards in play:\n");

        for (int i = 0; i < cards.length; i++) {
            cardsLog.append("P#").append(i + 1).append(" ");
            if (cards[i] != null)
                cardsLog.append(String.valueOf(cards[i])).append("\n");
            else
                cardsLog.append("no card\n");
        }

        printSeparator(cardsLog);

        writeLog(cardsLog.toString());
    }

    /**
     * Formats and writes the category chosen and the values
     * that are compared in a round to the log file. Takes an
     * integer denoting the index of the category chosen
     * and an integer array containing the values compared.
     * If a value is -1, prints "no value" (i.e. eliminated
     * player).
     * @param cat index of category chosen
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

        printSeparator(catLog);

        writeLog(catLog.toString());
    }

    /**
     * Formats and writes the winner of the game to
     * the log file. Takes an integer denoting the index
     * of the player who won.
     * @param winPos index of winner
     */
    public void printGameWinner(int winPos) {
        StringBuilder winLog = new StringBuilder();

        winLog.append("\tWinner of the game:\n");
        if (winPos == 0)
            winLog.append("Player #1 (Human player)");
        else
            winLog.append("Player #").append(winPos+1).append(" (AI Player)");

        writeLog(winLog.toString());
    }

    /**
     * Sets the category labels that will be used in
     * the game.
     * @param catLabels String[] with category labels
     */
    public void setCategoryLabels(String[] catLabels) {
        categoryLabels = catLabels;
    }

    /**
     * Takes a StringBuilder object and appends a line
     * used to separate the different contents reported
     * in the log file.
     * @param sb StringBuilder object to append to
     */
    private void printSeparator(StringBuilder sb) {
        final char LINE_SEPARATOR = '=';
        final int LINE_SEPARATOR_LENGTH = 25;
        for (int i = 0; i < LINE_SEPARATOR_LENGTH; i++) {
            sb.append(LINE_SEPARATOR);
        }
        sb.append("\n");
    }

    /**
     * Writes a given String to the log file.
     * @param logText text to be printed
     */
    private void writeLog(String logText) {
        logWriter.append(logText);
        logWriter.flush();
    }
}
