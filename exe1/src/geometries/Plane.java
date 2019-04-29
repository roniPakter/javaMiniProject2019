/*
 * Aharon Packter ID 201530508
 * Shlomo Perlov ID 206914301
 * 25/03/2019
 * 
 * Mini project in Software Engineering
 * Exercise 2
 */
package geometries;

import java.lang.annotation.Retention;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * represents a plane in the space
 */
public class Plane implements Geometry {
	protected Point point;
	protected Vector normalVector;

	// ***************** Constructors ******************** //
	/**
	 * cotr with a point and a normal vector
	 * 
	 * @param pointParm
	 * @param normalParm
	 */
	public Plane(Point pointParm, Vector normalParm) {
		point = new Point(pointParm);
		normalVector = (new Vector(normalParm)).normalization();
	}

	/**
	 * Ctor with three points
	 * 
	 * @param point1
	 * @param point2
	 * @param point3
	 */
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

	@Override
	public List<Point> findIntersections(Ray ray) {
		List<Point> list = new ArrayList<Point>();
		
		if (Util.isZero(normalVector.DotProduct(ray.getVector())))
			return list;
		
		if(this.getPoint().equals(ray.getBasePoint())) {
			list.add(ray.getBasePoint());
			return list;
		}
					
		double t= this.getPoint().subtract(ray.getBasePoint()).DotProduct(normalVector)
				/ normalVector.DotProduct(ray.getVector());
		if(t< 0)
			return list;
		if (Util.isZero(t)) {
			list.add(ray.getBasePoint());
			return list;
		}
			
		Point p = new Point(ray.getBasePoint()
			.addVector(ray.getVector().scale(t)));
		
		list.add(p);
		return list;
	}
}
