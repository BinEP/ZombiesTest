package persons;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public abstract class Figure extends Ellipse2D.Double {

	private static final long serialVersionUID = 1L;
	public double speed;
	public double deltaX;
	public double deltaY;
	public double movementVar;
	public boolean ifLoseHealth = true;
	public boolean onScreen = true;
	
	public int health;
	public Color color;
	
	public Figure(double x, double y, double w, double moveVar, int lives) {
		super(x, y, w, w);
		this.movementVar = moveVar;
		this.health = lives;
	}
	
	public abstract void move(Rectangle screen, ArrayList<? extends Figure> badFigures, Figure player);
	
	protected boolean checkCollisions(ArrayList<? extends Figure> badFigures) {
		
		boolean loseHealth = false;
		
		for (Figure f : badFigures) {
			if (!f.equals(this) && touchingFigure(f)) {
				x -= deltaX;
				y -= deltaY;
				loseHealth = true;
			}
		}
		
		return loseHealth && ifLoseHealth;
		
	}
	
	public boolean touchingFigure(Figure player) {
		
		if (player.y != y || player.x != x) { // If not the same zombie
			double sideA = (int) (player.x - x);
			double sideB = (int) (player.y - y);
			double distance = Math.sqrt(sideA * sideA + sideB * sideB);
			
			return distance <= width;
		}
		return false;
	}
	
	public void translateWithTransform(AffineTransform transform) {
		Shape s = transform.createTransformedShape(this);
		this.x = s.getBounds().x;
		this.y = s.getBounds().y;
		this.width = s.getBounds().width;
		this.height = s.getBounds().height;
	}
}
