/*
 * Aharon Packter ID 201530508
 * Shlomo Perlov ID 206914301
 * 25/03/2019
 * 
 * Mini project in Software Engineering
 * Exercise 2
 */
package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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

	@Override 
	public List<Point> findIntersections(Ray ray) {
		List<Point>intersectPoints = super.findIntersections(ray); 
		Point rayBasePoint = ray.getBasePoint();
		
		if(intersectPoints.size() == 0)
			return new ArrayList<Point>();
		
		Vector v1 = point.subtract(rayBasePoint);
		Vector v2 = bVertex.subtract(rayBasePoint);
		Vector v3 = cVertex.subtract(rayBasePoint);
		Vector n1 = v1.crossProduct(v2).normalization();
		Vector n2 = v2.crossProduct(v3).normalization();
		Vector n3 = v3.crossProduct(v1).normalization();
		
		Vector pMinusP0;
		if(intersectPoints.get(0).equals(rayBasePoint)) 
			return this.findIntersections(new Ray(rayBasePoint.addVector(ray.getVector().scale(-0.005)), ray.getVector()));

		pMinusP0 = rayBasePoint.subtract(intersectPoints.get(0));
		double n1DotProductCheck = pMinusP0.DotProduct(n1);
		double n2DotProductCheck = pMinusP0.DotProduct(n2);
		double n3DotProductCheck = pMinusP0.DotProduct(n3);
		
		if(n1DotProductCheck < 0 && n2DotProductCheck < 0 && n3DotProductCheck < 0 ||
			n1DotProductCheck > 0 && n2DotProductCheck > 0 && n3DotProductCheck > 0	)
			return intersectPoints;
		return new ArrayList<Point>();
	 }

}

