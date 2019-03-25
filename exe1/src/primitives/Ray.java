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

public class Ray {
	private Point basePoint;
	private Vector directionVector;
	
	// ***************** Constructors ******************** //
	public Ray(Point parmPoint, Vector parmDirection) {
		basePoint = new Point(parmPoint);
		directionVector = parmDirection.normalization();
	}
	
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
