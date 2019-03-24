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

public class Plane implements Geometry {
	private Point point;
	private Vector normalVector;
	
	// ***************** Constructors ******************** //
	public Plane(Point pointParm, Vector normalParm) {
		point = new Point(pointParm);
		normalVector = (new Vector(normalParm)).normalization();
	}
	
	public Plane(Point point1, Point point2, Point point3) {
		Vector a = point2.subtract(point1);
		Vector b = point3.subtract(point1);
		normalVector = a.crossProduct(b).normalization();
		point = new Point(point1);
		
	}

	// ***************** Getters ******************** //
	public Point getPoint() {
		return point;
	}
	
	public Vector getNormal() {
		return normalVector;
	}

	// ***************** Administration ******************** //
	@Override
	public String toString() {
		return "Point: " + point + "\nNormal vector: " + normalVector;
	}

	// ***************** Operations ******************** //
	@Override
	public Vector getNormal(Point point) {
		return normalVector;
	}

}

