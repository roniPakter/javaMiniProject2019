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
	public final static Vector Z_AXIS = new Vector(0, 0, 1);
	public final static Vector X_AXIS= new Vector(1, 0, 0);
	public final static Vector Y_AXIS= new Vector(0, 1, 0);
	private Point vector;

	// ***************** Constructors ******************** //
	public Vector(Point a) {
		// the zero vector is undefined and exception is thrown
		if (a.getX().isZero() && a.getY().isZero() && a.getZ().isZero())
			throw new IllegalFormatFlagsException("ERROR! vector can't be: (0,0,0)");
		vector = new Point(a);
	}

	public Vector(double _x, double _y, double _z) {
		// the zero vector is undefined and exception is thrown
		if (Util.isZero(_x) && Util.isZero(_y) && Util.isZero(_z))
			throw new IllegalFormatFlagsException("ERROR! vector can't be: (0,0,0)");
		vector = new Point(_x, _y, _z);
	}

	public Vector(Vector a) {
		vector = new Point(a.vector);
	}

	// ***************** Getters ******************** //
	public Point getVector() {
		return vector;
	}

	// ***************** Administration ******************** //
	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null || !(other instanceof Vector))
			return false;
		Vector otherVector = (Vector) other;
		return this.getVector().equals(otherVector.getVector());
	}

	/**
	 * returns the vector string: (X, Y, Z)
	 */
	@Override
	public String toString() {
		return vector.toString();
	}

	// ***************** Operations ******************** //
	/**
	 * adding a vector to the vector
	 * 
	 * @param added
	 * @return a new vector (this + added)
	 */
	public Vector add(Vector added) {
		return new Vector(vector.addVector(added));
	}

	/**
	 * Subtracting a vector from the vector
	 * 
	 * @return a new vector (this - added)
	 */
	public Vector substract(Vector other) {
		return vector.subtract(other.vector);
	}

	/**
	 * scalar multiplication on the vector
	 * 
	 * @param scalar
	 * @return a new vector made of (this * scalar)
	 */
	public Vector scale(double scalar) {
		return new Vector(scalar * vector.getX().get(), scalar * vector.getY().get(), scalar * vector.getZ().get());
	}

	/**
	 * dot product between two vectors
	 * 
	 * @param multiplicand
	 * @return a new vector made of (this * multiplicand vector)
	 */
	public double DotProduct(Vector multiplicand) {
		return (vector.getX().scale(multiplicand.vector.getX().get()).get()
				+ vector.getY().scale(multiplicand.vector.getY().get()).get()
				+ vector.getZ().scale(multiplicand.vector.getZ().get()).get());
	}

	/**
	 * cross product between two vectors
	 * 
	 * @param multiplicand
	 * @return a new vector which is vertical to the two vectors
	 */
	public Vector crossProduct(Vector multiplicand) {
		return new Vector(new Point(
				vector.getY().get() * multiplicand.vector.getZ().get()
						- vector.getZ().get() * multiplicand.vector.getY().get(),
				vector.getZ().get() * multiplicand.vector.getX().get()
						- vector.getX().get() * multiplicand.vector.getZ().get(),
				vector.getX().get() * multiplicand.vector.getY().get()
						- vector.getY().get() * multiplicand.vector.getX().get()));
	}

	/**
	 * get the length of the vector
	 * 
	 * @return a double value of the length
	 */
	public double length() {
		return vector.distance(Point.ZERO);
	}

	/**
	 * get the length of the vector
	 * 
	 * @return a double value of the length
	 */
	public double squaredLength() {
		double length = this.length();
		return length * length;
	}

	/**
	 * make a normalized vector in length of 1
	 * 
	 * @return a new normalized vector
	 */
	public Vector normalization() {
		return scale(1 / length());
	}
}
