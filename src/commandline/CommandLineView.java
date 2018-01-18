package commandline;

/**
 * Class to handle user interaction in a command line
 */
public class CommandLineView {

    public void initialOptions() {
        System.out.println("Choose an option:");
        System.out.println("1.) Start a new game [G]");
        System.out.println("2.) View statistics [S]");
        System.out.println("3.) Exit [Q]");
    }

    public int getUserInput() {
        int userSelection = 0;

        return userSelection;
    }

}
