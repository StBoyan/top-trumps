package commandline;

import java.io.*;
import java.util.Scanner;

/**
 * Class that handles inputting a deck file and outputting
 * to a log file in debug mode.
 */
public class IO {
    private PrintWriter logWriter;
    private final File DECK_FILE = new File("deck.txt");
    private final File LOG_FILE = new File("toptrumps.log");

    /**
     * Creates an IO object. If the argument passed is true
     * creates a PrintWriter object to facilitate writing to the
     * log file.
     * @param debug whether debug mode is active
     * @throws FileNotFoundException if there is an error creating
     * the log file
     */
    public IO(boolean debug) throws FileNotFoundException{
        if (debug)
            logWriter = new PrintWriter(LOG_FILE);
    }

    /**
     * Reads the deck file and returns it as a String.
     * @return String of the entire deck file
     * @throws FileNotFoundException if the deck file cannot be found
     */
    public String readDeck() throws FileNotFoundException {
        StringBuilder deck = new StringBuilder();
        FileReader reader = new FileReader(DECK_FILE);
        Scanner in = new Scanner(reader);

        while (in.hasNext()) {
            deck.append(in.nextLine()).append("\n");
        }

        in.close();
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deck.toString();
    }

    /**
     * Writes a given String to the log file.
     * @param logText text to be printed
     */
    public void writeLog(String logText) {
        logWriter.append(logText);
        logWriter.flush();
    }
}
