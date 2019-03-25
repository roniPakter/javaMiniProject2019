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

import java.util.IllegalFormatFlagsException;

public class Vector {
	private Point direction;

	// ***************** Constructors ******************** //
	public Vector(Point a) {
		if (a.getX().isZero() && a.getY().isZero() && a.getZ().isZero())
			throw new IllegalFormatFlagsException("ERROR! vector can't be: (0,0,0)");
		direction = new Point(a);
	}

	public Vector(double _x, double _y, double _z) {
		if (Util.isZero(_x) && Util.isZero(_y) && Util.isZero(_z))
			throw new IllegalFormatFlagsException("ERROR! vector can't be: (0,0,0)");
		direction = new Point(_x, _y, _z);
	}

	public Vector(Vector a) {
		direction = new Point(a.direction);
	}

	// ***************** Getters ******************** //
	public Point getDirection() {
		return direction;
	}

	// ***************** Administration ******************** //
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof Vector))
			return false;
		Vector otherVector = (Vector) other;
		return this.getDirection().equals(otherVector.getDirection());
	}

	@Override
	public String toString() {
		return direction.toString();
	}

	// ***************** Operations ******************** //
	public Vector add(Vector a) {
		return new Vector(direction.add(a.getDirection()));
	}
	
	public Vector substract(Vector a) {
		return direction.subtract(a.direction);
	}

	public Vector scale(Double a) {
		return new Vector(
				a * direction.getX().get(), a * direction.getY().get(), a * direction.getZ().get());
	}

	public double DotProduct(Vector a) {
		return (direction.getX().scale(a.direction.getX().get()).get()
				+ direction.getY().scale(a.direction.getY().get()).get()
				+ direction.getZ().scale(a.direction.getZ().get()).get());
	}

	public Vector crossProduct(Vector a) {
		return new Vector(new Point(
				direction.getY().get() * a.direction.getZ().get() - direction.getZ().get() * a.direction.getY().get(),
				direction.getZ().get() * a.direction.getX().get() - direction.getX().get() * a.direction.getZ().get(),
				direction.getX().get() * a.direction.getY().get() - direction.getY().get() * a.direction.getX().get()));
	}

	public double norm() {
		return direction.distance(Point.ZERO);
	}

	public Vector normalization() {
		return scale(1 / norm());
	}
}
