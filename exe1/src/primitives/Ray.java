/*
 * Aharon Packter ID 201530508
 * Shlomo Perlov ID 206914301
 * 25/03/2019
 * 
 * Mini project in Software Engineering
 * Exercise 2
 */
package primitives;

/**
 * represents a ray from a point in the space
 */
public class Ray {
	private Point basePoint;
	private Vector vector;
	
	// ***************** Constructors ******************** //
	/**
	 * Ctor with the base point and the vector for direction
	 * @param point
	 * @param direction
	 */
	public Ray(Point point, Vector direction) {
		basePoint = new Point(point);
		vector = direction.normalization();
	}
	
	/**
	 * copy Ctor
	 * @param other
	 */
	public Ray(Ray other) {
		basePoint = new Point(other.getBasePoint());
		vector = (new Vector(other.getVector()).normalization());
	}
	
	// ***************** Getters ******************** //
	public Point getBasePoint() {
		return basePoint;
	}
	
	public Vector getVector() {
		return vector;
	}
	
	// ***************** Administration ******************** //
	@Override
	public String toString() {
		return "Ray base point: " + basePoint + "\nDirection: " + vector;
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null || !(other instanceof Ray))
			return false;
		Ray otherRay = (Ray)other;
		return basePoint.equals(otherRay.getBasePoint()) &&
				vector.equals(otherRay.getVector());		
	}

	// ***************** Operations ******************** //
}
