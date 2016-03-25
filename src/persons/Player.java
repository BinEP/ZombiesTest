package persons;

import java.awt.geom.Ellipse2D;

public class Player extends Figure {

	public int maxHealth;
	
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
	
}
