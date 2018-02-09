package commandline.models;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class handles a single game of Top Trumps. It maintains
 * a list of the players in the game and the active player at
 * any moment. It has various methods to distribute cards (both
 * at beginning and after a round), methods to access attributes
 * in Round, methods to access the human player's top cards and
 * others. There are also methods to access information needed for
 * the debug log and the database.
 */
public class Game {
private Deck topTrumpsDeck;
private Round topTrumpsRound;
/* Array containing all indexed players currently in
 * the game. The human player is at index 0 and the
 * players never change position. */
private Player[] players;
/* Index of active player in the game  */
private int activePlayer;
private int numOfPlayers;

    /**
     * Creates a game object. Deck, Round, and Player
     * objects are initialised. First active player is
     * chosen at random.
     * @param numPlayers number of players
     * @throws FileNotFoundException if deck file is not found
     */
    public Game(int numPlayers, String deckFileName) throws FileNotFoundException{
        numOfPlayers = numPlayers;
        topTrumpsDeck = new Deck(deckFileName);
        topTrumpsRound = new Round(numOfPlayers);

        players = new Player[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++) {
            players[i] = new Player();
        }

        activePlayer = (int)(Math.random() *4);
    }

    /**
     * Shuffles the deck and assigns all game deck
     * cards to each player's individual deck.
     */
    public void dealCards() {
        topTrumpsDeck.shuffleDeck();

        int deckSize = topTrumpsDeck.getGameDeck().size();
        int currentPos = 0;
        for (int i = 0; i < deckSize; i++) {
            Card c = topTrumpsDeck.getCardAt(i);
            players[currentPos % numOfPlayers].addCard(c);
            currentPos++;
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
     * Gets the index of the current active player.
     * @return int active player position
     */
    public int getActivePlayer() {
        return activePlayer;
    }

    /**
     * Draws and returns the human player's topmost card.
     * @return Card human player's topmost card
     */
    public Card getHumanPlayerCard() {
        if (players[0] != null)
            return players[0].getFirstCard();
        else
            return null;
    }

    /**
     * Gets the number of rounds played.
     * @return int rounds played
     */
    public int getRoundsPlayed() {
        return topTrumpsRound.getRoundsPlayed();
    }

    /**
     * Draws cards from each player's deck and plays a round
     * with a given category. Changes current active player
     * if round wasn't a draw. Returns index of winner
     * or -1 if round was a draw.
     * @param category index of category
     * @return int index of winner or -1 in case of draw
     */
      public int playRound(int category) {
        Card[] roundCards = new Card[numOfPlayers];

        for (int i = 0; i < numOfPlayers; i++) {
            if (players[i] != null)
                roundCards[i] = players[i].drawCard();
            else
                roundCards[i] = null;
        }

        int roundWinner = topTrumpsRound.compareCards(roundCards, category);
        if (roundWinner != -1)
            activePlayer = roundWinner;

        return roundWinner;
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
            if (c != null)
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

        for (int i = 0; i < numOfPlayers; i++) {
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
        boolean[] isEliminatedPlayers = new boolean[numOfPlayers];

        for (int i = 0; i < numOfPlayers; i++) {
            if (players[i] != null)
                if (players[i].getPlayerDeck().size() == 0) {
                    players[i] = null;
                    isEliminatedPlayers[i] = true;
            }
        }

        return isEliminatedPlayers;
    }

    /**
     * Returns ArrayList of card objects representing
     * the communal pile.
     * @return ArrayList<Card> communal pile
     */
    public ArrayList<Card> getCommunalPile(){
        return topTrumpsRound.getCommunalPile();
    }

    /**
     * Returns number of draw rounds.
     * @return int draw rounds
     */
    public int getDrawStat() {
        return topTrumpsRound.getDrawRounds();
    }

    /**
     * Returns stats for rounds won by each player
     * in an int array.
     * @return int[] player wins
     */
    public int[] getPlayerWonRoundStat() {
        return topTrumpsRound.getPlayersWonRounds();
    }

    /**
     * Returns the game deck ArrayList.
     * @return ArrayList<Card> game deck
     */
    public ArrayList<Card> getGameDeck() {
        return topTrumpsDeck.getGameDeck();
    }

    /**
     * Returns the player's Deck at a certain index in
     * an ArrayList.
     * @param pos index of player
     * @return ArrayList<Card> player deck
     */
    public ArrayList<Card> getPlayersDeckAt(int pos) {
        try {
            return players[pos].getPlayerDeck();
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Returns the cards in the current round.
     * @return Card[] cards in round
     */
    public Card[] getCardsInRound() {
        return topTrumpsRound.getRoundCards();
    }

    /**
     * Returns the array of player objects
     * @return Player[] players
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Returns the values of all cards in a round
     * for a given category index in an array of integers.
     * @param cat index of category
     * @return int[] category values
     */
    public int[] getCatValues(int cat) {
        int[] catVals = new int[numOfPlayers];
        Card[] cards = topTrumpsRound.getRoundCards();
        for (int i = 0; i < numOfPlayers; i++) {
            if (cards[i] != null)
                catVals[i] = cards[i].getCatValueAt(cat);
            else
                catVals[i] = -1;
        }

        return catVals;
    }
}
