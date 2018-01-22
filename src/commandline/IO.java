package commandline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Class to do input and output to external files
 */
public class IO {
    private final File DECK_FILE = new File("deck.txt");
    private final File LOG_FILE = new File("toptrumps.log");
    FileReader reader;
    PrintWriter logWriter;
    /* 1 description line and 40 card lines */
    final int DECK_FILE_ROWS = 41;
    /* card name and 5 characteristics */
    final int DECK_FILE_COLUMNS = 6;

    public IO(boolean debug) throws FileNotFoundException{  //may need to implement different user info in case
        if (debug)                                          //of PrintWriter throwing exception
            logWriter = new PrintWriter(LOG_FILE);
    }
    /**
     * Reads deck file
     * @return String[][] 2d array with deck info
     * @throws FileNotFoundException if deck file is not present
     */
    public String[][] readDeck() throws FileNotFoundException {
        String[][] deck = new String[DECK_FILE_ROWS][DECK_FILE_COLUMNS];
        reader = new FileReader(DECK_FILE);
        Scanner in = new Scanner(reader);

        int currentRow = 0;
        while (in.hasNext()) {
            String[] deckFileLine = in.nextLine().split(" ");

            for (int i = 0; i < DECK_FILE_COLUMNS; i++) {
                deck[currentRow][i] = deckFileLine[i];
            }

            currentRow++;
        }
                                    //need to close reader and scanner object
        return deck;
    }

    /**
     * Writes to the logfile
     * @param logText text to be printed
     */
    public void writeLog(String logText) {
        logWriter.append(logText);
        logWriter.flush();
    }
}
