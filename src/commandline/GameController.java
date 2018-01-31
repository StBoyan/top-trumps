package commandline;

import java.io.FileNotFoundException;
                                            //TODO abstract class -> inherit onlinecontroller & clicontroller
/**
 * TODO Class description
 */
public class GameController {
private CommandLineView console;                //TODO packet models separate => import in online only models
private Game topTrumpsGame;

    /**
     *  Creates a GameController object and initialises the
     *  commandline view.
     */
    public GameController() {
        console = new CommandLineView();
    }

    /**
     * Displays the initial game menu in the commandline view.
     */
    public void showMenu() {
        console.optionsMenu();
    }

    /**
     * Gets the user's menu selection input from the commandline.
     * @return char user input
     */
    public char menuInput() {
        return console.getMenuInput();
    }

    /**
     * TODO Comment method
     * @param debug
     * @throws FileNotFoundException
     */
    public void startGame(boolean debug) throws FileNotFoundException{              //TODO method needs breaking down
        try {
            if (debug) {
                IO inputOutput = new IO(true);
                DebugLog log = new DebugLog(inputOutput);
                topTrumpsGame = new Game(inputOutput, log);
                log.setCategoryLabels(topTrumpsGame.getCategoryLabels());
            } else if (!debug) {
                IO inputOutput = new IO(false);
                topTrumpsGame = new Game(inputOutput);
            }
        }
        catch (FileNotFoundException e) {
            console.deckNotFoundError();
            throw new FileNotFoundException();
        }
        console.setDeckCategoryLabels(topTrumpsGame.getCategoryLabels());
        console.gameStarting();                                                             //TODO implement handle for user elimination and informing who is eliminated
        topTrumpsGame.dealCards();

        while (!topTrumpsGame.isFinished()) {                                             //TODO need to inform user if he is eliminated here and trigger fast finish
            console.informRound(topTrumpsGame.getCurrentRound());
            if (topTrumpsGame.getPlayerCard() != null)
                console.informPlayerCard(topTrumpsGame.getPlayerCard().toString());     //TODO change this and prev line

            int category;
            int actPlayer = topTrumpsGame.getActivePlayer();
            if (actPlayer == 0)
                category = console.informPlayerTurn(actPlayer);     //TODO SPLIT CONSOLE METHOD ON THIS Line - 1 for user input and one just to inform use
            else {
                category = topTrumpsGame.chooseCategoryAI();
                console.informPlayerTurn(actPlayer);
            }
            console.informUserOfCatChosen(category);
            int rndResult = topTrumpsGame.playRound(category);

            if (rndResult == -1) {
                console.informRoundResult(topTrumpsGame.getPlayersDrawCards());
            }
            else {
                console.informRoundResult(rndResult, topTrumpsGame.getWinningCard());
                topTrumpsGame.winnerTakeCards(rndResult);
            }
            topTrumpsGame.temporaryPrintPlayerDecks();
            console.informPlayerEliminations(topTrumpsGame.removeEliminatedPlayers());
        }

        console.informGameWinner(topTrumpsGame.getActivePlayer());
    }

    /**
     * Display game statistic from database
     */
    public void showStatistics() {

    }
}
