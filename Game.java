package commandline;

import javax.swing.JOptionPane;

public class Game {
	
	private Deck gamedeck = new Deck();
	private Player human = new Player(0);
	private Player ai1 = new Player (1);
	private Player ai2 = new Player (2);
	private Player ai3 = new Player (3);
	private Player ai4 = new Player (4);
	
	private int NumOfPlayers ;
	private int InitialDeckSize= 40;
	
	public Game() {
		
		
		   
		   
	}
		
		public void shareDeck() {
			gamedeck.Shuffle();
			
			for (int i = 0; i <  InitialDeckSize/NumOfPlayers; i++) {
			    
			        Card removed = gamedeck.deck().remove(0);
			        human.hand().add(0, removed);
			        
			        Card removed2 = gamedeck.deck().remove(0);
			        ai1.hand().add(0, removed2);
			        
			        if (NumOfPlayers == 3) {
			        	Card removed3 = gamedeck.deck().remove(0);
				        ai2.hand().add(0, removed3);
			        	
			        }
			        if (NumOfPlayers == 4) {
			        	Card removed3 = gamedeck.deck().remove(0);
				        ai2.hand().add(0, removed3);
			        	Card removed4 = gamedeck.deck().remove(0);
				        ai3.hand().add(0, removed4);
			        	
			        }
			        if (NumOfPlayers == 5) {
			        	Card removed3 = gamedeck.deck().remove(0);
				        ai2.hand().add(0, removed3);
			        	Card removed4 = gamedeck.deck().remove(0);
				        ai3.hand().add(0, removed4);
			        	Card removed5 = gamedeck.deck().remove(0);
				        ai4.hand().add(0, removed5);
			        	
			        }
			        
			        
			        
			    
		}
			
			
	}
		public static void main(String[] args) {
			String num = JOptionPane.showInputDialog(null, "How many players (2-5)");
			int num2 = Integer.parseInt(num);
			
			Game x = new Game();
			x.NumOfPlayers = num2;
			x.shareDeck();
		for (int i = 0; i < x.InitialDeckSize/x.NumOfPlayers; i++) {
			
			System.out.println(x.human.hand().get(i).getDescription() + "   " + x.human.hand().get(i).getSize());
			
			
			}
			System.out.println();
			for (int i = 0; i < x.InitialDeckSize/x.NumOfPlayers; i++) {
			System.out.println(x.ai1.hand().get(i).getDescription() + "   " + x.ai1.hand().get(i).getSize());
			}
			System.out.println();
			if (x.NumOfPlayers == 3 || x.NumOfPlayers == 4 || x.NumOfPlayers == 5  ) {
			for (int i = 0; i < x.InitialDeckSize/x.NumOfPlayers; i++) {
			System.out.println(x.ai2.hand().get(i).getDescription() + "   " + x.ai2.hand().get(i).getSize());
			}
			}
			System.out.println();
			if (x.NumOfPlayers == 4 || x.NumOfPlayers == 5  ) {
			for (int i = 0; i < x.InitialDeckSize/x.NumOfPlayers; i++) {
			System.out.println(x.ai3.hand().get(i).getDescription() + "   " + x.ai3.hand().get(i).getSize());
			}
			}
			if (x.NumOfPlayers == 5  ) {
			System.out.println();
			for (int i = 0; i < x.InitialDeckSize/x.NumOfPlayers; i++) {
			System.out.println(x.ai4.hand().get(i).getDescription() + "   " + x.ai4.hand().get(i).getSize());
			}
			}
		}
			
		
		
}

