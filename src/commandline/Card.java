package commandline;

/**
 * This class models a card in a game of Top Trumps.
 * It stores the description of a card and its categories'
 * values. This is an immutable class, hence there are only
 * methods to access its attributes.
 */
public class Card {
    private String description;
    /* Cards are assumed to have 5 categories */
    private final int NUM_OF_CATS = 5;
    private int[] catsValues;

    /**
     * Constructs a card object from a String array containing
     * the description and the values of the categories.
     * @param cardInfo Array containing card information
     */
    public Card(String[] cardInfo) {
        description = cardInfo[0];
        catsValues = new int[cardInfo.length - 1];

        for (int i = 0; i < NUM_OF_CATS; i++) {
            catsValues[i] = Integer.parseInt(cardInfo[i + 1]);
        }
    }

    /**
     * Gets the category value at position n.
     * @param pos category position
     * @return int category value
     */
    public int getCatValueAt(int pos) {
        return catsValues[pos];
    }

    /**
     * Gets the category position of the highest value.
     * @return int position of highest value category
     */
    public int getPosOfHighestCat() {
        int pos = 0;
        int maxValue = catsValues[0];

        for (int i = 1; i < NUM_OF_CATS; i++) {
            if (catsValues[i] > maxValue) {
                maxValue = catsValues[i];
                pos = i;
            }
        }

        return pos;
    }

    /**
     * Returns a string representation of a card object. That is -
     * its description followed by the category values, all of which
     * are delimited by whitespace.
     * @return String representation of a card object
     */
    public String toString() {
       StringBuilder sb = new StringBuilder();

        sb.append(description).append(" ");
        for (int i = 0; i < NUM_OF_CATS; i++) {
            sb.append(catsValues[i]).append(" ");
        }

        return sb.toString();
    }
}

