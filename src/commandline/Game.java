package commandline;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class handles a single game of Top Trumps. It maintains
 * a list of the players in the game and the active player at
 * any moment. It has various methods to distribute cards (both
 * at beginning and after a round), methods to access attributes
 * in Round, methods to access the human player's top cards and
 * others.
 */
public class Game {         //TODO METHODS TO ACCESS INFORMATION FOR THE DATABASE AND FOR THE LOG FROM ROUND
private DebugLog log;
private Deck topTrumpsDeck;
private Round topTrumpsRound;
/* Array containing all players currently in
 * the game in their positions. The human player
 * is at position 0 and the players never change
 * position. */
private Player[] players;
/* Position of active player in the game  */
private int activePlayer;
private final int NUM_OF_PLAYERS = 5;


    /**
     * Creates a game object. Deck, Round, and Player
     * objects are initialised. First active player is
     * chosen at random.
     * @param io IO object
     * @throws FileNotFoundException if deck file is not found
     */
    public Game(IO io) throws FileNotFoundException{
        topTrumpsDeck = new Deck(io);
        topTrumpsRound = new Round(NUM_OF_PLAYERS);

        players = new Player[NUM_OF_PLAYERS];
        Arrays.fill(players, new Player());

        activePlayer = (int)(Math.random() *4);
    }

    /**
     * Creates a game object in debug mode. Deck, Round, DebugLog,
     * and Player objects are initialised. The original deck is
     * printed to the log. First active player is chosen at random.
     * @param io IO object
     * @param dl DebugLog object
     * @throws FileNotFoundException if deck file is not found
     */
    public Game(IO io, DebugLog dl) throws FileNotFoundException{
    topTrumpsDeck = new Deck(io);
    topTrumpsRound = new Round(NUM_OF_PLAYERS);

    log = dl;
    log.printDeck(topTrumpsDeck.getGameDeck(), -1);

    players = new Player[NUM_OF_PLAYERS];
    Arrays.fill(players, new Player());

    activePlayer = (int)(Math.random() *4);
    }

    /**
     * Shuffles the deck and assigns all game deck
     * cards to each player's individual deck. If in debug
     * mode - the shuffled deck and each player's deck are
     * printed to the log.
     */
    public void dealCards() {
        topTrumpsDeck.shuffleDeck();

        int deckSize = topTrumpsDeck.getGameDeck().size();
        int currentPos = 0;
        for (int i = 0; i < deckSize; i++) {
            Card c = topTrumpsDeck.getCardAt(i);
            players[currentPos % NUM_OF_PLAYERS].addCard(c);
            currentPos++;
        }

        if (log != null) {
            /* Prints shuffled deck to log file */
            log.printDeck(topTrumpsDeck.getGameDeck(), -2);

            /* Prints each player's deck to log file */
            for (int i = 0; i < NUM_OF_PLAYERS; i++) {
                log.printDeck(players[i].getPlayerDeck(), i);
            }
        }
    }

    /**
     * Gets an array containing the deck's category labels.
     * @return String[] category labels
     */
    public String[] getCategoryLabels() {
        return topTrumpsDeck.getCatLabels();
    }

    /**
     * Gets the position of the current active player.
     * @return int active player position
     */
    public int getActivePlayer() {
        return activePlayer;
    }

    /**
     * Draws and returns the human player's topmost card.               //if this doesnt work -> player eliminated
     * @return Card human player's topmost card                        //needs to be acounted for in view
     */
    public Card getPlayerCard() {
        return players[0].drawCard();
    }

    /**
     * Gets the current round number.
     * @return int current round
     */
    public int getCurrentRound() {
        return topTrumpsRound.getCurrentRound();
    }

    /**
     * Draws cards from each player's deck and plays a round
     * with a given category. Changes current active player
     * if round wasn't a draw. Returns position of winner
     * or -1 if round was a draw.
     * @param category position of category
     * @return int position of winner or -1 in case of draw
     */
    public int playRound(int category) {
        Card[] roundCards = new Card[NUM_OF_PLAYERS];

        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
            roundCards[i] = players[i].drawCard();      //if NULL skip
        }

        int roundWinner = topTrumpsRound.compareCards(roundCards, category);
        if (roundWinner != -1)
            activePlayer = roundWinner;

        return roundWinner;
    }

    /**
     * Gets a Card array with the cards that ended
     * in a draw. The Cards' position in the array match
     * the position of the player.
     * @return Card[] draw cards
     */
    public Card[] getPlayersDrawCards() {
        return topTrumpsRound.getDrawCards();
    }

    /**
     * Gets the card that won the round.
     * @return Card winning card
     */
    public Card getWinningCard() {
        return topTrumpsRound.getWinningCard();
    }

    /**
     * Gets all the cards up for taking in the round
     * and assign them to the winning player.
     * @param winnerPos position of winner
     */
    public void winnerTakeCards(int winnerPos) {
        ArrayList<Card> cards = topTrumpsRound.takeAllCards();
        for (Card c: cards) {
            players[activePlayer].addCard(c);
        }
    }
}
