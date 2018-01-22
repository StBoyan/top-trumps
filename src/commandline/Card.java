package commandline;

/**
 * Class to model card. Class objects are immutable.
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
        stats = new int[NUM_OF_STATS];

        for (int i = 0; i < NUM_OF_STATS; i++) {
            stats[i] = Integer.parseInt(cardInfo[i + 1]);
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
     * @return int stat value
     */
    public int getStatAt(int pos) {
        return stats[pos];
    }

    /**
     * Gets position of highest card stat.
     * @return int index of highest stat
     */
    public int getPosOfHighestStat() {
        int pos = 0;
        int highestStat = stats[0];

        for (int i = 1; i < NUM_OF_STATS; i++) {
            if (stats[i] > highestStat) {
                highestStat = stats[i];
                pos = i;
            }
        }

        return pos;
    }

    /**
     * Returns string representation of card.
     * @return String card description and values
     */
    public String toString() {
       StringBuilder sb = new StringBuilder();

        sb.append(description).append(" ");
        for (int i = 0; i < NUM_OF_STATS; i++) {
            sb.append(stats[i]).append(" ");
        }

        return sb.toString();
    }
}

