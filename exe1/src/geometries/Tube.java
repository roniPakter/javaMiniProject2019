/*
 * Aharon Packter ID 201530508
 * Shlomo Perlov ID 206914301
 * 
 * 19/03/2019 
 * Mini project in Software Engineering
 * Exercise 1
 */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.isZero;

import java.util.List;

/**
 * a class to represent an infinite tube
 */
public class Tube extends RadialGeometries {
	protected Ray centerRay;

	// ***************** Constructors ******************** //
	/**
	 * Cotr with the radius and the center ray
	 * @param radiusParm
	 * @param centerRayParm
	 */
	public Tube(double radiusParm, Ray centerRayParm) {
		super(radiusParm);
		centerRay = new Ray(centerRayParm);
	}
	
	// ***************** Getters ******************** //
	public Ray getCenterRay() {
		return centerRay;
	}
	
	// ***************** Administration ******************** //
	@Override
	public String toString() {
		return "Center ray: " + centerRay.toString() + "\nRadius: " + super.toString();
	}

	// ***************** Operations ******************** //
	@Override
	public Vector getNormal(Point p) {
		//p0 is the base point
		Point p0 = centerRay.getBasePoint();
		//z is the vector from the base point to p (the argument point)
		Vector z = p.subtract(p0);
		//t is the scalar to go to the point on the center ray
		double t = centerRay.getVector().DotProduct(z);
		if (isZero(t))
			return z.normalization();
		//scaling the center vector by t gives the projection of z on the center vector
		Vector projectionVector = centerRay.getVector().scale(t);
		//O would be the vector from the base to the wanted point on the center ray
		//O = P0 + (t*v)
		Point o = p0.addVector(projectionVector);
		// line from o to p will be orthogonal to the tube at the point p
		return p.subtract(o).normalization();
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
}

