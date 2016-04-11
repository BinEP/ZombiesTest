package guns;

import java.util.ArrayList;

import persons.Player;
import persons.Zombie;

public class Browning extends Automatic {

	public Browning(ArrayList<Zombie> zombies, Player player) {
		super(zombies, player, "Browning", 300, 100, 4000, 300, 150);
		setPlayer(player);
		setZombies(zombies);
	}
	
}
