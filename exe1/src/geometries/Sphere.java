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

import primitives.Util.*;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
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

	@Override
	public List<Point> findIntersections(Ray ray) {
		List<Point> intersectPoints = new ArrayList<Point>();
		Point o = centerPoint;
		Point p0 = ray.getBasePoint();
		Vector v = ray.getVector();
		double r = radius;
		//the vector goes from the ray base to the sphere center
		Vector u = o.subtract(p0);
		//the length of the projection of u on the ray
		double tm = v.DotProduct(u);
		//the shortest distance from the sphere center to the ray
		double d = Math.sqrt(u.squaredLength() - (tm * tm));
		
		//if the distance is 0 then the only intersection is the point of (tm * v)
		if (Util.isZero(Util.usubtract(d, radius))) {
			intersectPoints.add(p0.addVector(v.scale(tm)));
			return intersectPoints;
		}
			
		//if the distance is bigger then the radius - there are no intersections
		if(Util.usubtract(d, radius) < 0)
			return intersectPoints;
		double th = Math.sqrt(a)
		
	}
}

