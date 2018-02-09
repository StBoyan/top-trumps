package commandline;

import commandline.controller.GameController;
import java.io.FileNotFoundException;

/**
 * Class that handles a game of Top Trumps in the
 * command line. It has class attributes to modify
 * the number of players in the game and the name
 * of the deck file used for the game. It contains
 * the main application loop in which appropriate
 * methods are called from the GameController object
 * to start a game and show previous game statistics.
 * The CommandLineView object is used to get the user's
 * input.
 */
public class TopTrumpsCLIApplication {

	/**
	 * Main method for the commandline application.
	 * @param args boolean denoting whether debug log is activate
	 */
	public static void main(String[] args) {
		boolean writeGameLogsToFile = false;
		/* Denotes how many players will play the game, inclusive
		 * of human player */
		final int NUM_OF_PLAYERS = 5;
		/* Name of deck file to be used */
		final String DECK_FILE = "deck.txt";

		if (args[0].equalsIgnoreCase("true"))
			writeGameLogsToFile=true;

		boolean userWantsToQuit = false;
        GameController topTrumpsController = new GameController();

		/* Core application loop */
		while (!userWantsToQuit) {
            topTrumpsController.showMenu();
            char input = topTrumpsController.menuInput();

            if (input == 'G' || input == 'g')
            	try {
					topTrumpsController.newGame(NUM_OF_PLAYERS, DECK_FILE, writeGameLogsToFile);
				}
				catch (FileNotFoundException e) {
				userWantsToQuit=true;
				}
            if (input == 'S' || input == 's')
                topTrumpsController.showStatistics();
            if (input == 'Q' || input == 'q')
                userWantsToQuit=true;
		}
	}
}
