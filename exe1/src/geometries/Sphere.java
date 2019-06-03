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

import primitives.Color;
import primitives.Material;
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

	/**
	 * Ctor with radius and center ray and color
	 * 
	 * @param radiusParm
	 * @param centerParm
	 * @param emission
	 */
	public Sphere(double radiusParm, Point centerParm, Color emission) {
		super(radiusParm, emission, Material.NULL_MATERIAL);
		centerPoint = new Point(centerParm);
	}
	
	/**
	 * Ctor with radius and center ray and color and material
	 * 
	 * @param radiusParm
	 * @param centerParm
	 * @param emission
	 * @param material
	 */
	public Sphere(double radiusParm, Point centerParm, Color emission, Material material) {
		super(radiusParm, emission, material);
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
	public List<GeoPoint> findIntersections(Ray ray) {
		List<GeoPoint> intersectPoints = new ArrayList<GeoPoint>();
		Point o = centerPoint;
		Point p0 = ray.getBasePoint();
		Vector v = ray.getVector();

		if (o.equals(p0)) {
			intersectPoints.add(new GeoPoint(p0.addVector(v.scale(radius)), this));
			return intersectPoints;
		}
		
		// the vector goes from the ray base to the sphere center
		Vector u = o.subtract(p0);
		// the length of the projection of u on the ray
		double tm = v.dotProduct(u);
		// the shortest distance from the sphere center to the ray
		double d = Math.sqrt(u.squaredLength() - (tm * tm));

		if (Util.isZero(Util.usubtract(u.length(), radius)))
			intersectPoints.add(new GeoPoint(p0, this));

		// if the distance is bigger then the radius - there are no intersections
		if (Util.usubtract(radius, d) < 0)
			return intersectPoints;
		// th is the distance from the chord(made of the ray in the sphere) to the
		// intersection points.
		double th = Math.sqrt((radius * radius) - (d * d));

		double tmPlusTh = Util.alignZero(tm + th);
		if (tmPlusTh > 0)
			intersectPoints.add(new GeoPoint(p0.addVector(v.scale(tmPlusTh)), this));
		double tmMinusTh = Util.alignZero(tm - th);
		if (tmMinusTh > 0)
			intersectPoints.add(new GeoPoint(p0.addVector(v.scale(tmMinusTh)), this));
		// if the distance is radius then the only intersection is the point of (tm * v)
		if (Util.isZero(Util.usubtract(d, radius)) && !(Util.isZero(tm)) && tm > 0) {
			intersectPoints.add(new GeoPoint(p0.addVector(v.scale(tm)), this));
		}
		return intersectPoints;

	}
}
