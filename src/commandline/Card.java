package commandline;

/**
 * Class to model card
 */
public class Card {
    String description;
    final int NUM_OF_STATS = 5;
    int[] stats;

    /**
     * constructor
     * @param cardInfo line of deck.txt
     */
    public Card(String[] cardInfo) {
        description = cardInfo[0];

        for (int i = 0; i < NUM_OF_STATS; i++) {
            stats[i] = Integer.parseInt(cardInfo[i]);
        }
    }

    /**
     * Gets card description.
     * @return String description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets all card stats.
     * @return int[] card stats
     */
    public int[] getStats() {
        return stats;
    }

    /**
     * Gets card stat at position n.
     * @param pos stat position
     * @return stat value
     */
    public int getStatAt(int pos) {
        return stats[pos];
    }
}

