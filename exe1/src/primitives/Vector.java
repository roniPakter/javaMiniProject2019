/*
 * Aharon Packter ID 201530508
 * Shlomo Perlov ID 206914301
 * 25/03/2019
 * 
 * Mini project in Software Engineering
 * Exercise 2
 */
package primitives;

import java.util.IllegalFormatFlagsException;
/**
 * A class represents a vector in 3D space
 */
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
	
	/**
	 * returns the vector string: (X, Y, Z)
	 */
	@Override
	public String toString() {
		return direction.toString();
	}

	// ***************** Operations ******************** //
	/**
	 * adding a vector to the vector 
	 * @param added
	 * @return a new vector (this + added)
	 */
	public Vector add(Vector added) {
		return new Vector(direction.add(added));
	}
	
	/**
	 * Subtracting a vector from the vector
	 * @return a new vector (this - added)
	 */
	public Vector substract(Vector a) {
		return direction.subtract(a.direction);
	}
	
/**
 * scalar multiplication on the vector
 * @param scalar
 * @return a new vector made of (this * scalar)
 */
	public Vector scale(double scalar) {
		return new Vector(
				scalar * direction.getX().get(), scalar * direction.getY().get(), scalar * direction.getZ().get());
	}
	
/**
 * dot product between two vectors
 * @param producter
 * @return a new vector made of (this * producter vector)
 */
	public double DotProduct(Vector producter) {
		return (direction.getX().scale(producter.direction.getX().get()).get()
				+ direction.getY().scale(producter.direction.getY().get()).get()
				+ direction.getZ().scale(producter.direction.getZ().get()).get());
	}
/**
 * cross product between two vectors
 * @param productor
 * @return a new vector which is vertical to the two vectors
 */
	public Vector crossProduct(Vector productor) {
		return new Vector(new Point(
				direction.getY().get() * productor.direction.getZ().get() - direction.getZ().get() * productor.direction.getY().get(),
				direction.getZ().get() * productor.direction.getX().get() - direction.getX().get() * productor.direction.getZ().get(),
				direction.getX().get() * productor.direction.getY().get() - direction.getY().get() * productor.direction.getX().get()));
	}
/**
 * get the length of the vector
 * @return a double value of the length
 */
	public double norm() {
		return direction.distance(Point.ZERO);
	}
/**
 * make a normalized vector in length of 1
 * @return a new normalized vector
 */
	public Vector normalization() {
		return scale(1 / norm());
	}
}
