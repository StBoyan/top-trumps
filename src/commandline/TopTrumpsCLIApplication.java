package commandline;

import java.io.File;
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
        Controller appController = new Controller();
        
		while (!userWantsToQuit) {
            appController.showMenu();
            char input = appController.menuInput();

            if (input == 'G' || input == 'g')
            	try {
					appController.startGame(writeGameLogsToFile);
				}
				catch (FileNotFoundException e) {
				userWantsToQuit=true;
				}
            if (input == 'S' || input == 's')
                appController.showStatistics();
            if (input == 'Q' || input == 'q')
                userWantsToQuit=true;
		}
	}
}
