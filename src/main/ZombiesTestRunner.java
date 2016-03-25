package main;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.shape.Line;
import persons.Player;
import persons.Zombie;
import utility_classes.Windows;
import game_actions.Game;
import game_actions.Runner;
import guns.*;

public class ZombiesTestRunner extends Game {
	
	public int playerX = 400;
	public int playerY = 220;
	public int playerWidth = 30;
	public Player player = new Player(playerX - 15, playerY - 15, playerWidth, playerWidth, 200);
	public boolean touchingZombie = false;
	
	public int[] screenX = { 0, 800, 800, 0 };
	public int[] screenY = { 0, 0, 480, 480 };
	public Polygon screen = new Polygon(screenX, screenY, 4);
	public int ticks = 75;
	public int maxTicks = 150;
	public int scoreCount = 0;
	public int spawnCount = 0;
	public int roundCount = 1;
	public int points = 0;
	
	public boolean rPressed = false;
	public boolean wPressed = false;
	public boolean shooting = false;
	public static HashMap<String, Gun> guns = new HashMap<String, Gun>();
	public Gun currentGun;
	
	public double randomSpeed;
	public ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	public int zombieLives = 100;
		
	public SupplyDrop supplyDrop = new SupplyDrop();
	
	public static int getMouseX() {
		return MouseInfo.getPointerInfo().getLocation().x - 8;
	}
	
	public static int getMouseY() {
		return MouseInfo.getPointerInfo().getLocation().y - 31;
	}
	
	@Override
	public void moves() {
		// TODO Auto-generated method stub
		
		playerMove();
		zombieMove();
		
		
//		reloading();
		
		supplyDrop();
		
		horrorOfShooting();
		
		spawnZombie();
		
		scoringStuff();
		
		
	}

	public void playerMove() {
		//TODO Something is seriouosly messed up with movement, not sure what though.
		//But I don't like how it looks - seems like zombie and player movements are mixed
		//right now
		
		if (upPressed) {
			deltaY = -movementVar;
		} else if (downPressed) {
			deltaY = movementVar;
		} else {
			deltaY = 0;
		}
		
		player.y += deltaY;
		
		if (!screen.contains(player.getBounds2D())) {
			player.y -= deltaY;
		}
		
		if (rightPressed) {
			deltaX = movementVar;
		} else if (leftPressed) {
			deltaX = -movementVar;
		} else {
			deltaX = 0;
		}
		
		player.x += deltaX;
		
		if (!screen.contains(player.getBounds2D())) {
			player.x -= deltaX;
		}
		
		for (Zombie z : zombies) {
			if (z.touchingFigure(player)) {
				player.x -= deltaX;
				player.y -= deltaY;

			}
		}
	}

	public void zombieMove() {
		
		//TODO Something is seriouosly messed up with movement, not sure what though.
		//But I don't like how it looks - seems like zombie and player movements are mixed
		//right now
		
		for (Zombie z : zombies) {
			if (z.move(player, zombies, player.health))
				player.health -= 2;
		}
	}

	public void supplyDrop() {
		
		if (supplyDrop.intersects(player.getBounds2D())) {
			score = 0;
			supplyDrop.applyDrop(currentGun);
		}
	}

	public void horrorOfShooting() {
		
		if (shooting) {
			
			if (!currentGun.isAuto || autoReady) {
				autoReady = false;
				for (Zombie z : zombies) {
					z.shot(currentGun.shot, "Normal");
					if (currentGun.name == "Shotgun") {
						z.shot(currentGun.spreadOneLine, "Spread One");
						z.shot(currentGun.spreadTwoLine, "Spread Two");
					}
				}
				double[] minDistance = { 2000, -1 }; // Minimum distance from
														// zombie to player and
														// index of zombie
				int i = 0;
				if (currentGun.name == "Sniper") {
					for (Zombie z : zombies) {
						if (z.isShot) {
							score++;
							if ((z.health -= currentGun.damage) < 0) {
								zombies.remove(i);
								score++;
							} else
								zombies.get(i).setColor();
						}
						i++;
					}
				} else {
					i = 0;
					
					for (Zombie z : zombies) {
						if (z.isShot) {
							double temp = z.getDistanceToPlayer(player);
							if (temp < minDistance[0]) {
								minDistance[0] = temp;
								minDistance[1] = i;
							}
							z.isShot = false;
						}
						i++;
					}
					if (minDistance[0] != 2000) {
						// zombies.get((int)minDistance[1]).isShot = true;
						score++;
						if ((zombies.get((int) minDistance[1]).health -= currentGun.damage) < 0) {
							zombies.remove((int) minDistance[1]);
							score++;
						} else
							zombies.get((int) minDistance[1]).setColor();
					}
					
					if (currentGun.name == "Shotgun") {
						double[] minDistance1 = { 2000, -1 }; // Minimum
																// distance from
																// zombie to
																// player and
																// index of
																// zombie
						int j = 0;
						for (Zombie z : zombies) {
							if (z.spreadOneHit) {
								double temp = z.getDistanceToPlayer(player);
								if (temp < minDistance1[0]) {
									minDistance1[0] = temp;
									minDistance1[1] = j;
								}
								z.spreadOneHit = false;
							}
							j++;
						}
						if (minDistance1[0] != 2000) {
							// zombies.get((int)minDistance[1]).isShot = true;
							score++;
							if ((zombies.get((int) minDistance1[1]).health -= currentGun.damage) < 0) {
								zombies.remove((int) minDistance1[1]);
								score++;
							} else
								zombies.get((int) minDistance1[1]).setColor();
						}
						double[] minDistance2 = { 2000, -1 }; // Minimum
																// distance from
																// zombie to
																// player and
																// index of
																// zombie
						int k = 0;
						for (Zombie z : zombies) {
							if (z.spreadOneHit) {
								double temp = z.getDistanceToPlayer(player);
								if (temp < minDistance2[0]) {
									minDistance2[0] = temp;
									minDistance2[1] = k;
								}
								z.spreadTwoHit = false;
							}
							k++;
						}
						if (minDistance2[0] != 2000) {
							// zombies.get((int)minDistance[1]).isShot = true;
							score++;
							if ((zombies.get((int) minDistance2[1]).health -= currentGun.damage) < 0) {
								zombies.remove((int) minDistance2[1]);
								score++;
							} else
								zombies.get((int) minDistance2[1]).setColor();
						}
					}
				}
				
			}
			if (currentGun.name == "AK-47") {
				if (currentGun.magCurrent > 0 && System.currentTimeMillis() - lastShot > currentGun.shotTime
						&& !reloading) {
					shooting = true;
					currentGun.shoot(player);
					lastShot = System.currentTimeMillis();
					autoReady = true;
				}
			} else {
				shooting = false;
			}
			
		}
		
		if (currentGun.isAuto == true) {
			
			if (currentGun.magCurrent > 0 && System.currentTimeMillis() - lastShot > currentGun.shotTime
					&& !reloading) {
				autoReady = true;
			}
			
		}
	}

	public void scoringStuff() {
		
		scoreCount++;
		if (scoreCount % 25 == 0) {
			player.boostHealth();
		}
		
		if (scoreCount == 150) {
			scoreCount = 0;
			score++;
			points++;
		}
		if (score >= 100) {
			score = 0;
			roundCount += 1;
			supplyDrop.spawnSupplyDrop(player);
		}
	}

	public void spawnZombie() {
		
		if (ticks >= maxTicks) {
			ticks = 0;
			
			randomSpeed = (3 + (int) (Math.random() * 3)) * 0.25;
			int zombieY = ((int) (Math.random() * 2) == 1) ? -20 : 500;
			
			zombies.add(new Zombie((int) (Math.random() * 700) + 20, zombieY, 20, randomSpeed, zombieLives));
			spawnCount++;
			if (maxTicks > 50 && spawnCount % 2 == 0) {
				maxTicks--;
			}
			if (spawnCount == 100 || spawnCount == 50)
				zombieLives += 100;
			
		}
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
		
		
	}

	public void drawZombies(Graphics2D g) {
		
		for (Zombie z : zombies) {
			g.setColor(z.color);
			g.fill(z);
		}
	}

	public void drawPlayer(Graphics2D g) {
		
		g.setColor(Color.RED);
		g.fillOval(player.x, player.y, playerWidth, playerWidth);
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
		g.drawString("Score: " + String.valueOf(points), 20, 460);
		
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
		int centerX = player.x + 15;
		int centerY = player.y + 15;
		int sideX = getMouseX() - centerX;
		int sideY = getMouseY() - centerY;
		double distance = Math.sqrt(sideX * sideX + sideY * sideY);
		int a = centerX + (int) ((sideX * Math.sqrt(900)) / distance);
		int b = centerY + (int) ((sideY * Math.sqrt(900)) / distance);
		g.fillOval(a, b, 4, 4);
	}
	
	@Override
	public void setup() {
		// TODO Auto-generated method stub
		
		guns.put("Pistol", new Pistol());
		guns.put("AK-47", new Automatic());
		guns.put("Rifle", new Rifle());
		guns.put("Shotgun", new Shotgun());
		guns.put("Sniper", new Sniper());

		currentGun = guns.get("Pistol");

		upKey = KeyEvent.VK_R;
		downKey = KeyEvent.VK_S;
		leftKey = KeyEvent.VK_A;
		rightKey = KeyEvent.VK_D;
		
		movementVar = 3;
		shooting = false;
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
				shooting = true;
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
			shooting = false;
			currentGun.stopShooting();
		}
	}
	
	@Override
	public void customPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_R) {
			rPressed = true;
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
		
		// TODO Auto-generated method stub
		new Runner(new ZombiesTestRunner());
	}
}