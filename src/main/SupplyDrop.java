package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import guns.DropItem;
import guns.Gun;
import persons.Player;

public class SupplyDrop extends Rectangle implements ActionListener {
	
	public boolean displaySupplyDrop = false;
	public int insideDrop;
	public int dropMessageDuration = 2;
	public String dropMessage;
	private DropItem dropItem;
	private Timer drawMessage = new Timer(dropMessageDuration * 1000, this);
	
	
	public void spawnSupplyDrop(Player player) {
		displaySupplyDrop = true;
		x = (int) (Math.random() * 650) + 20;
		y = (int) (Math.random() * 350) + 70;
		while (x > player.x - 40 && x < player.x + 40) {
			y = (int) (Math.random() * 760) + 20;
		}
		while (x > player.y - 40 && x < player.y + 40) {
			y = (int) (Math.random() * 400) + 20;
		}
		insideDrop = (int) (Math.random() * 6);
		
		
		if (ZombiesTestRunner.guns.keySet().toArray(null)[insideDrop] instanceof String) {
			String key = (String) ZombiesTestRunner.guns.keySet().toArray(null)[insideDrop];
			if (key.equals("Ammo")) {
				dropItem = new DropItem(true);
			} else {
				dropItem = new DropItem(key, ZombiesTestRunner.guns.get(key));
			}
		}
		
		drawMessage.setInitialDelay(dropMessageDuration * 1000);
		drawMessage.setRepeats(false);
		drawMessage.start();
	}
	
	public void applyDrop(Gun gun) {
		if (dropItem.isNewGun()) {
			gun = dropItem.getGun();
		}
		gun.maxAmmo();
		gun.applyDrop();
		displaySupplyDrop = false;
	}
	
	public void draw(Graphics2D g) {
		if (displaySupplyDrop) {
			g.setColor(Color.YELLOW);
			g.fill(this);
		}
		
		if (drawMessage.isRunning()) {
			g.setColor(Color.ORANGE);
			g.setFont(new Font("Century Gothic", Font.PLAIN, 36));
			g.drawString(dropMessage, x, y);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// TODO Auto-generated method stub
		
	}
	
}
