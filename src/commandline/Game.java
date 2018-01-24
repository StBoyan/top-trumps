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
private int playersTurnPos;
private int currentRound;

    /**
     * Game default constructor
     * @param io IO object
     * @throws FileNotFoundException if deck file is not found
     */
    public Game(IO io) throws FileNotFoundException{
        inputOutput = io;
        topTrumpsDeck = new Deck(inputOutput);
        players = new Player[NUM_OF_PLAYERS];
        topTrumpsRound = new Round(NUM_OF_PLAYERS);

        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
            players[i] = new Player();
        }

        /* Picks first player at random */
        playersTurnPos = (int)(Math.random() *4);
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
    topTrumpsRound = new Round(NUM_OF_PLAYERS);

    log.printDeck(topTrumpsDeck.getGameDeck(), -1);

    players = new Player[NUM_OF_PLAYERS];

        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
             players[i] = new Player();
        }

    /* Picks first player at random */
    playersTurnPos = (int)(Math.random() *4);
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
     * Gets the attribute descriptions of the deck in play.
     * @return String[] attribute descriptions
     */
    public String[] getDeckDescriptions() {
        return topTrumpsDeck.getStatDescr();
    }

    /**
     * Gets the position of the player whose turn it is.
     * @return
     */
    public int getPlayersTurnPos() {
        return playersTurnPos;
    }

    /**
     * Gets the human player's card             //if this doesnt work -> player eliminated
     * @return                                  //needs to be acounted for in view
     */
    public Card getPlayerCard() {
        return players[0].drawCard();
    }

    /**
     * Returns the current round number
     * @return int current round
     */
    public int getCurrentRound() {
        return topTrumpsRound.getCurrentRound();
    }

    public int getStatChosen() {
        return topTrumpsRound.getStatChosen();
    }

    /**
     * Plays a round of Top Trumps .
     * @param humanCategoryChoice category chosen by human or -1 if not human's turn
     * @return boolean if this is last round
     */
    public boolean playRound(int humanCategoryChoice) {
        Card[] round = new Card[NUM_OF_PLAYERS];

        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
            round[i] = players[i].drawCard();
        }

        if (humanCategoryChoice == -1) {
            topTrumpsRound.compareCards(round, players[playersTurnPos].aiChooseCategory());
        }
        else
            topTrumpsRound.compareCards(round, humanCategoryChoice);

        return true;  //placeholder
    }
}
