package guns;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Point2D;

import javafx.scene.shape.Line;


public class Gun {
	
	public int bullets;
	public final int maxBullets;
	public int magCurrent;
	public int magSize;
	public long reloadTime;
	public long shotTime;
	public Line2D.Double shot = new Line2D.Double();
	public Line2D.Double spreadOneLine = new Line2D.Double();
	public Line2D.Double spreadTwoLine = new Line2D.Double();
	public String name;
	public int damage;
	public boolean isAuto;
	
	public Gun(String name, int bullets, int magSize, boolean isAuto, long reloadTime, long shotTime, int damage){
		this.damage = damage;
		this.name = name;
		this.bullets = bullets;
		this.maxBullets = bullets;
		this.magCurrent = 0;
		this.isAuto = isAuto;
		this.magSize = magSize;
		this.reloadTime = reloadTime;
		this.shotTime = shotTime;
	}

	public void reload(){
		if(bullets > 0){
			if(magCurrent < magSize){
				int reloadValue = magSize - magCurrent;
				if (bullets >= reloadValue){
					bullets -= reloadValue;
					magCurrent += reloadValue;
				} else {
					magCurrent += bullets;
					bullets = 0;
				}
			}
		}
	}
	
	public void shoot(Rectangle player, int mouseX, int mouseY){
		int x;
		int y;
		int rise = mouseY - player.y;
		int run = mouseX - player.x;
		if(magCurrent > 0){
			magCurrent--;
			while((run + player.x > 0 && run + player.x < 800) && (rise + player.y > 0 && rise + player.y < 480)){
				run *= 2;
				rise *= 2;
			}
			x = player.x + run;
			y = player.y + rise;
			shot = new Line2D.Double(player.x + 15, player.y + 15, x, y);
			if(this.name == "Shotgun"){
				AffineTransform af = new AffineTransform();
				af.setToRotation(Math.PI / 20, player.x + 15, player.y + 15);
				spreadOneLine.setLine(new Point(player.x + 15, player.y + 15), af.transform(shot.getP2(), null));
				
				af.setToRotation(-Math.PI / 20, player.x + 15, player.x + 15);
				spreadTwoLine.setLine(new Point(player.x + 15, player.y + 15), af.transform(shot.getP2(), null));
			}
		}
	}	
}
