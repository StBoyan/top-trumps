package commandline;

import commandline.controller.GameController;
import java.io.FileNotFoundException;

/**
 * TODO generic commnents about the CLI app
 */
public class TopTrumpsCLIApplication {

	/**
	 * TODO comment here and throughout method if necessary
	 * @param args
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
