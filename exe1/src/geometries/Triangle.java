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
 * Represents a triangle
 */
public class Triangle extends Plane implements Geometry{
	
	private Point bVertex;
	private Point cVertex;

	// ***************** Constructors ******************** //
	public Triangle(Point a, Point b, Point c) {
		super(a, b, c);
		if(b.subtract(a).norm() + c.subtract(b).norm() <= a.subtract(c).norm())
			throw new IllegalArgumentException("ERROR! Invalid triangle");
		bVertex = new Point(b);
		cVertex = new Point(c);
	}

	// ***************** Getters ******************** //
	public Point getA() {
		return super.getPoint();
	}

	public Point getB() {
		return bVertex;
	}

	public Point getC() {
		return cVertex;
	}

	// ***************** Administration ******************** //
	/**
	 * Returns a string represents the values of the three vertices of the triangle
	 */
	@Override
	public String toString() {
		return "Vertex A: " + super.getPoint() + "\nVertex B: " + bVertex + "\nVetex C: " + cVertex;
	}

	// ***************** Operations ******************** //
	@Override
	public Vector getNormal(Point point) {
		return super.getNormal();
	}
}

