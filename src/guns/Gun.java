package guns;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javafx.scene.shape.Line;
import main.SpecialTimer;
import main.ZombiesTestRunner;
import persons.Figure;
import persons.Player;
import persons.Zombie;

public abstract class Gun implements ActionListener{
	
	public int bullets;
	public final int maxBullets;
	public int magCurrent;
	public int magSize;
	public int reloadTime;
	public int shotTime;
	public SpecialTimer firingTimer;
	public Shot shot = new Shot();
	
	public String name;
	public int damage;
	
	public Player player;
	public ArrayList<Zombie> zombies;
	public int score = 0;
	
	public Gun(String name, int bullets, int magSize, int reloadTime, int shotTime, int damage) {
		this.damage = damage;
		this.name = name;
		this.bullets = bullets;
		this.maxBullets = bullets;
		this.magCurrent = 0;
		this.magSize = magSize;
		this.reloadTime = reloadTime;
		this.shotTime = shotTime;
		firingTimer = new SpecialTimer(shotTime, this);
		firingTimer.fireTimerWhenStart(false);
		firingTimer.setRepeats(false);
		firingTimer.start();
	}
	
	public void setPlayer(Player p) {
		this.player = p;
	}
	
	public void setZombies(ArrayList<Zombie> zombies) {
		this.zombies = zombies;
	}
	
	
	public void reload() {
		
		if (bullets > 0) {
			if (magCurrent < magSize) {
				int reloadValue = magSize - magCurrent;
				if (bullets >= reloadValue) {
					bullets -= reloadValue;
					magCurrent += reloadValue;
				} else {
					magCurrent += bullets;
					bullets = 0;
				}
			}
		}
	}
	
	public ArrayList<Integer> shoot(Figure player) {
		
		if (magCurrent <= 0) {
			reloadEvent();
			return new ArrayList<Integer>();
		}
		
		int rise = ZombiesTestRunner.getMouseY() - player.y;
		int run = ZombiesTestRunner.getMouseX() - player.x;
		
		magCurrent--;
		while ((run + player.x > 0 && run + player.x < 800) && (rise + player.y > 0 && rise + player.y < 480)) {
			run *= 2;
			rise *= 2;
		}
		int x = player.x + run;
		int y = player.y + rise;
		shot = new Shot(player.x + 15, player.y + 15, x, y);
		
		modifyShot();
		
		ArrayList<Integer> shotZombies = new ArrayList<Integer>();
		for (int i = 0; i < zombies.size(); i++) {
			if (applyShot(zombies.get(i))) {
				shotZombies.add(i);
			}
		}
		return shotZombies;
	}
	
	public void resetShot() {
		shot.unshoot();
	}
	
	public boolean applyShot(Zombie zombie) {
		return checkShot(zombie, shot);
	}
	
	protected boolean checkShot(Zombie zombie, Shot shot) {
		if (zombie.ifShot(shot) && !shot.isShot()) {
			shot.shotHit();
			zombie.health -= damage;
			score++;
			return true;
		}
		return false;
	}
	public abstract void modifyShot();

	
	public void maxAmmo() {
		bullets = maxBullets;
		magCurrent = magSize;
	}
	
//	public void reloading(boolean rPressed) {
//		
//		ticks++;
//		
//		if (rPressed && magCurrent < magSize && bullets > 0) {
//			rPressed = false;
//			reloadStart = System.currentTimeMillis();
//			reloading = true;
//		} else if (currentGun.magCurrent == 0 && currentGun.bullets > 0 && !reloading) {
//			reloadStart = System.currentTimeMillis();
//			reloading = true;
//		}
//		
//		if (System.currentTimeMillis() - reloadStart > currentGun.reloadTime && reloading) {
//			reload();
//			reloading = false;
//		}
//		
//		
//	}
	
	public void shootEvent(Figure player) {
		if (!firingTimer.isRunning()) {
			firingTimer.setInitialDelay(getGunDelay());
			firingTimer.fireTimerWhenStart(true);
			firingTimer.setActionCommand("Shoot");
			resetShot();
			firingTimer.restart();
		}
	}
	
	public int getGunDelay() {
		return shotTime;
	};

	
	public void reloadEvent() {
		if (!firingTimer.isRunning() || firingTimer.getActionCommand().equals("Shoot")) {
			stopShooting();
			firingTimer.setInitialDelay(reloadTime);
			firingTimer.fireTimerWhenStart(true);
			firingTimer.setActionCommand("Reload");
			firingTimer.restart();
			
		}
	}
	
	public boolean isReloading() {
		return firingTimer.getActionCommand().equals("Reload") && firingTimer.isRunning();
	}
	
	public void stopShooting() {
		firingTimer.stop();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("Reload")) {
			reload();
		} else if (e.getActionCommand().equals("Shoot")) {
			shoot(player);
		}
	}
	
	public void applyDrop() {
		
	}
	
	
}
