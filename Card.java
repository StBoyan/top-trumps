package commandline;

/**
 * Class to model card
 */
public class Card {
    private String description;
    final int NUM_OF_STATS = 6;
   private int Size,Speed, Range,Firepower, Cargo;

    /**
     * constructor
     * @param cardInfo line of deck.txt
     */
    public Card(String d, int si, int sp, int ra, int fi, int ca) {
        description = d;
        Size =si;
        Speed = sp;
        Range = ra;
        Firepower = fi;
        Cargo = ca;
        
    }

   
    public String getDescription() {
        return description;
    }

  
    public int getSize() {
        return Size;
    }
    public int getSpeed() {
        return Speed;
    }

    
    public int getRange() {
        return Range;
    }
    public int getFirepower() {
        return Firepower;
    }
    public int getCargo() {
        return Cargo;
    }
}
