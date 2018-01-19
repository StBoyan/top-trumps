package commandline;

import java.util.Scanner;

/**
 * Class to handle user interaction in the command line
 */
public class CommandLineView {

    public void optionsMenu() {
        System.out.println("Game Menu:");
        System.out.println("1.) Start a new game [G]");
        System.out.println("2.) View statistics [S]");
        System.out.println("3.) Exit [Q]");
        System.out.print("Choose an option: ");
    }

    public char getUserInput() {
        char userSelection;
        Scanner reader = new Scanner(System.in);

        for (;;) {
            userSelection = reader.next().charAt(0); //need to be able to handle empty char or space

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

}
