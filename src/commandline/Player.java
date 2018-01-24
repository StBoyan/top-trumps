package commandline;

import java.util.ArrayList;

/**
 * Class to keep track of players
 */
public class Player {
    private ArrayList<Card> playerDeck;

    /**
     * Constructor to initialise playerDeck arraylist
     */
    public Player () {
        playerDeck = new ArrayList<Card>();
    }

    /**
    * Adds a card to the bottom of the player's deck
    * @param c Card object
    */
    public void addCard(Card c) {
        playerDeck.add(c);
    }

    /**
     * Returns Card object from the top and removes it
     * from the player's deck
     * @return Card object
     */
    public Card drawCard() {
        Card c = playerDeck.get(0);
        playerDeck.remove(0);
        return c;
    }

    /**
     * Chooses the highest value category from the top of the
     * deck.
     * @return
     */
    public int aiChooseCategory() {
        return playerDeck.get(0).getPosOfHighestStat();
    }

    /**
     * Returns player's deck
     * @return ArrayList<Card> player's deck>
     */
    public ArrayList<Card> getPlayerDeck() {
        return playerDeck;
    }


}
