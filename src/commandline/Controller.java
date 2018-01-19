package commandline;

import java.io.FileNotFoundException;

/**
 * Class to serve as controller for the models
 */
public class Controller {
CommandLineView console;
Game topTrumpsGame;
DebugLog log;

    /**
     *  Constructor
     */
    public Controller() {
        console = new CommandLineView();
    }

    /**
     * Display menu
     */
    public void showMenu() {
        console.optionsMenu();
    }

    /**
     * User input from menu
     * @return char user input
     */
    public char menuInput() {
        return console.getUserInput();
    }

    /**
     * Start a new game instance
     * @param debug whether debug mode is activated
     * @throws FileNotFoundException thrown if no deck file is present
     */
    public void startGame(boolean debug) throws FileNotFoundException{
        try {
            if (debug) {
                log = new DebugLog();
                topTrumpsGame = new Game(log);
            } else if (!debug)
                topTrumpsGame = new Game();
        }
        catch (FileNotFoundException e) {
            console.deckNotFoundError();
            throw new FileNotFoundException();
        }
    }

    /**
     * Display game statistic from database
     */
    public void showStatistics() {

    }
}
