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
final int NUM_OF_PLAYERS = 5;


    /**
     * Game default constructor
     * @param io IO object
     * @throws FileNotFoundException if deck file is not found
     */
    public Game(IO io) throws FileNotFoundException{
        inputOutput = io;
        topTrumpsDeck = new Deck(inputOutput);
        players = new Player[NUM_OF_PLAYERS];

        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
            players[i] = new Player();
        }
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
            players[currentPos % 5].addCard(c);
            currentPos++;
        }

        if (log != null) {
            log.printDeck(topTrumpsDeck.getGameDeck(), -2);

            for (int i = 0; i < NUM_OF_PLAYERS; i++) {
                log.printDeck(players[i].getPlayerDeck(), i);
            }
        }
    }
}
