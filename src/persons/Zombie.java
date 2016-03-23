package persons;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;




import javafx.scene.shape.Line;


public class Zombie extends Ellipse2D.Double {
	public double speed;
	public double deltaX;
	public double deltaY;
	public double distance;
	
	public boolean isUndead = false;
	public boolean isShot = false;
	public boolean spreadOneHit = false;
	public boolean spreadTwoHit = false;
	public double movementVar;
	
	public int lives;
	public Color color; 
	public final Color FOREST = new Color(34, 139, 34);
	public final Color OLIVE = new Color(107, 142, 35);
	public final Color LIGHT_GREEN = new Color(144, 238, 144);
	
	public Zombie(double a, double b, double c, double d, int lives){
		super(a, b, c, c);
//		x = a;
//		y = b;
//		width = c; 
		this.movementVar = d; 
		this.lives = lives;
		this.setColor();
	}
	
	public void setColor(){
		switch(this.lives){
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
		
	public boolean moveX(Rectangle player, ArrayList<Zombie> zombies, int playerHealth){
		
		boolean loseHealth = false;
		deltaX = 0;
		if (player.x >= x){
			deltaX = movementVar;
		} else if (player.x <= x){
			deltaX = -movementVar;
		}
		
		x += deltaX;
		for (Zombie z : zombies){
				if (touchingZombie(z)){
					x -= deltaX;
				}
				if(touchingPlayer(player)){
					x -= deltaX;
					loseHealth = true;
				}
		}
		return loseHealth;
	}
	
	public boolean move(Rectangle player, ArrayList<Zombie> zombies, int playerHealth) {
		return moveX(player, zombies, playerHealth) ||
		moveY(player, zombies, playerHealth);

	}
	
	public boolean moveY(Rectangle player, ArrayList<Zombie> zombies, int playerHealth){
		
		boolean loseHealth = false;
		
		deltaY = 0;
		
		if (player.y >= y){
			deltaY = movementVar;
		} else if (player.y <= y){
			deltaY = -movementVar;
		}
		
		y += deltaY;
		for (Zombie z : zombies){
				if (touchingZombie(z)){
					y -= deltaY;
				}
				if (touchingPlayer(player)){
					y -= deltaY;
					loseHealth = true;
				}
			}
		return loseHealth;
	}
	
	public boolean touchingPlayer(Rectangle player){
		
		if (player.y != y || player.x != x){ // If not the same zombie
			double sideA = (int) (player.x - x);
			double sideB = (int) (player.y - y);
			distance = Math.sqrt(sideA * sideA + sideB * sideB);
			
			if (distance <= width){
				return true;
			} else{
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean touchingZombie(Zombie zombie){
		
		if (zombie.y != y || zombie.x != x){ // If not the same zombie
			double sideA = (int) (zombie.x - x);
			double sideB = (int) (zombie.y - y);
			distance = Math.sqrt(sideA * sideA + sideB * sideB);
			
			if (distance <= width){
				return true;
			} else{
				return false;
			}
		} else {
			return false;
		}
	}
	
	public void shot(Zombie zombie, Line2D.Double shot, String shotType){
		
		Rectangle zombieRect = new Rectangle((int)zombie.x, (int)zombie.y, (int)zombie.width, (int)zombie.width);
		
		if(shotType == "Normal"){
			if(zombieRect.intersectsLine(shot.getX1(), shot.getY1(), shot.getX2(), shot.getY2())){
				zombie.isShot = true;
			}
		} else if(shotType == "Spread One"){
			if(zombieRect.intersectsLine(shot.getX1(), shot.getY1(), shot.getX2(), shot.getY2())){
				zombie.spreadOneHit = true;
			}
		} else{
			if(zombieRect.intersectsLine(shot.getX1(), shot.getY1(), shot.getX2(), shot.getY2())){
				zombie.spreadTwoHit = true;
			}
		}
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
}