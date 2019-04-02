package geometries;

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
	List<Point> findIntersections(Ray ray);
}
