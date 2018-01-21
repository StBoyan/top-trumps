package commandline;

import java.util.ArrayList;

public class Player {
	private int ID;
	private ArrayList<Card> hand;
	
	public Player (int id) {
		ID=id;
		hand = new ArrayList<>();
		
	}
	public void Showplaycard () {
		hand.get(0).getDescription();
		hand.get(0).getSize();
		hand.get(0).getSpeed();
		hand.get(0).getRange();
		hand.get(0).getFirepower();
		
	}
	public void removeOneCard () {
		hand.remove(0);
	}
	public int getPlayer () {
		return ID;
	}

public ArrayList<Card> hand() {
		
		return hand;
	}

}