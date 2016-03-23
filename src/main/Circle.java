package main;
import java.awt.geom.Ellipse2D;

public class Circle extends Ellipse2D.Double {
	
	public Circle(int a, int b, int c){
		super(a, b, c, c);
	}
}