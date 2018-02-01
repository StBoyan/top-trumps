package commandline.models;

import java.io.FileNotFoundException;               //TODO CHANGE POS TO INDEX
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class handles a single game of Top Trumps. It maintains
 * a list of the players in the game and the active player at
 * any moment. It has various methods to distribute cards (both     //TODO include in descr it also has methods to access info for db and log
 * at beginning and after a round), methods to access attributes
 * in Round, methods to access the human player's top cards and
 * others.
 */
public class Game {         //TODO METHODS TO ACCESS INFORMATION FOR THE DATABASE AND FOR THE LOG FROM ROUND
private Deck topTrumpsDeck;
private Round topTrumpsRound;
/* Array containing all players currently in
 * the game in their positions. The human player        //TODO MAYBE MOVE DEBUG LOGIC INTO GAMECONTROLLER AND CREATE METHODS IN GAME THAT HANDLE DEBUG
 * is at position 0 and the players never change
 * position. */
private Player[] players;
/* Position of active player in the game  */
private int activePlayer;
private int numOfPlayers;

    /**
     * Creates a game object. Deck, Round, and Player
     * objects are initialised. First active player is
     * chosen at random.                                                //TODO change descrp
     * @param numPlayers number of players
     * @throws FileNotFoundException if deck file is not found
     */
    public Game(int numPlayers) throws FileNotFoundException{
        numOfPlayers = numPlayers;
        topTrumpsDeck = new Deck();
        topTrumpsRound = new Round(numOfPlayers);

        players = new Player[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++) {
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
    public Card getHumanPlayerCard() {
        if (players[0] != null)                         //TODO change description
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
     * if round wasn't a draw. Returns position of winner
     * or -1 if round was a draw.
     * @param category position of category
     * @return int position of winner or -1 in case of draw
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
            if (c != null)          //TODO comment
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

        for (int i = 0; i < numOfPlayers; i++) {                      //TODO need new description
            if (players[i] != null)
                if (players[i].getPlayerDeck().size() == 0) {
                    players[i] = null;
                    isEliminatedPlayers[i] = true;
            }
        }

        return isEliminatedPlayers;
    }

    /**
     * TODO descr
     * @return
     */
    public ArrayList<Card> getCommunalPile(){
        return topTrumpsRound.getCommunalPile();
    }

    /**
     * TODO desc
     * @return
     */
    public int getDrawStat() {
        return topTrumpsRound.getDrawRounds();
    }

    /**
     * TODO
     * @return
     */
    public int[] getPlayerWonRoundStat() {
        return topTrumpsRound.getPlayersWonRounds();
    }

    /**
     * TODO
     * @return
     */
    public ArrayList<Card> getGameDeck() {
        return topTrumpsDeck.getGameDeck();
    }

    /**
     * //TODO
     * @param pos
     * @return
     */
    public ArrayList<Card> getPlayersDeckAt(int pos) {
        return players[pos].getPlayerDeck();
    }

    /**
     * TODO
     * @return
     */
    public Card[] getCardsInRound() {
        return topTrumpsRound.getRoundCards();
    }

    /**
     * TODO descr
     * @param cat
     * @return
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
