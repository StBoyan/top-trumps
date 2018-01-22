package commandline;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

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
     */
    public Game(IO io) throws FileNotFoundException{
        inputOutput = io;
        topTrumpsDeck = new Deck(inputOutput);
        players = new Player[NUM_OF_PLAYERS];
    }

    /**
     * Game constructor in debug mode
     * @param dl DebugLog object
     */
    public Game(IO io, DebugLog dl) throws FileNotFoundException{
    inputOutput = io;
    log = dl;
    topTrumpsDeck = new Deck(inputOutput);
    players = new Player[NUM_OF_PLAYERS];
    }

    public void dealCards() {
        int currentPos = 0;
        for (int i = 0; i < topTrumpsDeck.DECK_SIZE; i++) {
            Card c = topTrumpsDeck.getCardAt(i);
            players[currentPos % 5].addCard(c);
        }
    }
}
