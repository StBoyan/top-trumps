package commandline.view;

import commandline.models.Card;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class to model a view that handles the interaction with the
 * user of the application. It has methods to display a game menu and
 * take user input, and display game statistics data. It also informs
 * the user about various events (e.g. round winner, game winner etc.)
 * and take user input when needed. All interactions during a game employ
 * Thread.sleep so as to give time for the human user to read the outcome
 * of the rounds being played.
 */
public class CommandLineView {
    /* Stores the attributes (category labels) for each card */
    private String[] deckAttributes;

    /**
     *  Displays the game menu.
     */
    public void optionsMenu() {
        System.out.println("\nGame Menu:");
        System.out.println("1.) Start a new game [G]");
        System.out.println("2.) View statistics [S]");
        System.out.println("3.) Exit [Q]");
    }

    /**
     * Gets menu selection input from the command line.
     * G - new game
     * S - statistics
     * Q - quit
     * @return char user input
     */
    public char getMenuInput() {
        char userSelection;
        Scanner in = new Scanner(System.in);

        for (;;) {
            System.out.print("Choose an option: ");
            userSelection = in.next().charAt(0);

            switch (userSelection) {
                case 'G':
                case 'g':
                    return userSelection;
                case 'S':
                case 's':
                    return userSelection;
                case 'Q':
                case 'q':
                    return userSelection;
            }
        }
    }

    /**
     * Informs the user when the deck has not been
     * found.
     */
    public void deckNotFoundError() {
        System.out.println("\nError! deck.txt not found");
        System.out.println("The program will now terminate.");
    }

    /**
     * Informs the user that the game is starting
     * and of his player number.
     */
    public void gameStarting() {
        printDelay("\nGame is about to begin!\n", 1, 350);
        System.out.print("Shuffling deck");
        printDelay(".",3, 350);
        System.out.print("\nDealing cards");
        printDelay(".",3, 350);
        System.out.println("");
        printDelay("You are player #1. Good luck!\n", 1, 450);
    }

    /**
     * Informs the user of the current round number.
     * @param rnd rounds played
     */
    public void informRound(int rnd) {
        String roundLine = "\nRound " + (rnd + 1) + " starting!\n\n";
        printDelay(roundLine, 1, 500);
    }

    /**
     * Informs the user of the card drawn.
     * @param c object of the card drawn
     */
    public void informPlayerCard(Card c) {
        printDelay("You draw: ", 1 , 400);
        printDelay(c.toString(), 1, 600);
        System.out.println("");
    }

    /**
     * Informs the user that it's his turn to pick
     * and gets the choice of category.
     * @return int index of the category chosen by the user
     */
    public int getUserCat() {
        printDelay("Your turn to pick a category\n", 1, 500);
        for (int i = 0; i < 5; i++) {
            System.out.print(" (" + (i+1) + ") " + deckAttributes[i]);
        }

        Scanner in = new Scanner(System.in);
        for (;;) {
            System.out.print("\nEnter category number:");
            int cat;

            /* Checks if user input is correct */
            try {
                cat = in.nextInt();
            } catch (InputMismatchException e) {
                continue;
            }

            if (cat <= 5 && cat >= 1)
                return cat - 1;
        }
    }

    /**
     * Informs the user about the category chosen by one of the
     * AI players.
     * @param actPlayer active player (AI)
     * @param catPos index of the position chosen
     */
    public void informCatChosen(int actPlayer, int catPos) {
        String catLine = String.format("Player #%d picks %s (%d).\n", (actPlayer+1), deckAttributes[catPos], catPos + 1);
         printDelay(catLine, 1, 450);
    }

    /**
     * Informs the user about the outcome of the round in case of
     * a win.
     * @param winnerPos index of the player who won
     * @param c Card object of the winning card
     */
    public void informRoundWin(int winnerPos, Card c) {
        String resultLine;

        if (winnerPos == 0)
            resultLine = String.format("You win the round with: %s\n", c.toString());
        else
            resultLine = String.format("Player #%d wins the round with: %s\n", winnerPos + 1, c.toString());

        printDelay(resultLine, 1, 500);
    }

    /**
     * Informs the user about the outcome of the round in case of
     * a draw.
     * @param commPile number of cards in the communal pile
     */
    public void informRoundDraw(int commPile) {
        String resultLine = "This round is a draw. There are now " + commPile + " cards in the communal pile.\n";

        printDelay(resultLine, 1, 450);
    }

    /**
     * Informs the user about players eliminated in a particular
     * round.
     * @param elimPlayers boolean array matching the players indices;
     *                    index with true means player has been eliminated
     *                    at the current round
     */
    public void informPlayerEliminations(boolean[] elimPlayers) {
        for (int i = 0; i < elimPlayers.length; i++) {
            if (elimPlayers[i]) {
                String elimLine;
                if (i == 0)
                    elimLine = "You have been eliminated. Better luck next time!\n";
                else
                    elimLine = "Player #" + (i + 1) + " has been eliminated.\n";

                printDelay(elimLine, 1, 500);
            }
        }
    }

    /**
     * Informs the user of the winner of the game.
     * @param playerPos index of winning player
     */
    public void informGameWinner(int playerPos) {
        String winnerLine;
        if (playerPos == 0)
            winnerLine = "You win the game. Congratulations!\n";
        else
            winnerLine = "Player #" + (playerPos + 1) +" wins the game!\n";

        printDelay(winnerLine, 1, 500);
    }

    /**
     * Prints the overall number of games played.
     * @param gamesPlayed int number of games played
     */
    public void printGamesPlayed(int gamesPlayed) {
        System.out.println(String.format("\nNumber of games played overall: %d", gamesPlayed));
    }

    /**
     * Prints the number of times computer players have
     * won.
     * @param compWins number of times computer players won
     */
    public void printComputerWins(int compWins) {
        System.out.println(String.format("Number of times computer players have won: %d", compWins));
    }

    /**
     * Prints the number of times the human player has won.
     * @param humWins number of times human player won
     */
    public void printHumanWins(int humWins) {
        System.out.println(String.format("Number of times human player has won: %d", humWins));
    }

    /**
     * Prints the average number of draws per game, formatted
     * to 2 places after the decimal point.
     * @param avgDraws average number of draws
     */
    public void printAverageDraws(double avgDraws) {
        System.out.println(String.format("Average number of draws per game: %.2f", avgDraws));
    }

    /**
     * Prints the largest number of rounds played in a single
     * game.
     * @param maxRounds largest number of rounds
     */
    public void printMaxRounds(int maxRounds) {
        System.out.println(String.format("Largest number of rounds played in a game: %d", maxRounds));
    }

    /**
     * Sets the labels of the categories used in the game.
     * @param attr String array with attributes
     */
    public void setDeckCategoryLabels(String[] attr) {
        deckAttributes = attr;
    }

    /**
     * Prints a given text n number of times after a
     * specified delay.
     * @param text String to be printed
     * @param n number of times to print
     * @param ms millisecond delay before printing
     */
    private void printDelay(String text, int n, int ms) {
        for (int i = 0; i < n; i++) {
            try {
                Thread.sleep(ms);
                System.out.print(text);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}