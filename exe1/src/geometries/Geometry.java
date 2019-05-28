package geometries;

import primitives.*;
/**
 * Abstract class for every geometry shape
 */
public abstract class Geometry implements Intersectable{
	
	/**emission light of each geometric body*/
	protected Color _emission;
	/**the material the of the geometric body*/
	protected Material _material;
	
	// ***************** Getters ******************** //
	/**
	 * get the emission light of the geometric body
	 */
	public Color getEmission() {
		return _emission;		
	}
	
	/**
	 * get the material the geometric body is made of
	 */
	public Material getMaterial() {
		return _material;
	}	
	
	// ***************** Setters ******************** //
	/**
	 * set the material the geometric body is made of
	 */
	public void setMaterial(Material material) {
		_material = material;
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
