package commandline;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Deck {
	
	private ArrayList<Card> deck;
	private final int DECK_SIZE = 50;
	private final String DeckFile = "StarCitizenDeck.txt";
	private Scanner readDeck;
	public Deck()
    {
        deck = new ArrayList<>();
        
        
			 try {
				readDeck = new Scanner(new File (DeckFile));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
        for(int i =0; i<DECK_SIZE; i++)
        {	
        	if (readDeck.hasNext()) {
        	String description = readDeck.next();
			int size  = Integer.parseInt(readDeck.next());
			int speed = Integer.parseInt(readDeck.next());
			int range = Integer.parseInt(readDeck.next());
			int firepower = Integer.parseInt(readDeck.next());
			int cargo = Integer.parseInt(readDeck.next());
            deck.add(new Card(description,size,speed,range,firepower,cargo));
        	}
            
        }  
        readDeck.close();
        
 
    
    }
	public ArrayList<Card> deck() {
		
		return deck;
	}
	
	public void removeOneCard () {
		deck.remove(0);
	}
	
	public void Shuffle() {
		Collections.shuffle(deck); 
	}
	public static void main(String[] args) {
		
		
	}		
}