package guns;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import persons.Figure;
import persons.Player;
import persons.Zombie;

public class Shotgun extends Gun {
	
	public Shot spreadOneShot = new Shot();
	public Shot spreadTwoShot = new Shot();
	
	public Shotgun(ArrayList<Zombie> zombies, Player player) {
		super("Shotgun", 54, 9, 2000, 150, 100);
		setPlayer(player);
		setZombies(zombies);
		
	}
	
	@Override
	public void resetShot() {	
		spreadOneShot.unshoot();
		spreadTwoShot.unshoot();
		super.resetShot();
	}
	
	@Override
	public boolean applyShot(Zombie zombie) {
		
		boolean zombieShot = false;
		zombieShot = zombieShot || checkShot(zombie, shot);
		zombieShot = zombieShot || checkShot(zombie, spreadOneShot);
		zombieShot = zombieShot || checkShot(zombie, spreadTwoShot);

		return zombieShot;
	}

	@Override
	public void modifyShot() {
		
		if (magCurrent >= 0) {
			AffineTransform af = new AffineTransform();
			af.setToRotation(Math.PI / 20, player.x + 15, player.y + 15);
			spreadOneShot.setLine(new Point((int) player.x + 15, (int) player.y + 15), af.transform(shot.getP2(), null));
			
			af.setToRotation(-Math.PI / 20, player.x + 15, player.x + 15);
			spreadTwoShot.setLine(new Point((int) player.x + 15, (int) player.y + 15), af.transform(shot.getP2(), null));
		}		
	}
	
	@Override
	public ArrayList<Zombie> loopThroughZombies() {
			
		ArrayList<Zombie> shotZombies = new ArrayList<Zombie>();
		for (Zombie z : zombies) {
			if (applyShot(z)) {
				if (shot.isShot())
					shotZombies.add(z);
				if (spreadOneShot.isShot())
					shotZombies.add(z);
				if (spreadTwoShot.isShot())
					shotZombies.add(z);
				if (shot.isShot() && spreadOneShot.isShot() && spreadTwoShot.isShot())
					return shotZombies;
			}
		}
		return shotZombies;
	}
	
}
