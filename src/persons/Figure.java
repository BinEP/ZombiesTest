package persons;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Figure extends Ellipse2D.Double {
	
	public double speed;
	public double deltaX;
	public double deltaY;
	public double distance;
	public double movementVar;

	public int x;
	public int y;
	public int width;
	public int height;
	
	public boolean isShot = false;
	public int health;
	public Color color;
	
	public Figure(double x, double y, double w, double moveVar, int lives) {
		super(x, y, w, w);
		this.movementVar = moveVar;
		this.health = lives;
	}
	
	public boolean move(Figure player, ArrayList<Figure> badFigures, int playerHealth) {
		
		boolean loseHealth = false;
		moveX(player);
		moveY(player);
		
		if (touchingFigure(player)) {
			x -= deltaX;
			y -= deltaY;
			loseHealth = true;
		}
		
		for (Figure f : badFigures) {
			if (touchingFigure(f)) {
				x -= deltaX;
				y -= deltaY;
			}
			
		}
		
		return loseHealth;
		
	}
	
	public void moveX(Figure player) {
		
		deltaX = 0;
		if (player.x >= x) {
			deltaX = movementVar;
		} else if (player.x <= x) {
			deltaX = -movementVar;
		}
		
		x += deltaX;
		
	}
	
	public void moveY(Figure player) {
		
		deltaY = 0;
		
		if (player.y >= y) {
			deltaY = movementVar;
		} else if (player.y <= y) {
			deltaY = -movementVar;
		}
		
		y += deltaY;
		
	}
	
	public boolean touchingFigure(Figure player) {
		
		if (player.y != y || player.x != x) { // If not the same zombie
			double sideA = (int) (player.x - x);
			double sideB = (int) (player.y - y);
			distance = Math.sqrt(sideA * sideA + sideB * sideB);
			
			return distance <= width;
		}
		return false;
	}
}
