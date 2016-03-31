package guns;

import java.util.ArrayList;

import persons.Player;
import persons.Zombie;

public class Rifle extends Gun {
	
	public Rifle(ArrayList<Zombie> zombies, Player player) {
		super("Rifle", 105, 15, 1500, 500, 200);
		setPlayer(player);
		setZombies(zombies);
	}

	@Override
	public void modifyShot() {
		
		// TODO Auto-generated method stub
		
	}
	
	
}
