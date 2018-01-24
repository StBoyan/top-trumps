package commandline;

import java.io.FileNotFoundException;

/**
 * Class to handle a game of Top Trumps
 */
public class Game {
IO inputOutput;
DebugLog log;
Deck topTrumpsDeck;
Player[] players;
Round topTrumpsRound;
final int NUM_OF_PLAYERS = 5;
/* Array index of the player whose turn it is  */
private int playerTurnPos;


    /**
     * Game default constructor
     * @param io IO object
     * @throws FileNotFoundException if deck file is not found
     */
    public Game(IO io) throws FileNotFoundException{
        inputOutput = io;
        topTrumpsDeck = new Deck(inputOutput);
        players = new Player[NUM_OF_PLAYERS];
        topTrumpsRound = new Round();

        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
            players[i] = new Player();
        }

        playerTurnPos = (int)(Math.random() *4);
    }

    /**
     * Game constructor in debug mode
     * @param io IO object
     * @param dl DebugLog object
     * @throws FileNotFoundException
     */
    public Game(IO io, DebugLog dl) throws FileNotFoundException{
    inputOutput = io;
    log = dl;
    topTrumpsDeck = new Deck(inputOutput);
    topTrumpsRound = new Round();

    log.printDeck(topTrumpsDeck.getGameDeck(), -1);

    players = new Player[NUM_OF_PLAYERS];

        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
             players[i] = new Player();
        }
    }

    /**
     * Deals cards to each player
     */
    public void dealCards() {
        topTrumpsDeck.shuffleDeck();

        int currentPos = 0;
        for (int i = 0; i < topTrumpsDeck.DECK_SIZE; i++) {
            Card c = topTrumpsDeck.getCardAt(i);
            players[currentPos % NUM_OF_PLAYERS].addCard(c);
            currentPos++;
        }

        /* Print player's decks to log in debug mode */
        if (log != null) {
            log.printDeck(topTrumpsDeck.getGameDeck(), -2);

            for (int i = 0; i < NUM_OF_PLAYERS; i++) {
                log.printDeck(players[i].getPlayerDeck(), i);
            }
        }
    }

    /**
     * Plays a single game round
     */
    public boolean playRound() {                           //can make this method throw an exception when
                                                         //human player is eliminated to speed finish game
        return true;  //placeholder
    }
}
