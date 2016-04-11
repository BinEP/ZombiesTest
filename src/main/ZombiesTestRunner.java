package main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import persons.Player;
import persons.Sprite;
import persons.Zombie;
import utility_classes.Windows;
import game_actions.Game;
import game_actions.Runner;
import guns.*;

public class ZombiesTestRunner extends Game {
	
	private static final long serialVersionUID = 1L;
	
	public int playerX = 400;
	public int playerY = 220;
	public int playerWidth = 30;
	public Player player = new Player(playerX - 15, playerY - 15, playerWidth, playerWidth, 200);
	
	Sprite playerSprite = new Sprite(1, "InfoFiles/player1.png", "InfoFiles/player2.png", "InfoFiles/player3.png", "InfoFiles/player2.png");
	
	
	public boolean touchingZombie = false;
	
//	public int ticks = 75;
//	public int maxTicks = 150;
//	public int scoreCount = 0;
//	public int spawnCount = 0;
//	public int roundCount = 1;
//	public int points = 0;
	
	public boolean rPressed = false;
	public boolean wPressed = false;
//	public boolean shooting = false;
	public static HashMap<String, Gun> guns = new HashMap<String, Gun>();
	public static HashMap<String, Gun> ownedGuns = new HashMap<String, Gun>();
	public Gun currentGun;
	
	
	public int[] screenX = {0, 800, 800, 0};
	public int[] screenY = {0, 0, 480, 480};
	public Polygon screen = new Polygon(screenX, screenY, 4);
	
	
	public double randomSpeed;
	public ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	public int zombieLives = 200;
		
	public SupplyDrop supplyDrop = new SupplyDrop();
	
	public static int getMouseX() {
		return MouseInfo.getPointerInfo().getLocation().x + 3;
	}
	
	public static int getMouseY() {
		return MouseInfo.getPointerInfo().getLocation().y - 20;
	}
	
	@Override
	public void moves() {
		// TODO Auto-generated method stub
		
		if (upPressed){
			deltaY = -movementVar;
		} else if (downPressed){
			deltaY = movementVar;
		} else{
			deltaY = 0;
		}
		
		player.y += deltaY;
		
		if (!screen.contains(player.getBounds())){
			player.y -= deltaY;
		}
		for(Zombie z : zombies){
			if(z.touchingPlayer(player.getBounds())){
				 player.y -= deltaY;
		}
	}
		if (rightPressed){
			deltaX = movementVar;
		} else if (leftPressed){
			deltaX = -movementVar;
		} else {
			deltaX = 0;
		}
		
		player.x += deltaX;
		
		if (!screen.contains(player.getBounds())){
			player.x -= deltaX;
		}
		for(Zombie z : zombies){
			if(z.touchingPlayer(player.getBounds())){
				 player.x -= deltaX;
		}
	}
		
		for (Zombie z : zombies) {
			if(z.moveX(player.getBounds(), zombies, player.health))
				player.health -= 2;
		}
		for(Zombie z : zombies){
			if(z.moveY(player.getBounds(), zombies, player.health))
				player.health -= 2;
		}
		//playerMove();
		//zombieMove();
		
		
//		reloading();
		
		Collections.sort(zombies, (z1, z2) -> (int) z1.getDistanceToPlayer(player.getBounds()) - (int) z2.getDistanceToPlayer(player.getBounds()));
		Scoring.gunUpdateScore(currentGun);
		currentGun.score = 0;
		
		playerSprite.setLocation((int) player.x, (int) player.y, (int) player.width, (int) player.height);
		playerSprite.setAngle(aimDotAngle());
		playerSprite.updateSprite();

		
		supplyDrop();

		spawnZombie();
		
		Scoring.scoreUpdate(player, supplyDrop);
		
		
	}

	public void playerMove() {
		//TODO Something is seriously messed up with movement, not sure what though.
		//But I don't like how it looks - seems like zombie and player movements are mixed
		//right now
		
		if (upPressed) {
			player.deltaY = -movementVar;
		} else if (downPressed) {
			player.deltaY = movementVar;
		} else {
			player.deltaY = 0;
		}
		
		
		
		if (rightPressed) {
			player.deltaX = movementVar;
		} else if (leftPressed) {
			player.deltaX = -movementVar;
		} else {
			player.deltaX = 0;
		}
		
		player.move(outerbox, zombies, player);
		
	}

	public void zombieMove() {
		
		//TODO Something is seriouosly messed up with movement, not sure what though.
		//But I don't like how it looks - seems like zombie and player movements are mixed
		//right now
		
		for (Zombie z : zombies) {
//			if (z.move(player, zombies, player.health))
//				player.health -= 2;
			z.move(outerbox, zombies, player);
		}
	}

	public void supplyDrop() {
		
		if (supplyDrop.displaySupplyDrop && supplyDrop.intersects(player.getBounds2D())) {
			Scoring.scoreToZero();
			switchCurrentGun(supplyDrop.applyDrop(currentGun).name);
			
			System.out.println(currentGun.name);
		}
	}

	public void spawnZombie() {
		
		if (Scoring.readyToSpawnZombie()) {
			System.out.println("Spawn Zombie");
			randomSpeed = (3 + (int) (Math.random() * 3)) * 0.25;
			int zombieY = ((int) (Math.random() * 2) == 1) ? -20 : 500;
			
			zombies.add(new Zombie((int) (Math.random() * 700) + 20, zombieY, 20, randomSpeed, zombieLives));
			zombieLives = Scoring.spawnCountAdjustments(zombieLives);
		}
	}
	
	public void switchCurrentGun(String name) {
		currentGun = guns.get(name);
		ownedGuns.put(name, currentGun);
	}
	
	public double aimDotAngle() {
		int centerX = (int) player.x + 15;
		int centerY = (int) player.y + 15;
		int sideX = getMouseX() - centerX;
		int sideY = getMouseY() - centerY;
		double distance = Math.sqrt(sideX * sideX + sideY * sideY);
		int a = (int) ((sideX * Math.sqrt(900)) / distance);
		int b = (int) ((sideY * Math.sqrt(900)) / distance);
		System.out.println("x: " + a);
		System.out.println("y: " + b);

		return Math.atan2(b, a);
	}
	
	@Override
	public boolean checkIfDead() {
		// TODO Auto-generated method stub
		
		return player.health <= 0;
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void drawPlaying(Graphics2D g) {
		
		// TODO Auto-generated method stub
		
		supplyDropDraw(g);
		
		drawPlayer(g);
		
		drawZombies(g);
		
		drawAmmo(g);
		
		drawHealth(g);
		
		
		drawAimDot(g);
		
//		if(currentGun.firstShot){
//			g.setColor(Color.ORANGE);
//			g.fill(currentGun.shot);
//		}
		
		g.setColor(Color.ORANGE);
		g.drawLine((int)(player.x + player.width/2), (int)(player.y + player.width/2), getMouseX(), getMouseY());
		
		
	}

	public void drawZombies(Graphics2D g) {
		
		for (Zombie z : zombies) {
			g.setColor(z.color);
			g.fill(z);
		}
	}

	public void drawPlayer(Graphics2D g) {
		
		g.setColor(Color.RED);
//		g.fillOval((int) player.x, (int) player.y, (int) player.width, (int) player.width);
		playerSprite.drawSprite(g);

	}

	public void supplyDropDraw(Graphics2D g) {
		
		supplyDrop.draw(g);
	}

	public void drawAmmo(Graphics2D g) {
		
		g.setFont(new Font("Century Gothic", Font.PLAIN, 64));
		g.setColor(Color.orange);
		g.drawString((currentGun.isReloading()) ? "-" : "" + currentGun.magCurrent, 720, 460);
		
		
		g.setFont(new Font("Century Gothic", Font.PLAIN, 28));
		g.drawString(String.valueOf(currentGun.bullets), 760, 460);
		Scoring.draw(g);		
		g.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		g.drawString(currentGun.name, 720, 475);
	}

	public void drawHealth(Graphics2D g) {
		
		g.setColor(Color.RED);
		g.fillRect(400 - player.maxHealth / 2, 20, 200, 20);
		g.setColor(Color.GREEN);
		g.fillRect(400 - player.maxHealth / 2, 20, player.health, 20);
	}

	public void drawAimDot(Graphics2D g) {
		
		g.setColor(Color.CYAN);
		int centerX = (int) player.x + 15;
		int centerY = (int) player.y + 15;
		int sideX = getMouseX() - centerX;
		int sideY = getMouseY() - centerY;
		double distance = Math.sqrt(sideX * sideX + sideY * sideY);
		int a = centerX + (int) ((sideX * Math.sqrt(900)) / distance);
		int b = centerY + (int) ((sideY * Math.sqrt(900)) / distance);
		g.fillOval(a - 2, b - 2, 4, 4);
	}
	
	@Override
	public void setup() {
		// TODO Auto-generated method stub
		
		guns.put("Pistol", new Pistol(zombies, player));
		guns.put("AK-47", new AK47(zombies, player));
		guns.put("Rifle", new Rifle(zombies, player));
		guns.put("Shotgun", new Shotgun(zombies, player));
		guns.put("Sniper", new Sniper(zombies, player));
		guns.put("Browning", new Browning(zombies, player));
		
		switchCurrentGun("Sniper");
		switchCurrentGun("Pistol");
		
		System.out.println(currentGun.shotTime);
		System.out.println(currentGun.firingTimer.getInitialDelay());

		upKey = KeyEvent.VK_W;
		downKey = KeyEvent.VK_S;
		leftKey = KeyEvent.VK_A;
		rightKey = KeyEvent.VK_D;
		
		playerSprite.setTiming(10, 10, 10, 10);
		playerSprite.setLocation((int) player.x, (int) player.y, (int) player.width, (int) player.height);
		playerSprite.setPolygonOffset(0, 0, 10);
		playerSprite.setPolygonOffset(2, 0, -10);

		movementVar = 3;
		Windows.setSCORE_SIZE(30);
		currentGun.reload();
		
	}
	
	@Override
	public String getGameName() {
		
		// TODO Auto-generated method stub
		return "Zombies";
	}
	
	@Override
	public void pressed(MouseEvent m) {
		
		if (m.getButton() == MouseEvent.BUTTON1) {
//			if (System.currentTimeMillis() - lastShot > currentGun.shotTime && !reloading
//					&& currentGun.magCurrent > 0) {
//				currentGun.shoot(player, cursorX, cursorY);
//				lastShot = System.currentTimeMillis();
//				autoReady = true;
//			}
			currentGun.shootEvent(player);
		}
	}
	
	@Override
	public void released(MouseEvent m) {
		
		if (m.getButton() == MouseEvent.BUTTON1) {
//			currentGun.stopShooting();
		}
	}
	
	@Override
	public void customPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_R) {
			rPressed = true;
			currentGun.reloadEvent();
		} else if (e.getKeyCode() == KeyEvent.VK_T) {
//			rPressed = true;
			ArrayList<Gun> prevOwnGuns = new ArrayList<Gun>();
			prevOwnGuns.addAll(ownedGuns.values());
			int currentIndex = prevOwnGuns.indexOf(currentGun);
			currentIndex++;
			if (currentIndex >= prevOwnGuns.size()) currentIndex = 0;
			currentGun = prevOwnGuns.get(currentIndex);
			if (currentGun.magCurrent <= 0)
				currentGun.reloadEvent();
		}
	}
	
	@Override
	public void customReleased(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_R) {
			rPressed = false;
		}
	}
	
	public static void main(String[] args) {
		
		new Runner(new ZombiesTestRunner());
	}
}