package commandline;

import java.util.Scanner;

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
     * Get user input from console
     * G - new game
     * S - statistics
     * Q - quit
     * @return char user input
     */
    public char getUserInput() {
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
                    return userSelection;
                default:
                    System.out.print("\rChoose an option: ");
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

}
