/*
	 * Aharon Packter
	 * ID 201530508
	 * Shlomo Perlov
	 * ID 206914301
	 * 19/03/2019
	 * 
	 * Mini project in Software Engineering
	 * Exercise 1
	 */

package geometries;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a sphere in the space
 */
public class Sphere extends RadialGeometries {
	private Point centerPoint;

	// ***************** Constructors ******************** //
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
		return point.subtract(centerPoint).normalization();
	}
}

