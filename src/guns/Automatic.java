package guns;

import java.awt.event.MouseEvent;
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
		this.firingTimer.setDelay(shotTime);
		// TODO Auto-generated method stub
		return super.getGunDelay();
	}

	@Override
	public void modifyShot() {
		
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void reload() {
		firingTimer.setRepeats(false);
		// TODO Auto-generated method stub
		super.reload();
	}
	
	@Override
	public abstract void onMouseRelease(MouseEvent m);
}
