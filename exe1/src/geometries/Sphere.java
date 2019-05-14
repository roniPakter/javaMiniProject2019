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
	 * 
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
		// normal = (point - center point) normalized
		return point.subtract(centerPoint).normalization();
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		List<Point> intersectPoints = new ArrayList<Point>();
		Point o = centerPoint;
		Point p0 = ray.getBasePoint();
		Vector v = ray.getVector();

		if (o.equals(p0)) {
			intersectPoints.add(p0.addVector(v.scale(radius)));
			return intersectPoints;
		}
		// the vector goes from the ray base to the sphere center
		Vector u = o.subtract(p0);
		// the length of the projection of u on the ray
		double tm = v.DotProduct(u);
		// the shortest distance from the sphere center to the ray
		double d = Math.sqrt(u.squaredLength() - (tm * tm));

		if (Util.isZero(Util.usubtract(u.length(), radius)))
			intersectPoints.add(p0);

		// if the distance is bigger then the radius - there are no intersections
		if (Util.usubtract(radius, d) < 0)
			return intersectPoints;
		// th is the distance from the chord(made of the ray in the sphere) to the
		// intersection points.
		double th = Math.sqrt((radius * radius) - (d * d));

		if ((tm + th) > 0 && !Util.isZero(tm + th))
			intersectPoints.add(p0.addVector(v.scale(tm + th)));
		if ((tm - th) > 0 && !Util.isZero(tm - th))
			intersectPoints.add(p0.addVector(v.scale(tm - th)));
		// if the distance is radius then the only intersection is the point of (tm * v)
		if (Util.isZero(Util.usubtract(d, radius)) && !(Util.isZero(tm)) && tm > 0) {
			intersectPoints.add(p0.addVector(v.scale(tm)));
		}
		return intersectPoints;

	}
}
