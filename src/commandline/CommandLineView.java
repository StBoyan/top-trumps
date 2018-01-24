package commandline;

import java.util.Scanner;

/**
 * Class to handle user interaction in the command line
 */
public class CommandLineView {
    String[] deckAttributes;

    /**
     *  Display initial options
     */
    public void optionsMenu() {
        System.out.println("Game Menu:");
        System.out.println("1.) Start a new game [G]");
        System.out.println("2.) View statistics [S]");
        System.out.println("3.) Exit [Q]");
        System.out.print("Choose an option: ");
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
        Scanner in = new Scanner(System.in); //may need to increase scope to use in other user interactions

        for (;;) {
            userSelection = in.next().charAt(0); //need to be able to handle empty char or space

            switch (userSelection) {
                case 'G':
                case 'g':
                    return userSelection;
                case 'S':
                case 's':
                    return userSelection;
                case 'Q':
                case 'q':
                    return userSelection;       //case empty string
                default:
                    System.out.print("Choose an option: ");
                    break;
            }
        }
    }

    /**
     * Informs the user of deck not found error
     */
    public void deckNotFoundError() {
        System.out.println("Error! deck.txt not found");
        System.out.println("The program will now terminate.");
    }

    /**
     * Informs the user the game is about to begin
     */
    public void gameStarting() {
        System.out.println("Game is about to begin!");
        System.out.print("Shuffling deck");
        printDelay(".",3, 450);
        System.out.print("\nDealing cards");
        printDelay(".",3, 450);
        System.out.println("");
        printDelay("You are player #1\n\n", 1, 500);
    }

    /**
     * Informs the player of the round number.
     * @param rnd round number
     */
    public void informRound(int rnd) {
        String roundLine = "Round " + rnd + " starting!\n";
        printDelay(roundLine, 1, 450);
    }

    /**
     * Informs the player of the card drawn
     * @param card text representation of the card drawn
     */
    public void informPlayerCard(String card) {
        System.out.print("You draw: ");
        printDelay(card, 1, 600);
        System.out.println("");
    }

    /**
     * Informs the user of whose turn it is and takes
     * input if human's turn.
     * @param playerTurn player's turn; 0 - human
     *                   1-4 - AI players
     * @return category selection; -1 if its not human's turn
     */
    public int informPlayerTurn(int playerTurn) {       //method name sucks
        if (playerTurn == 0) {
            printDelay("Your turn to pick a category\n", 1, 500);
            for (int i = 0; i < 5; i++) {
                System.out.print(" (" + (i+1) + ") " + deckAttributes[i]);
            }
            System.out.print("\nEnter category number:");
            Scanner in = new Scanner(System.in);
            return in.nextInt() - 1;            //needs to be able to validate user input
        }
        else {
            System.out.println("Player " + (playerTurn + 1) + " picks a category.");
            return -1;
        }
    }

    /**
     * Informs user of what category was chosen.
     * @param catPos int of position of category
     */
    public void informUserOfCatChosen(int catPos) {
        String catLine = String.format("Cards are compared by " + deckAttributes[catPos] + ".\n");
        printDelay(catLine, 1, 350);
    }

    /**
     * Sets the name of the attributes used in the game.
     * @param attr String array with attributes
     */
    public void setDeckAttributes(String[] attr) {
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
