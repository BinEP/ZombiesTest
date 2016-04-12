package guns;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
	public SpecialTimer firingTimer = new SpecialTimer(100, this);
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
//		firingTimer = new SpecialTimer(shotTime, this);
		firingTimer.setInitialDelay(shotTime);
		firingTimer.fireTimerWhenStart(true);
		firingTimer.setRepeats(false);
//		firingTimer.start();
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
	
	public ArrayList<Zombie> shoot(Figure player) {
		
		int rise = ZombiesTestRunner.getMouseY() - (int) player.y - (int) player.width;
		int run = ZombiesTestRunner.getMouseX() - (int) player.x - (int) player.width;
		
		magCurrent--;
		while ((run + player.x > 0 && run + player.x < 800) && (rise + player.y > 0 && rise + player.y < 480)) {
			run *= 2;
			rise *= 2;
		}
		int x = (int) player.x + run;
		int y = (int) player.y + rise;
		shot = new Shot(player.x + player.width, player.y + player.width, x, y);
		
		modifyShot();
		
		ArrayList<Zombie> shotZombies = loopThroughZombies();
		
		updateZombieHealth(shotZombies);
		
		if (magCurrent <= 0) {
			reloadEvent();
//			return new ArrayList<Integer>();
		}
		
		return shotZombies;
	}

	public void updateZombieHealth(ArrayList<Zombie> shotZombies) {
		
		for (Zombie z : shotZombies) {
			if (z.health <= 0) {
				zombies.remove(z);
				score++;
			} else {
				z.setColor();
			}
		}
		
	}

	public ArrayList<Zombie> loopThroughZombies() {
		
		ArrayList<Zombie> shotZombies = new ArrayList<Zombie>();
		for (Zombie z : zombies) {
			if (applyShot(z)) {
				shotZombies.add(z);
				return shotZombies;
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
		System.out.println("Firing: " + firingTimer.isRunning());
		if (!firingTimer.isRunning()) {
			firingTimer.stop();
			firingTimer.setActionCommand("Shoot");
			firingTimer.setInitialDelay(getGunDelay());
			resetShot();
			firingTimer.start();
		}
	}
	
	public int getGunDelay() {
		return shotTime;
	};

	
	public void reloadEvent() {
		if (!firingTimer.isRunning() || firingTimer.getActionCommand().equals("Shoot")) {
			stopShooting();
			firingTimer.setInitialDelay(reloadTime);
			firingTimer.setActionCommand("Reload");
			firingTimer.start();
			
		}
	}
	
	public boolean isReloading() {
		if (firingTimer.getActionCommand() == null) return false;
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
	
	public void onMouseRelease(MouseEvent m) {
		
	}
	
	
}
