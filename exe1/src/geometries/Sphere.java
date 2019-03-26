/*
 * Aharon Packter ID 201530508
 * Shlomo Perlov ID 206914301
 * 25/03/2019
 * 
 * Mini project in Software Engineering
 * Exercise 2
 */

package geometries;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a sphere in the 3D space
 */
public class Sphere extends RadialGeometries {
	private Point centerPoint;

	// ***************** Constructors ******************** //
	/**
	 * Ctor with radius and center ray
	 * @param radiusParm
	 * @param centerParm
	 */
	public Sphere(double radiusParm, Point centerParm) {
		super(radiusParm);
		centerPoint = new Point(centerParm);
	}
	
	// ***************** Getters ******************** //
	public Point getCenter() {
		return centerPoint;
	}
	
	// ***************** Administration ******************** //
	@Override
	public String toString() {
		return "Center Point: " + centerPoint + "\nRadius: " + super.getRadius();
	}
	
	// ***************** Operations ******************** //
	@Override
	public Vector getNormal(Point point) {
		//normal = (point - center point) normalized
		return point.subtract(centerPoint).normalization();
	}
}

