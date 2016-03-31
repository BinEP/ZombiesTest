package persons;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;




import javafx.scene.shape.Line;


public class Zombie extends Figure {
	
	
	public boolean isUndead = false;
	
	public final Color FOREST = new Color(34, 139, 34);
	public final Color OLIVE = new Color(107, 142, 35);
	public final Color LIGHT_GREEN = new Color(144, 238, 144);
	
	public Zombie(double a, double b, double c, double d, int lives){
		super(a, b, c, d, lives);
//		x = a;
//		y = b;
//		width = c; 
		
		this.setColor();
		ifLoseHealth = false;
	}
	
	public void setColor(){
		switch(this.health){
		case 0:
			this.color = FOREST;
			break;
		case 100:
			this.color = OLIVE;
			break;
		case 200:
			this.color = Color.GREEN;
			break;
		case 300:
			this.color = LIGHT_GREEN;
			break;
		}
	}
	
	public boolean ifShot(Line2D.Double shot){
		
		Rectangle zombieRect = this.getBounds();
		return zombieRect.intersectsLine(shot.getX1(), shot.getY1(), shot.getX2(), shot.getY2());
				
	}
	
	public double getDistanceToPlayer(Rectangle player){
		int playerX = player.x + player.width/2;
		int playerY = player.y + player.width/2;
		int zombieX = (int)this.x + (int)this.width/2;
		int zombieY = (int)this.y + (int)this.width/2;
		
		int sideA = playerX - zombieX;
		int sideB = playerY - zombieY;
		
		double distance = Math.sqrt(sideA * sideA + sideB * sideB);
		
		return distance;
	}
	
	
	
	@Override
	public void move(Rectangle screen, ArrayList<? extends Figure> badFigures, Figure player) {
		moveX(player);
		moveY(player);
		checkCollisions(badFigures, player);
		// TODO Auto-generated method stub
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
	
	public boolean checkCollisions(ArrayList<? extends Figure> badFigures, Figure player) {
		super.checkCollisions(badFigures);
		
		if (touchingFigure(player)) {
			x -= deltaX;
			y -= deltaY;
		}
		return false;
	}
	
	public boolean isDead() {
		return health <= 0;
	}

	
	public void gotShot(int damage) {
		health -= damage;
		this.setColor();
	}
}