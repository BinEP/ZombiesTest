package guns;

import java.util.ArrayList;

import persons.Player;
import persons.Zombie;

public class Automatic extends Gun {
	
	public Automatic(ArrayList<Zombie> zombies, Player player) {
		super("AK-47", 160, 40, 1500, 200, 100);
		setPlayer(player);
		setZombies(zombies);
	}
	
	@Override
	public int getGunDelay() {
		
		this.firingTimer.setRepeats(true);
		// TODO Auto-generated method stub
		return super.getGunDelay();
	}

	@Override
	public void modifyShot() {
		
		// TODO Auto-generated method stub
		
	}
}
