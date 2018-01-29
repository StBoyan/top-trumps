package commandline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * This class models a round in a game of Top Trumps. It keeps track
 * of the cards in play, cards that caused a draw, communal pile,
 * current round number, round winner, and the result of each round.
 * It has methods to compare cards and decide the winner, to access
 * the class attributes, and to return all cards won in the round.
 */
public class Round {
    private Card[] roundCards;
    /* Array containing cards that end up in a draw
     * in a single round */
    private Card[] drawCards;
    private ArrayList<Card> communalPile;
    private int currentRound;
    private int roundWinner;
    /* Array containing rounds won by each player.
     * The positions correspond to the positions
     * of each player. */
    private int[] playerWonRounds;   //need theses for database
    /* Keeps track of how many draw rounds */           //TODO NEED methods / class attributes for log
    private int drawRounds;

    /**
     * Creates a Round object and initialises the
     * persistent class attributes.
     * @param numPlayers
     */
    public Round(int numPlayers) {
        communalPile = new ArrayList<Card>();
        drawCards = new Card[numPlayers];
        playerWonRounds = new int[numPlayers];
        drawRounds = 0;
        currentRound = 1;
    }

    /**
     * Compares the cards during a round returns the
     * position of the player who won the round or -1 if
     * the round ended in a draw.
     * @param c Card array with cards to compare
     * @param catPos position of category to compare by
     * @return int winner of round or -1 if draw
     */
    public int compareCards(Card[] c, int catPos) {
        roundCards = c;
        /* Set first player's card to be highest */
        int highestStat = roundCards[0].getCatValueAt(catPos);
        roundWinner = 0;
        boolean isDraw = false;

        /* Compare other cards in play this round to
         * the current highest */
        for (int i = 1; i < c.length; i++) {
            if (roundCards == null)         //TODO comment
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
             * player positions */
            else if (highestStat == stat) {
                drawCards[roundWinner] = roundCards[roundWinner];
                drawCards[i] = roundCards[i];
                isDraw = true;
            }
        }

        currentRound++;

        if (isDraw) {
            /* Add the round cards to the communal pile */
            Collections.addAll(communalPile, roundCards);       //TODO This needs testing
            drawRounds++;
            return -1;
        }
        else {
            Arrays.fill(drawCards, null);
            playerWonRounds[roundWinner]++;
            return roundWinner;
        }
    }

    /**
     * Returns the current round number.
     * @return int current round number
     */
    public int getCurrentRound() {
        return currentRound;
    }

    /**
     * Returns an array containing the cards
     * that caused a draw and removes all
     * cards from drawCards array.
     * @return Card[] cards that caused a draw
     */
    public Card[] getDrawCards() {
        Card[] cards = drawCards;
        Arrays.fill(drawCards, null);
        return cards;
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
        return roundCards[roundWinner];
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
     * Returns an ArrayList containing all the rounds played
     * in the current round and all the cards in the communal
     * pile (if there is one). Communal pile is also cleared.
     * @return ArrayList<Card> all cards won in this round
     */
    public ArrayList<Card> takeAllCards() {
        ArrayList<Card> allCards = new ArrayList<Card>();
        for (int i = 0; i < roundCards.length; i ++) {
            allCards.add(roundCards[i]);
        }
        if (communalPile.size() != 0) {
            allCards.addAll(communalPile);
            communalPile.clear();
        }

        return allCards;
    }

}
