/*
 * Aharon Packter ID 201530508
 * Shlomo Perlov ID 206914301
 * 25/03/2019
 * 
 * Mini project in Software Engineering
 * Exercise 2
 */
package primitives;

import static primitives.Util.*;

/**
 * Representing a point in a Euclidean 3D space with the coordinates: (x,y,z)
 */
public class Point {
	public final static Point ZERO = new Point(0, 0, 0);
	private Coordinate x;
	private Coordinate y;
	private Coordinate z;

	// ***************** Constructors ********************** //
	/**
	 * copy Ctor
	 * @param otherPoint
	 */
	public Point(Point otherPoint) {
		this.x = new Coordinate(otherPoint.getX());
		this.y = new Coordinate(otherPoint.getY());
		this.z = new Coordinate(otherPoint.getZ());
	}

	/**
	 * Ctor with three coordinates
	 * @param _x Coordinate
	 * @param _y Coordinate
	 * @param _z Coordinate
	 */
	public Point(Coordinate _x, Coordinate _y, Coordinate _z) {
		x = new Coordinate(_x);
		y = new Coordinate(_y);
		z = new Coordinate(_z);
	}

	/**
	 * Ctor with double values
	 * @param _x Value
	 * @param _y Value
	 * @param _z Value
	 */
	public Point(double _x, double _y, double _z) {
		x = new Coordinate(_x);
		y = new Coordinate(_y);
		z = new Coordinate(_z);
	}

	// ***************** Getters/Setters ********************** //
	public Coordinate getX() {
		return x;
	}

	public Coordinate getY() {
		return y;
	}

	public Coordinate getZ() {
		return z;
	}
	// ***************** Administration ******************** //

	/**
	 * Checks whether a point is the same as the argument point
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null || !(other instanceof Point))
			return false;
		Point temPoint = (Point) other;
		return (x.equals(temPoint.getX()) && y.equals(temPoint.getY()) && z.equals(temPoint.getZ()));
	}

	/**
	 * Returns the string representation of a point: "(1, 2, 3)"
	 */
	@Override
	public String toString() {

		return "(" + x + ", " + y + ", " + z + ")";
	}

	// ***************** Operations ******************** //
	/**
	 * subtract a point from the current point
	 * @param otherPoint
	 * @return new vector from the point to the other point
	 */
	public Vector subtract(Point otherPoint) {
		//vector - vector = (x1 - x2, y1 - y2, z1 - z2)
		return new Vector(new Point(x.subtract(otherPoint.getX()), y.subtract(otherPoint.getY()), z.subtract(otherPoint.getZ())));
	}
	
	/**
	 * add a point to another point
	 * @param otherPoint
	 * @return new Point added from the two
	 */
	public Point add(Vector vector) {
		//point + vector = (x1 + x2, y1 + y2, z1 + z2)
		Point otherPoint = vector.getDirection();
		return new Point(x.add(otherPoint.getX()), y.add(otherPoint.getY()), z.add(otherPoint.getZ()));
	}

	/**
	 * square distance from the 0,0,0 
	 * @param other
	 * @return the distance
	 */
	public double distanceInSquare(Point other) {
		//
		return (x.subtract(other.getX()).get() * x.subtract(other.getX()).get()
				+ y.subtract(other.getY()).get() * y.subtract(other.getY()).get()
				+ z.subtract(other.getZ()).get() * z.subtract(other.getZ()).get());
	}

	public double distance(Point a) {
		double dist = Math.sqrt(distanceInSquare(a));
		return isOne(dist) ? 1.0 : dist;
	}
}