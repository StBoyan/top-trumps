package commandline;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Class to handle user interaction in the command line
 */
public class CommandLineView {

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
    }

    /**
     * Prints given text n number of times after a
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
