import java.awt.Rectangle;


public class Bullet {
	
	public int startX;
	public int startY;
	public int x;
	public int y;
	public int width;
	public int height = 1;
	public int spread;
	
	public Bullet(Rectangle start, int width, int spread){
		this.startX = start.x;
		this.startY = start.y;
		this.spread = spread;
		this.width = width;	
	}
}
