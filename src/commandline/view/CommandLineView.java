package commandline.view;

import commandline.models.Card;
import java.util.Scanner;

/**
 * TODO Class description
 */
public class CommandLineView {
    private String[] deckAttributes;

    /**
     *  Displays the initial game menu.
     */
    public void optionsMenu() {
        System.out.println("\nGame Menu:");
        System.out.println("1.) Start a new game [G]");
        System.out.println("2.) View statistics [S]");
        System.out.println("3.) Exit [Q]");
    }

    /**
     * Get menu selection input from command line
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
     * Informs the user that the deck has not been
     * found.
     */
    public void deckNotFoundError() {
        System.out.println("Error! deck.txt not found");
        System.out.println("The program will now terminate.");
    }

    /**
     * Informs the user that the game is starting
     * and of his player number.
     */
    public void gameStarting() {
        printDelay("Game is about to begin!\n", 1, 350);
        System.out.print("Shuffling deck");
        printDelay(".",3, 350);
        System.out.print("\nDealing cards");
        printDelay(".",3, 350);
        System.out.println("");
        printDelay("You are player #1. Good luck!\n", 1, 450);
    }

    /**
     * Informs the user of current the round number.
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
     * TODO descr and in method
     * @return
     */
    public int getUserCat() {
        printDelay("Your turn to pick a category\n", 1, 500);
        for (int i = 0; i < 5; i++) {
            System.out.print(" (" + (i+1) + ") " + deckAttributes[i]);
        }

        Scanner in = new Scanner(System.in);
        for (;;) {
            System.out.print("\nEnter category number:");
            int cat = in.nextInt();

            if (cat <= 5 && cat >= 1)
                return cat - 1;
        }
    }

    /**
     * TODO description MAKE SURE TO SAY INDEX in comment
     * @param actPlayer
     * @param catPos
     */
    public void informCatChosen(int actPlayer, int catPos) {
        String catLine = String.format("Player #%d picks %s (%d).\n", (actPlayer+1), deckAttributes[catPos], catPos + 1);
         printDelay(catLine, 1, 450);
    }

    /**
     * TODO description
     * @param winnerPos
     * @param c
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
     * TODO Description
     * @param commPile
     */
    public void informRoundDraw(int commPile) {
        String resultLine = "This round is a draw. There are now " + commPile + " cards in the communal pile.\n";

        printDelay(resultLine, 1, 450);
    }

    /**
     * TODO descrp in method too
     * @param elimPlayers
     */
    public void informPlayerEliminations(boolean[] elimPlayers) {
        for (int i = 0; i < elimPlayers.length; i++) {
            if (elimPlayers[i]) {
                String elimLine;
                if (i == 0)
                    elimLine = "You have been eliminated. Better luck next time!";
                else
                    elimLine = "Player #" + (i + 1) + " has been eliminated.\n";

                printDelay(elimLine, 1, 500);
            }
        }
    }

    /**
     * TODO comment
     * @param playerPos
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
