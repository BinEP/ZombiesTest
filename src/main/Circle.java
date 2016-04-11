package main;
import java.awt.geom.Ellipse2D;

public class Circle extends Ellipse2D.Double {
	
	private static final long serialVersionUID = 1L;

	public Circle(int a, int b, int c){
		super(a, b, c, c);
	}
}