package commandline;

import java.io.FileNotFoundException;

/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApplication {

     /**
	 * This main method is called by TopTrumps.java when the user specifies that they want to run in
	 * command line mode. The contents of args[0] is whether we should write game logs to a file.
 	 * @param args
	 */
	public static void main(String[] args) {
		boolean writeGameLogsToFile = false;

		if (args[0].equalsIgnoreCase("true"))
			writeGameLogsToFile=true;
		
		boolean userWantsToQuit = false;
        GameController topTrumpsController = new GameController();
        
		while (!userWantsToQuit) {
            topTrumpsController.showMenu();
            char input = topTrumpsController.menuInput();

            if (input == 'G' || input == 'g')
            	try {
					topTrumpsController.startGame(writeGameLogsToFile);
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
