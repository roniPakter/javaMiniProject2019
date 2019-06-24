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
 * represents a plane in the space
 */
public class Plane extends Geometry {
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
		super();
		point = new Point(pointParm);
		normalVector = (new Vector(normalParm)).normalization();
	}

	/**
	 * Ctor with three points and color
	 * 
	 * @param point1
	 * @param point2
	 * @param point3
	 * @param emission
	 */
	public Plane(Point point1, Point point2, Point point3, Color emission) {
		// should we put "new" on this?
		super(emission, Material.NULL_MATERIAL);
		Vector a = point2.subtract(point1);
		Vector b = point3.subtract(point1);
		normalVector = a.crossProduct(b).normalization();
		point = new Point(point1);
	}

	/**
	 * cotr with a point and a normal vector and color
	 * 
	 * @param pointParm
	 * @param normalParm
	 * @param emission
	 */
	public Plane(Point pointParm, Vector normalParm, Color emission) {
		super(emission, Material.NULL_MATERIAL);
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
		super();
		Vector a = point2.subtract(point1);
		Vector b = point3.subtract(point1);
		normalVector = a.crossProduct(b).normalization();
		point = new Point(point1);
	}
	
	/**
	 * cotr with a point and a normal vector and color and material
	 * 
	 * @param pointParm
	 * @param normalParm
	 * @param emission
	 * @param material
	 */
	public Plane(Point pointParm, Vector normalParm, Color emission, Material material) {
		super(emission, material);
		point = new Point(pointParm);
		normalVector = (new Vector(normalParm)).normalization();
	}
	
	/**
	 * Ctor with three points and color and material
	 * 
	 * @param point1
	 * @param point2
	 * @param point3
	 * @param emission
	 * @param material
	 */
	public Plane(Point point1, Point point2, Point point3, Color emission, Material material) {
		super(emission, material);
		Vector a = point2.subtract(point1);
		Vector b = point3.subtract(point1);
		normalVector = a.crossProduct(b).normalization();
		point = new Point(point1);
	}

	// ***************** Getters ******************** //
	/**
	 * get a point that is in the plane
	 */
	public Point getPoint() {
		return point;
	}

	/**
	 * get a normalized vector that is orthogonal to the plane
	 */
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

	/**
	 * returns a list with all intersection points of a given ray with the plane
	 */
	@Override
	public List<GeoPoint> findIntersections(Ray ray) {
		List<GeoPoint> list;
		// in case the ray is included in the plane or parallel to it: empty list
		if (Util.isZero(normalVector.dotProduct(ray.getVector())))
			return EMPTY_LIST;
		// in case (rare...) the base point is the representing point of the plane: add
		// it.
		if (point.equals(ray.getBasePoint())) {
			list = new ArrayList<GeoPoint>();
			list.add(new GeoPoint(ray.getBasePoint(), this));
			return list;
		}

		// t is the coefficient of the
		double t = point.subtract(ray.getBasePoint()).dotProduct(normalVector)
				/ normalVector.dotProduct(ray.getVector());
		if (t < 0)
			return EMPTY_LIST;
		if (Util.isZero(t)) {
			list = new ArrayList<GeoPoint>();
			list.add(new GeoPoint(ray.getBasePoint(), this));
			return list;
		}

		Point p = new Point(ray.getBasePoint().addVector(ray.getVector().scale(t)));
		list = new ArrayList<GeoPoint>();
		list.add(new GeoPoint(p, this));
		return list;
	}
}
