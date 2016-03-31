package guns;

import java.util.ArrayList;

import persons.Player;
import persons.Zombie;

public class Pistol extends Gun {
	
	public Pistol(ArrayList<Zombie> zombies, Player player) {
		super("Pistol", 64, 8, 1000, 100, 100);
		setPlayer(player);
		setZombies(zombies);
	}

	@Override
	public void modifyShot() {
		
		// TODO Auto-generated method stub
		
	}
	
}
