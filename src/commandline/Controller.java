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
        console.optionsMenu();
    }

    public char userInput() {
        return console.getUserInput();
    }
}
