package guns;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Shot extends Line2D.Double {
	
	private boolean shotHit = false;
	
	public Shot() {
	}
	
	public Shot(double x1, double y1, double x2, double y2) {
	    setLine(x1, y1, x2, y2);
	}
	
	public Shot(Point2D p1, Point2D p2) {
	    setLine(p1, p2);
	}

	public void shotHit() {
		shotHit = true;
	}
	
	public void unshoot() {
		shotHit = false;
	}
	
	public boolean isShot() {
		return shotHit;
	}
}
