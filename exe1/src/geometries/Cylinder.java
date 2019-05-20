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

import primitives.Color;

/**
 * a class to represent a cylinder (a tube with a height)
 */
public class Cylinder extends Tube {
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
		_emission = Color.WHITE;
	}
	
	/**
	 * Ctor that gets the radius the center ray and the height
	 * 
	 * @param radiusParm
	 * @param centerRayParm
	 * @param heightParm
	 * @param emission
	 */
	public Cylinder(double radiusParm, Ray centerRayParm, double heightParm, Color emission) {
		super(radiusParm, centerRayParm);
		height = heightParm;
		_emission = emission;
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
		Vector vector = centerRay.getVector();
		//z is the vector from the base point to p (the argument point)
		Vector z = p.subtract(p0);
		// t is the scalar to go to the point on the center ray
		double t = centerRay.getVector().DotProduct(z);
		//if the projection of z on the ray is 0 : p is on the bottom of the cylinder
		if (isZero(t))
			return vector.scale(-1);
		//if the projection is the height of the cylinder: p is on the upper base of the cylinder
		if (isZero(t - height))
			return vector;
		Vector projectionVector = centerRay.getVector().scale(t);
		// O would be the vector to the wanted point ont the center ray
		// O = P0 + (t*v)
		Point o = p0.addVector(projectionVector);
		// line from o to p will be orthogonal to the tube at the point p
		return p.subtract(o).normalization();
	}

}
