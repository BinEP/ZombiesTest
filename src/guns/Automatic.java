package guns;

import java.util.ArrayList;

import persons.Player;
import persons.Zombie;

public abstract class Automatic extends Gun {
	
	public Automatic(ArrayList<Zombie> zombies, Player player, String name, int bullets, int magSize, int reloadTime, int shotTime, int damage) {
		super(name, bullets, magSize, reloadTime, shotTime, damage);
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
