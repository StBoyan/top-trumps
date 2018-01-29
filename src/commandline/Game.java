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
    for (int i = 0; i < NUM_OF_PLAYERS; i++) {
        players[i] = new Player();
    }

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
     * Draws and returns the human player's topmost card.
     * @return Card human player's topmost card
     */
    public Card getPlayerCard() {
        return players[0].getFirstCard();
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

        if (log != null)
            log.printCommunalPile(topTrumpsRound.getCommunalPile());        //TODO NEEDS in-method comments; maybe combine log prints to a separate method

        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
            if (players[i] != null)
                roundCards[i] = players[i].drawCard();
            else
                roundCards[i] = null;
        }

        int roundWinner = topTrumpsRound.compareCards(roundCards, category);
        if (roundWinner != -1)
            activePlayer = roundWinner;

        if (log != null)
            log.printCardsInPlay(topTrumpsRound.getRoundCards());

        if (log!= null) {                           //TODO make this a separate private method
            int[] catVals = new int[NUM_OF_PLAYERS];
            Card[] cards = topTrumpsRound.getRoundCards();
            for (int i = 0; i < NUM_OF_PLAYERS; i++) {
                if (cards[i] != null)
                    catVals[i] = cards[i].getCatValueAt(category);
                else
                    catVals[i] = -1;
            }
            log.printCatValues(category, catVals);
        }


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
            players[winnerPos].addCard(c);
        }
    }

    /**
     * Checks if the game is finished (i.e. there
     * is only 1 player) and returns true if finished
     * or false if not.
     * @return boolean whether the game is finished or not
     */
    public boolean isFinished() {
        int playersLeft = 0;

        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
            if (players[i] != null)
                playersLeft++;
        }

        if (playersLeft > 1)
            return false;
        else
            return true;

    }

    /**
     * Gets the best category (i.e. one with highest value)
     * for an AI player. This method should only be called if
     * it is an AI player's turn.
     * @return int position of category to be played
     */
    public int chooseCategoryAI() {
        return players[activePlayer].getBestCategory();
    }

    /**
     * Removes players who do not have any cards left in their deck
     * from the players array. Returns a boolean array denoting the
     * position of the player(s) which were removed.
     * @return boolean[] pos of eliminated players
     */
    public boolean[] removeEliminatedPlayers() {
        boolean[] isEliminatedPlayers = new boolean[NUM_OF_PLAYERS];

        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
             if (players[i].getPlayerDeck().size() == 0) {           //TODO MAY NEED TO HANDLE NULL POSITIONS
                players[i] = null;
                isEliminatedPlayers[i] = true;
            }
        }

        return isEliminatedPlayers;
    }
}
