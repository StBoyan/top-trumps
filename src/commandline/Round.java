package commandline;

import java.util.ArrayList;

/**
 * Class to handle a round
 */
public class Round {
    final int NUM_OF_PLAYERS;
    int currentRound;
    int[] playerWonRounds;
    int drawRounds;
    private int statChosen;
    Card[] cardsInPlay;
    Card[] drawCards;
    ArrayList<Card> communalPile;


    /**
     * Constructor
     * @param players number of players
     */
    public Round(int players) {
        NUM_OF_PLAYERS = players;
        currentRound = 1;
        playerWonRounds = new int[players];
        drawCards = new Card[NUM_OF_PLAYERS];
        drawRounds = 0;
    }

    /**
     * Compares cards during the rond.
     * @param c Card array with cards to compare
     * @param statPos position of stat to compare by
     * @return int with winner of round; -1 if draw
     */
    public int compareCards(Card[] c, int statPos) {
        System.err.println("Game compareCards()" + statPos);
        statChosen = statPos;
        cardsInPlay = c;
        boolean isDraw = false;
        int highestStat = cardsInPlay[0].getStatAt(statChosen);
        int playerWithHighestStat = 0;

        for (int i = 1; i < NUM_OF_PLAYERS; i++) {
            int stat = cardsInPlay[i].getStatAt(statChosen);

            if(highestStat < stat) {
                highestStat = stat;
                playerWithHighestStat = i;
                isDraw = false;
            }
            else if (highestStat == stat) {
                drawCards[playerWithHighestStat] = cardsInPlay[playerWithHighestStat];
                drawCards[i] = cardsInPlay[i];
                isDraw = true;
            }
        }

        if (isDraw) {
            drawRounds++;
            return -1;
        }
        else {
            for (int i = 0; i < NUM_OF_PLAYERS; i++) {
                drawCards[i] = null;
            }

            playerWonRounds[playerWithHighestStat]++;

            return playerWithHighestStat;
        }
    }

    /**
     * Gets the current round number.
     * @return int current round number
     */
    public int getCurrentRound() {
        return currentRound;
    }

    public int getStatChosen() {
        return statChosen;
    }

}
