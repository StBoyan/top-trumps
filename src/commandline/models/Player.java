package commandline.models;

import java.util.ArrayList;

/**
 * This class models a player in a game of Top Trumps.
 * It contains an ArrayList with all the card objects
 * associated with the Player. It has methods to draw
 * and add cards, as well as a method to facilitate AI
 * player decisions (i.e. choose best category to play).
 */
public class Player {
    private ArrayList<Card> playerDeck;

    /**
     * Creates a Player object and initialises the
     * Card ArrayList.
     */
    public Player () {
        playerDeck = new ArrayList<Card>();
    }

    /**
    * Adds a card to the bottom of the player's deck.
    * @param c Card object
    */
    public void addCard(Card c) {
        playerDeck.add(c);
    }

    /**
     * Returns Card object from the top and removes it
     * from the player's deck.
     * @return Card card on top of deck
     */
    public Card drawCard() {
        Card c = playerDeck.get(0);
        playerDeck.remove(0);
        return c;
    }

    /**
     * Returns the first Card object of this Player. This
     * method should not be used for playing a round since
     * it does not remove the card from the player's deck.
     * @return Card card on top of deck
     */
    public Card getFirstCard() {
        return playerDeck.size() > 0 ? playerDeck.get(0) : null;
    }

    /**
     * Chooses the highest value category of the first
     * card in the player's deck.
     * @return int index of highest value category
     */
    public int getBestCategory() {
        return playerDeck.get(0).getPosOfHighestCat();
    }

    /**
     * Returns ArrayList containing the deck of the
     * player.
     * @return ArrayList<Card> player's deck>
     */
    public ArrayList<Card> getPlayerDeck() {
        return playerDeck;
    }

    public void setPlayerDeck(ArrayList<Card> playerDeck) {
        this.playerDeck = playerDeck;
    }
}
