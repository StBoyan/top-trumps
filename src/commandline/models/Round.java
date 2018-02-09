package commandline.models;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class models a round in a game of Top Trumps. It keeps track
 * of the cards in play, communal pile, current round number,
 * round winner, and the result of each round. It has methods to
 * compare cards and decide the winner, to access the class attributes,
 * and to return all cards won in the round.
 */
public class Round {
    private Card[] roundCards;
    private ArrayList<Card> communalPile;
    private int roundsPlayed;
    private int roundWinner;
    /* Array containing rounds won by each player.
     * The indices correspond to the indices
     * of each player. */
    private int[] playersWonRounds;
    /* Keeps track of how many draw rounds */
    private int drawRounds;

    /**
     * Creates a Round object and initialises the
     * persistent class attributes.
     * @param numPlayers number of players
     */
    public Round(int numPlayers) {
        communalPile = new ArrayList<Card>();
        playersWonRounds = new int[numPlayers];
        drawRounds = 0;
        roundsPlayed = 0;
    }

    /**
     * Compares the cards during a round and returns the
     * index of the player who won the round or -1 if
     * the round ended in a draw.
     * @param c Card array with cards to compare
     * @param catPos index of category to compare by
     * @return int index of winner of round or -1 if draw
     */
    public int compareCards(Card[] c, int catPos) {
        /* Array containing cards that end up in a draw
         * in a single round */
        Card[] drawCards = new Card[c.length];
        roundCards = c;
        /* Set highest stat to -1 (lower than minimum
         * value of a category) to allow for comparison
          * in the following loop*/
        int highestStat = -1;
        roundWinner = 0;
        boolean isDraw = false;

        /* Compare cards in play this round to
         * the current highest */
        for (int i = 0; i < c.length; i++) {
            /* If card is null (i.e. player in that index
             * has been eliminated - skip this iteration */
            if (roundCards[i] == null)
                continue;

            int stat = roundCards[i].getCatValueAt(catPos);

            /* If card is higher thar current highest, set it
             *  to be the highest and erase draw cards array */
            if (highestStat < stat) {
                highestStat = stat;
                roundWinner = i;
                isDraw = false;
                Arrays.fill(drawCards, null);
            }
            /* If card is equal to current highest, put both
             * cards in drawCards array in their matching
             * player indices */
            else if (highestStat == stat) {
                drawCards[roundWinner] = roundCards[roundWinner];
                drawCards[i] = roundCards[i];
                isDraw = true;
            }
        }

        roundsPlayed++;

        if (isDraw) {
            /* Add the round cards to the communal pile */
            for (Card card : roundCards) {
                if (card != null)
                    communalPile.add(card);
            }
            drawRounds++;
            return -1;
        }
        else {
            playersWonRounds[roundWinner]++;
            return roundWinner;
        }
    }

    /**
     * Returns the number of rounds played
     * @return int number of rounds played
     */
    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    /**
     * Returns an Array with the cards in play.
     * @return Card[] cards in play
     */
    public Card[] getRoundCards() {
        return roundCards;
    }

    /**
     * Returns the Card object that had the
     * highest category value in the round.
     * @return Card winning card
     */
    public Card getWinningCard() {
        if (roundCards != null && roundCards.length > 0) {
            return roundCards[roundWinner];
        }
        return null;
    }

    /**
     * Returns the ArrayList containing the communal
     * pile.
     * @return ArrayList communal pile Cards
     */
    public ArrayList<Card> getCommunalPile() {
        return communalPile;
    }

    /**
     * Returns an ArrayList containing all the cards played
     * in the current round and all the cards in the communal
     * pile (if there is one). Communal pile is also cleared.
     * @return ArrayList<Card> all cards won in this round
     */
    public ArrayList<Card> takeAllCards() {
        ArrayList<Card> allCards = new ArrayList<Card>();
        for (Card card : roundCards) {
            allCards.add(card);
        }
        if (communalPile.size() != 0) {
            allCards.addAll(communalPile);
            communalPile.clear();
        }

        return allCards;
    }

    /**
     * Returns array of integers containing the number
     * of rounds won by each player.
     * @return int[] rounds won by each player
     */
    public int[] getPlayersWonRounds() {
        return playersWonRounds;
    }

    /**
     * Returns the number rounds that ended in a draw.
     * @return int number of draws
     */
    public int getDrawRounds() {
        return drawRounds;
    }
}
