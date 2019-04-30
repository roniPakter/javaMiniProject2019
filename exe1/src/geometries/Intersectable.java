package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.Point;
import primitives.Ray;

/**
 * represents all kinds of geometry bodies (one or a group)
 */
public interface Intersectable {
	/**
	 * a method to find all intersection point of a given ray with the geometry body
	 * @param ray
	 * @return a list of the intersection points
	 */
	public static final List<Point> EMPTY_LIST = new ArrayList<Point>();
	List<Point> findIntersections(Ray ray);
}
