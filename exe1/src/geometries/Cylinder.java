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
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.isZero;

/**
 * a class to represent a cylinder (a tube with a height)
 */
public class Cylinder extends Tube implements Geometry {
	private double height;

	// ***************** Constructors ******************** //
	/**
	 * Ctor that gets the radius the center ray and the height
	 * 
	 * @param radiusParm
	 * @param centerRayParm
	 * @param heightParm
	 */
	public Cylinder(double radiusParm, Ray centerRayParm, double heightParm) {
		super(radiusParm, centerRayParm);
		height = heightParm;
	}

	// ***************** Getters ******************** //
	public double getHeight() {
		return height;
	}

	// ***************** Administration ******************** //
	@Override
	public String toString() {
		return super.toString() + "\nHeight: " + height;
	}

	// ***************** Operation ******************** //
	@Override
	public Vector getNormal(Point p) {
		// p0 is the base point
		Point p0 = centerRay.getBasePoint();
		Vector vector = centerRay.getDirectionVector();
		// z is the normalized direction of the tube center
		Vector z = p.subtract(p0);
		// t is the scalar to go to the point on the center ray
		double t = centerRay.getDirectionVector().DotProduct(z);
		if (isZero(t))
			return vector.scale(-1);
		if (isZero(t - height))
			return vector;
		Vector projectionVector = centerRay.getDirectionVector().scale(t);
		// O would be the vector to the wanted point ont the center ray
		// O = P0 + (t*v)
		Point o = p0.add(projectionVector);
		// line from o to p will be orthogonal to the tube at the point p
		return p.subtract(o).normalization();
	}

}
