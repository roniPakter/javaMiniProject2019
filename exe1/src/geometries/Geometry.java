package geometries;
import java.util.List;

import primitives.*;
/**
 * interface for geometry shapes
 */
public interface Geometry {
	/**
	 * get the normal vector to a given point on the surface of the geometry body
	 * @param point
	 * @return a new vector normalized and vertical to the given point
	 */
	Vector getNormal(Point point);
	List<Point> findIntersections(Ray ray);
}
