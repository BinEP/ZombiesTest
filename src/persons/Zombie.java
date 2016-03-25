package persons;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;




import javafx.scene.shape.Line;


public class Zombie extends Figure {
	
	
	public boolean isUndead = false;
	
	public boolean spreadOneHit = false;
	public boolean spreadTwoHit = false;
	
	
	public final Color FOREST = new Color(34, 139, 34);
	public final Color OLIVE = new Color(107, 142, 35);
	public final Color LIGHT_GREEN = new Color(144, 238, 144);
	
	public Zombie(double a, double b, double c, double d, int lives){
		super(a, b, c, d, lives);
//		x = a;
//		y = b;
//		width = c; 
		
		this.setColor();
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
	
	
	
	public void shot(Line2D.Double shot, String shotType){
		
		Rectangle zombieRect = new Rectangle((int) x, (int) y, (int) width, (int) width);
		
		if(shotType == "Normal"){
			if(zombieRect.intersectsLine(shot.getX1(), shot.getY1(), shot.getX2(), shot.getY2())){
				isShot = true;
			}
		} else if(shotType == "Spread One"){
			if(zombieRect.intersectsLine(shot.getX1(), shot.getY1(), shot.getX2(), shot.getY2())){
				spreadOneHit = true;
			}
		} else{
			if(zombieRect.intersectsLine(shot.getX1(), shot.getY1(), shot.getX2(), shot.getY2())){
				spreadTwoHit = true;
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