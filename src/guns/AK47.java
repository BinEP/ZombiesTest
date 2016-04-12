package guns;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import persons.Player;
import persons.Zombie;

public class AK47 extends Automatic {
	
	public AK47(ArrayList<Zombie> zombies, Player player) {
		super(zombies, player, "AK-47", 160, 40, 1500, 200, 100);
		setPlayer(player);
		setZombies(zombies);
	}

	@Override
	public void onMouseRelease(MouseEvent m) {
		
		stopShooting();		
	}

}
