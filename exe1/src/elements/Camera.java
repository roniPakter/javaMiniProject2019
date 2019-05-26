package elements;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * Represents a camera watching on a 3d scene
 */
public class Camera {
	/** the position point of the camera */
	Point p0;
	/** the up direction vector of the camera */
	Vector vUp;
	/** the forward direction vector of the camera */
	Vector vTo;
	/** the right direction vector of the camera */
	Vector vRight;

	// ***************** Constructors ******************** //
	/**
	 * Ctor with parameters for the p0 position and the directions of the camera
	 * 
	 * @param position
	 * @param toDirection
	 * @param upDirection
	 */
	public Camera(Point position, Vector toDirection, Vector upDirection) {
		double orthogonalCheck = toDirection.DotProduct(upDirection);
		// in case the vector of up and to are not orthogonal - exception
		if (!Util.isZero(orthogonalCheck))
			throw new IllegalArgumentException("vTo and vUp are not orthogonal");

		p0 = new Point(position);
		vUp = upDirection.normalization();
		vTo = toDirection.normalization();
		vRight = (vTo.crossProduct(vUp)).normalization();
	}

	// ***************** Getters ******************** //
	/** returns the P0 point of the camera position */
	public Point getP0() {
		return p0;
	}

	/** returns the vector of the forward direction */
	public Vector getToVector() {
		return vTo;
	}

	/** returns the vector of the up direction */
	public Vector getUpVector() {
		return vUp;
	}

	/** returns the vector of the right direction */
	public Vector getRightVector() {
		return vRight;
	}

	// ***************** Administrations ******************** //
	/**
	 * string representation of the camera
	 */
	@Override
	public String toString() {
		return "P0 position: " + p0.toString() + "\nUp direction: " + vUp.toString() + "\nTo direction: "
				+ vTo.toString() + "\nRight direction: " + vRight.toString();
	}

	// ***************** Operations ******************** //
	/**
	 * given the details of the camera's current view plane (screen), create a ray
	 * goes from the camera through the center of an asked pixel
	 * 
	 * @param nx             the number of columns
	 * @param ny             the number of rows
	 * @param i              the place of the pixel in rows
	 * @param j              the pace of the pixel in columns
	 * @param screenDistance
	 * @param screenWidth
	 * @param screenHeight
	 * @return a Ray from the camera through the center of the [i,j] pixel
	 */
	public Ray constructRayThroughPixel(int nx, int ny, int j, int i, double screenDistance, double screenWidth,
			double screenHeight) {
		// Pc is the center point of the view plane: P0 + d*vTo
		Point pc = p0.addVector(vTo.scale(screenDistance));
		//ratio factors: rx is the width of each pixel
		double rx = screenWidth / nx;
		double ry = screenHeight / ny;
		// Xi and Yj are the coefficients that would take us to the asked point from the
		// Pc point
		// Xi for moving in the X axis direction (right / left)
		double xi = ((i - (nx / 2d)) * rx) + rx/2d;
		// Yj for moving in the Y axis direction (down / up)
		double yj = ((j - (ny / 2d)) * ry) + ry/2d;
		// in case the both coefficients are zero, the asked point is the Pc point
		Point pij = pc;
		if (!Util.isZero(xi)) {
			pij = pij.addVector(vRight.scale(xi));
		}
		if (!Util.isZero(yj)) {
			pij = pij.addVector(vUp.scale(-yj));
		}
		// the vector leads to the asked point is: Pij - P0
		Vector vij = pij.subtract(p0);
		return new Ray(p0, vij);
	}

}
