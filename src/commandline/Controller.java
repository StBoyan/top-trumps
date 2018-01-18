package commandline;

/**
 * Class to serve as controller for the models
 */
public class Controller {
CommandLineView console;

    public Controller() {
        console = new CommandLineView();
    }

    public void showMenu() {
        console.initialOptions();
    }

    public int userInput() {
        return console.getUserInput();
    }
}
