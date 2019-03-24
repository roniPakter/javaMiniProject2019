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
	public Point(Point a) {
		this.x = new Coordinate(a.getX());
		this.y = new Coordinate(a.getY());
		this.z = new Coordinate(a.getZ());
	}

	public Point(Coordinate _x, Coordinate _y, Coordinate _z) {
		x = new Coordinate(_x);
		y = new Coordinate(_y);
		z = new Coordinate(_z);
	}

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
	public Vector subtract(Point a) {
		return new Vector(new Point(x.subtract(a.getX()), y.subtract(a.getY()), z.subtract(a.getZ())));
	}

	public Point add(Point a) {
		return new Point(x.add(a.getX()), y.add(a.getY()), z.add(a.getZ()));
	}

	public double distanceInSquare(Point a) {
		return (x.subtract(a.getX()).get() * x.subtract(a.getX()).get()
				+ y.subtract(a.getY()).get() * y.subtract(a.getY()).get()
				+ z.subtract(a.getZ()).get() * z.subtract(a.getZ()).get());
	}

	public double distance(Point a) {
		double dist = Math.sqrt(distanceInSquare(a));
		return isOne(dist) ? 1.0 : dist;
	}
}