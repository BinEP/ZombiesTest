package guns;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import persons.Figure;

public class Shotgun extends Gun {
	
	public Line2D.Double spreadOneLine = new Line2D.Double();
	public Line2D.Double spreadTwoLine = new Line2D.Double();
	
	public Shotgun() {
		super("Shotgun", 54, 9, false, 2000, 150, 100);
		
	}
	
	@Override
	public void shoot(Figure player) {
		
		
		// TODO Auto-generated method stub
		super.shoot(player);
		
		if (magCurrent >= 0) {
			AffineTransform af = new AffineTransform();
			af.setToRotation(Math.PI / 20, player.x + 15, player.y + 15);
			spreadOneLine.setLine(new Point(player.x + 15, player.y + 15), af.transform(shot.getP2(), null));
			
			af.setToRotation(-Math.PI / 20, player.x + 15, player.x + 15);
			spreadTwoLine.setLine(new Point(player.x + 15, player.y + 15), af.transform(shot.getP2(), null));
		}
		
	}
	
	
}
