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

/**
 * Represents a triangle in the space
 */
public class Triangle extends Plane {
	
	private Point bVertex;
	private Point cVertex;

	// ***************** Constructors ******************** //
	/**
	 * Ctor with three points
	 * @param a Vertex
	 * @param b Vertex
	 * @param c Vertex
	 */
	public Triangle(Point a, Point b, Point c) {
		//one point will be the point to set the plane which includes the triangle
		super(a, b, c);
		//the rest are the b and c vertices
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

}

