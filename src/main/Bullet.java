package main;
import java.awt.Point;
import java.awt.Rectangle;


public class Bullet extends Rectangle {
	
	private static final long serialVersionUID = 1L;
	public int startX;
	public int startY;
	public int spread;
	
	public Bullet(Point start, int width, int spread){
		super(start);
		this.startX = start.x;
		this.startY = start.y;
		this.spread = spread;
		this.height = 1;
		this.width = width;	
	}
}
