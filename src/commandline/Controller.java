package commandline;

import java.io.FileNotFoundException;

/**
 * Class to serve as controller for the models
 */
public class Controller {
CommandLineView console;
IO inputOutput;
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
        return console.getMenuInput();
    }

    /**
     * Start a new game instance
     * @param debug whether debug mode is activated
     * @throws FileNotFoundException thrown if no deck file is present
     */
    public void startGame(boolean debug) throws FileNotFoundException{
        try {
            if (debug) {
                inputOutput = new IO(true);
                log = new DebugLog(inputOutput);
                topTrumpsGame = new Game(inputOutput, log);
            } else if (!debug) {
                inputOutput = new IO(false);
                topTrumpsGame = new Game(inputOutput);
            }
        }
        catch (FileNotFoundException e) {
            console.deckNotFoundError();
            throw new FileNotFoundException();
        }
        console.setDeckAttributes(topTrumpsGame.getDeckDescriptions());
        console.gameStarting();
        topTrumpsGame.dealCards();

        boolean isLastRound = false;
        while (!isLastRound) {                              // need to inform user if he is eliminated here and trigger fast finish
            console.informRound(topTrumpsGame.getCurrentRound());
            Card playerCard = topTrumpsGame.getPlayerCard();
            console.informPlayerCard(playerCard.toString());

            int categoryPlayed = console.informPlayerTurn(topTrumpsGame.getPlayersTurnPos());

            isLastRound = topTrumpsGame.playRound(categoryPlayed);
            console.informUserOfCatChosen(topTrumpsGame.getStatChosen());//pass category played to playRound
        }                                                               //OR have playRound return int of round winner
    }                                                                   //and run a method

    /**
     * Display game statistic from database
     */
    public void showStatistics() {

    }
}
