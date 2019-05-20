package geometries;

import primitives.*;
/**
 * Abstract class for every geometry shape
 */
public abstract class Geometry implements Intersectable{
	/**
	 * emission light of each geometric body
	 */
	protected Color _emission;
	
	// ***************** Getters/Setters ******************** //
	/**
	 * get the emission light of the geometric body
	 */
	public Color getEmission() {
		return _emission;
	}
	
	/**
	 * set the emission light of the geometric body
	 */
	public void setEmission(Color emissionColor) {
		_emission = emissionColor;
	}
	
	
	// ***************** Operation ******************** //
	/**
	 * get the normal vector to a given point on the surface of the geometry body
	 * @param point
	 * @return a new vector normalized and vertical to the given point
	 */
	abstract Vector getNormal(Point point);
}
