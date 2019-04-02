package geometries;

import primitives.*;
/**
 * interface for a single geometry shape
 */
public interface Geometry extends Intersectable{
	/**
	 * get the normal vector to a given point on the surface of the geometry body
	 * @param point
	 * @return a new vector normalized and vertical to the given point
	 */
	Vector getNormal(Point point);
	
}
