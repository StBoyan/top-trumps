package commandline;

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
        
		// Main application loop
		while (!userWantsToQuit) {
            appController.showMenu();
            int input = appController.userInput();

            if (input == 1)
                break; //start game
            if (input == 2)
                break; //show statistics
            if (input == 3)
                userWantsToQuit=true;

		}
	}
}
