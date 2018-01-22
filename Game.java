package commandline;

import javax.swing.JOptionPane;
import static java.lang.Math.floorDiv;

import java.util.ArrayList;

public class Game {
	
	private Deck gamedeck = new Deck();
	
	
	private int NumOfPlayers ;
	private int initialNum = 40;
	
	public Game() {
   
	}
		
		public void shareDeck() {
			
			gamedeck.Shuffle();
			initialNum = 40;   
			ArrayList<Player> playerList = new ArrayList<>();
			ArrayList<Card> arlTemp = null;
			
	        int numOfCards = floorDiv(initialNum, NumOfPlayers);
	        
	        for (int i = 0; i < NumOfPlayers; i++) {
	        	playerList.add(new Player(i));
	        	arlTemp = new ArrayList<>();
	        	
	            int start = i * numOfCards;
	            int end = (i * numOfCards + numOfCards >= initialNum) ? initialNum : 
	             (i * numOfCards + 2 * numOfCards <= initialNum) ? i * numOfCards + numOfCards : initialNum;

	          //  System.out.println("the starting point is: " + start + "\nthe end poind is: " + end);
	            for (int j = start; j< end; j++ ) {
	            	
	            	arlTemp.add(gamedeck.deck().get(j));
	            }
	            playerList.get(i).setHand(arlTemp);
	        }
	      
	        int i = 0;
	        for (Player a : playerList ) {
	        	int j = 0;
	        	for (Card b : a.hand()) {
	        		System.out.println(b.getDescription()   + "   " + b.getSize());
	        		//or //
	        		//System.out.println(playerList.get(i).hand().get(j).getDescription()+ "   " + playerList.get(i).hand().get(j).getSize());
	        		j++;
	        	}
	        	System.out.println();
	        	i++;
	        }
	       
	        System.exit(0);
	        

				}

		public static void main(String[] args) {
			String num = JOptionPane.showInputDialog(null, "How many players (2-5)");
			int num2 = Integer.parseInt(num);
			
			Game x = new Game();
			x.NumOfPlayers = num2;
			x.shareDeck();
		
		}
	
}

