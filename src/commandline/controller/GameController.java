package commandline.controller;

import commandline.models.Database;
import commandline.models.DebugLog;
import commandline.models.Game;
import commandline.view.CommandLineView;
import java.io.FileNotFoundException;

/**
 * Class to model the controller for the command line
 * version of the game. It creates all the objects needed
 * to play the game and contains the core application logic.
 */
public class GameController {
private CommandLineView console;
private Game topTrumpsGame;
private DebugLog log;
private Database topTrumpsDatabase;
private int numOfPlayers;

    /**
     *  Creates a GameController object and initialises the
     *  commandline view.
     */
    public GameController() {
        console = new CommandLineView();
        topTrumpsDatabase = new Database();
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
     * Starts a new game of Top Trumps in the command line.
     * @param playersNum number of players
     * @param deckFile name of deck file
     * @param debug whether debug mode is on
     * @throws FileNotFoundException
     */
    public void newGame(int playersNum, String deckFile, boolean debug) throws FileNotFoundException{
        numOfPlayers = playersNum;
        try {
        topTrumpsGame = new Game(numOfPlayers, deckFile);
        } catch (FileNotFoundException e) {
            console.deckNotFoundError();
            throw new FileNotFoundException();
        }

        console.setDeckCategoryLabels(topTrumpsGame.getCategoryLabels());
        console.gameStarting();

        /* Starts game in debug mode */
        if (debug) {
            log = new DebugLog();
            log.setCategoryLabels(topTrumpsGame.getCategoryLabels());
            playGameDebug();
        }
        /* Starts game in normal mode */
        else
            playGame();

        /* Informs the user of the game winner */
        console.informGameWinner(topTrumpsGame.getActivePlayer());

        /* Connects to the database and inserts data after a
         * game has finished and then disconnect */
        topTrumpsDatabase.connect();
        topTrumpsDatabase.insertData(topTrumpsGame.getRoundsPlayed(), topTrumpsGame.getDrawStat(), topTrumpsGame.getActivePlayer(), topTrumpsGame.getPlayerWonRoundStat());
        topTrumpsDatabase.disconnect();
    }

    /**
     * Helper method which contains the game logic in a
     * regular game (no debug).
     */
    private void playGame() {
        topTrumpsGame.dealCards();

            /* Core game loop */
            while(!topTrumpsGame.isFinished()) {
                console.informRound(topTrumpsGame.getRoundsPlayed());

                /* If human player is in game informs him of his card */
                if (topTrumpsGame.getHumanPlayerCard() != null)
                    console.informPlayerCard(topTrumpsGame.getHumanPlayerCard());

                /* Checks who is the active player and determines
                 * category to be played */
                int category;
                int actPlayer = topTrumpsGame.getActivePlayer();
                if (actPlayer == 0)
                    category = console.getUserCat();
                else {
                    category = topTrumpsGame.chooseCategoryAI();
                    console.informCatChosen(actPlayer, category);
                }

                int rndWinner = topTrumpsGame.playRound(category);

                /* Informs the user of the result of the round */
                if (rndWinner == -1)
                    console.informRoundDraw(topTrumpsGame.getCommunalPile().size());
                else {
                    console.informRoundWin(rndWinner, topTrumpsGame.getWinningCard());
                    topTrumpsGame.winnerTakeCards(rndWinner);
                }

                /* Eliminates the players with no cards left in their
                 * deck and informs the user */
                console.informPlayerEliminations(topTrumpsGame.removeEliminatedPlayers());
            }
    }

    /**
     * Helper method which contains the game logic
     * in a game in debug mode.
     */
    private void playGameDebug() {
        log.printDeck(topTrumpsGame.getGameDeck(), - 1);

        topTrumpsGame.dealCards();
        log.printDeck(topTrumpsGame.getGameDeck(), -2);
        logPlayerDecks();

        /* Core game loop */
        while(!topTrumpsGame.isFinished()) {
            console.informRound(topTrumpsGame.getRoundsPlayed());

            /* If human player is in game informs him of his card */
            if (topTrumpsGame.getHumanPlayerCard() != null)
                console.informPlayerCard(topTrumpsGame.getHumanPlayerCard());

            /* Checks who is the active player and determines
             * category to be played */
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

            /* Informs the user of the result of the round */
            if (rndWinner == -1)
                console.informRoundDraw(topTrumpsGame.getCommunalPile().size());
            else {
                console.informRoundWin(rndWinner, topTrumpsGame.getWinningCard());
                topTrumpsGame.winnerTakeCards(rndWinner);
            }

            /* Eliminates the players with no cards left in their
             * deck and informs the user */
            console.informPlayerEliminations(topTrumpsGame.removeEliminatedPlayers());
            logPlayerDecks();
        }
        log.printGameWinner(topTrumpsGame.getActivePlayer());
    }

    /**
     * Takes information from the database about previous games
     * and displays it to the user.
     */
    public void showStatistics() {
        topTrumpsDatabase.connect();
        console.printGamesPlayed(topTrumpsDatabase.getGamesPlayed());
        console.printComputerWins(topTrumpsDatabase.getComputerWins());
        console.printHumanWins(topTrumpsDatabase.getHumanWins());
        console.printAverageDraws(topTrumpsDatabase.getAverageDraws());
        console.printMaxRounds(topTrumpsDatabase.getMaxRounds());
        topTrumpsDatabase.disconnect();
    }

    /**
     * Helper method to print each player's deck to
     * the log file.
     */
    private void logPlayerDecks() {
        for (int i = 0; i < numOfPlayers; i++) {
            if (topTrumpsGame.getPlayersDeckAt(i) != null)
                log.printDeck(topTrumpsGame.getPlayersDeckAt(i), i);
        }
    }
}
