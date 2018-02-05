package commandline.controller;                                    //TODO change position to index throughout code

import commandline.models.DebugLog;
import commandline.models.Game;
import commandline.view.CommandLineView;
import java.io.FileNotFoundException;

/**
 * TODO Class description
 */                                 //TODO fix bug where last round ends in a draw and cards are not distributed properly if possible
public class GameController {
private CommandLineView console;
private Game topTrumpsGame;
private DebugLog log;
private int numOfPlayers;

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
    public void newGame(int playersNum, String deckFileName, boolean debug) throws FileNotFoundException{
        numOfPlayers = playersNum;
        try {
        topTrumpsGame = new Game(numOfPlayers, deckFileName);
        } catch (FileNotFoundException e) {
            console.deckNotFoundError();
            throw new FileNotFoundException();
        }

        console.setDeckCategoryLabels(topTrumpsGame.getCategoryLabels());
        console.gameStarting();

        if (debug) {
            log = new DebugLog();
            log.setCategoryLabels(topTrumpsGame.getCategoryLabels());
            playGameDebug();
        }
        else
            playGame();

        console.informGameWinner(topTrumpsGame.getActivePlayer());
        //TODO database calls will go here
    }

    /**
     * TODO description + in-method comments
     */
    private void playGame() {
        topTrumpsGame.dealCards();

            while(!topTrumpsGame.isFinished()) {
                console.informRound(topTrumpsGame.getRoundsPlayed());

                if (topTrumpsGame.getHumanPlayerCard() != null)
                    console.informPlayerCard(topTrumpsGame.getHumanPlayerCard());

                int category;
                int actPlayer = topTrumpsGame.getActivePlayer();
                if (actPlayer == 0)
                    category = console.getUserCat();
                else {
                    category = topTrumpsGame.chooseCategoryAI();
                    console.informCatChosen(actPlayer, category);
                }

                int rndWinner = topTrumpsGame.playRound(category);

                if (rndWinner == -1)
                    console.informRoundDraw(topTrumpsGame.getCommunalPile().size());
                else {
                    console.informRoundWin(rndWinner, topTrumpsGame.getWinningCard());
                    topTrumpsGame.winnerTakeCards(rndWinner);
                }

                console.informPlayerEliminations(topTrumpsGame.removeEliminatedPlayers());
            }
    }

    /**
     * TODO descrp + in-method comments
     */
    private void playGameDebug() {
        log.printDeck(topTrumpsGame.getGameDeck(), - 1);

        topTrumpsGame.dealCards();
        log.printDeck(topTrumpsGame.getGameDeck(), -2);
        logPlayerDecks();

        while(!topTrumpsGame.isFinished()) {
            console.informRound(topTrumpsGame.getRoundsPlayed());

            if (topTrumpsGame.getHumanPlayerCard() != null)
                console.informPlayerCard(topTrumpsGame.getHumanPlayerCard());

            int category;
            int actPlayer = topTrumpsGame.getActivePlayer();
            if (actPlayer == 0)
                category = console.getUserCat();
            else {
                category = topTrumpsGame.chooseCategoryAI();
                console.informCatChosen(actPlayer, category);
            }

            log.printCommunalPile(topTrumpsGame.getCommunalPile());
            int rndWinner = topTrumpsGame.playRound(category);
            log.printCardsInPlay(topTrumpsGame.getCardsInRound());
            log.printCatValues(category, topTrumpsGame.getCatValues(category));

            if (rndWinner == -1)
                console.informRoundDraw(topTrumpsGame.getCommunalPile().size());
            else {
                console.informRoundWin(rndWinner, topTrumpsGame.getWinningCard());
                topTrumpsGame.winnerTakeCards(rndWinner);
            }

            console.informPlayerEliminations(topTrumpsGame.removeEliminatedPlayers());
            logPlayerDecks();
        }
        log.printGameWinner(topTrumpsGame.getActivePlayer());
    }

    /**
     * Display game statistic from database
     */
    public void showStatistics() {

    }

    /**
     * TODO descrp
     */
    private void logPlayerDecks() {
        for (int i = 0; i < numOfPlayers; i++) {
            if (topTrumpsGame.getPlayersDeckAt(i) != null)
                log.printDeck(topTrumpsGame.getPlayersDeckAt(i), i);
        }
    }
}
