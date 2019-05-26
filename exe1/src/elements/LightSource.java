package elements;



import primitives.Color;
import primitives.Point;
import primitives.Vector;

public interface LightSource {
	public Color getIntensity(Point point);
	public Vector getL(Point point);
	public Vector getD(Point point);
 
}
