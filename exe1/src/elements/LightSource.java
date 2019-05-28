package elements;

import java.util.ArrayList;
import java.util.List;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Interface for light sources such as a lamp or spot lights
 */
public interface LightSource {
	public static final List<LightSource> EMPTY_LIST = new ArrayList<LightSource>();
	
	/**
	 * get the intensity of the light gets to a specific point
	 * @param point lighted
	 * @return light intensity at the point
	 */
	public Color getIntensity(Point point);
	/**
	 * get the direction from the light source to a specific point
	 * @param point in space
	 * @return vector from light to point
	 */
	public Vector getL(Point point);
	/**
	 * get the direction the light source is directed to
	 * @param point
	 * @return direction vector
	 */
	public Vector getD(Point point);
 
}
