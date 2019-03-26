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
	private Vector directionVector;
	
	// ***************** Constructors ******************** //
	/**
	 * Ctor with the base point and the vector for direction
	 * @param parmPoint
	 * @param parmDirection
	 */
	public Ray(Point parmPoint, Vector parmDirection) {
		basePoint = new Point(parmPoint);
		directionVector = parmDirection.normalization();
	}
	
	/**
	 * copy Ctor
	 * @param other
	 */
	public Ray(Ray other) {
		basePoint = new Point(other.getBasePoint());
		directionVector = (new Vector(other.getDirectionVector()).normalization());
	}
	
	// ***************** Getters ******************** //
	public Point getBasePoint() {
		return basePoint;
	}
	
	public Vector getDirectionVector() {
		return directionVector;
	}
	
	// ***************** Administration ******************** //
	@Override
	public String toString() {
		return "Ray base point: " + basePoint + "\nDirection: " + directionVector;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof Ray))
			return false;
		Ray otherRay = (Ray)other;
		return basePoint.equals(otherRay.getBasePoint()) &&
				directionVector.equals(otherRay.getDirectionVector());		
	}

	// ***************** Operations ******************** //
}
