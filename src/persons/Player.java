package persons;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Player extends Figure {

	public int maxHealth;
	public int loseHealthByZombie = 2;
	private boolean collision = false;
	
	public Player(double x, double y, double w, double moveVar, int lives) {
		super(x, y, w, moveVar, lives);
		// TODO Auto-generated constructor stub
		maxHealth = lives;
	}
	
	public Player(double x, double y, double w, double moveVar, int lives, int maxHealth) {
		super(x, y, w, moveVar, lives);
		// TODO Auto-generated constructor stub
		this.maxHealth = maxHealth;
	}
	
	public void boostHealth() {
		if (health < maxHealth)
			health++;
	}
	
	@Override
	public void move(Rectangle screen, ArrayList<? extends Figure> badFigures, Figure player) {
		
		collision = false;
		
		y += deltaY;
		
		if (!screen.contains(getBounds2D())) {
			y -= deltaY;
		}
		
		if(checkCollisions(badFigures)){
			y -= deltaY;
		}
		
		x += deltaX;
		
		if (!screen.contains(getBounds2D()) || checkCollisions(badFigures)) {
			x -= deltaX;
		}
		if(checkCollisions(badFigures)){
			x -= deltaX;
		}
		
//		if (checkCollisions(badFigures)) {
//			health -= loseHealthByZombie;
//		}
		
		
	}
	
	public boolean checkCollisions(ArrayList<? extends Figure> badFigures) {
		return super.checkCollisions(badFigures);
	}
	
	public void loseHealth(){
		health -= loseHealthByZombie;
	}

	
	
}