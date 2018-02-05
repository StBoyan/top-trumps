package commandline.models;

/**
 * This class models a card in a game of Top Trumps.
 * It stores the description of a card and its categories'
 * values. This is an immutable class, hence there are only
 * methods to access its attributes.
 */
public class Card {
    private String description;
    private int[] catsValues;

    /**
     * Constructs a card object from a String array containing
     * the description and the values of the categories.
     * @param cardInfo Array containing card information
     */
    public Card(String[] cardInfo) {
        description = cardInfo[0];
        catsValues = new int[cardInfo.length - 1];

        for (int i = 0; i < cardInfo.length - 1; i++) {
            catsValues[i] = Integer.parseInt(cardInfo[i + 1]);
        }
    }

    /**
     * Gets the category value at index n.
     * @param pos category index
     * @return int category value
     */
    public int getCatValueAt(int pos) {
        return catsValues[pos];
    }

    /**
     * Gets the category index of the highest value.
     * @return int index of highest value category
     */
    public int getPosOfHighestCat() {
        int pos = 0;
        int maxValue = catsValues[0];

        for (int i = 1; i < catsValues.length; i++) {
            if (catsValues[i] > maxValue) {
                maxValue = catsValues[i];
                pos = i;
            }
        }

        return pos;
    }

    /**
     * Returns a string representation of a Card object. That is -
     * its description followed by the category values, all of which
     * are delimited by whitespace.
     * @return String representation of a card object
     */
    public String toString() {
       StringBuilder sb = new StringBuilder();

        sb.append(description).append(" ");

        for (int catValue : catsValues) {
            sb.append(catValue).append(" ");
        }

        return sb.toString();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int[] getCatsValues() {
        return catsValues;
    }

    public void setCatsValues(int[] catsValues) {
        this.catsValues = catsValues;
    }
}

