package elements;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Represents a camera watching on a 3d scene
 * 
 * @author ronip
 *
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
		if (orthogonalCheck != 0)
			throw new IllegalArgumentException("vTo and vUp are not orthogonal");

		p0 = new Point(position);
		vUp = upDirection.normalization();
		vTo = toDirection.normalization();
		vRight = (vTo.crossProduct(vUp)).normalization();
	}

	// ***************** Getters ******************** //
	public Point getP0() {
		return p0;
	}

	public Vector getToVector() {
		return vTo;
	}

	public Vector getUpVector() {
		return vUp;
	}

	public Vector getRightVector() {
		return vRight;
	}

	// ***************** Administrations ******************** //
	/**
	 * string representation of the camera
	 */
	@Override
	public String toString() {
		return "P0 position: " + p0.toString() + 
				"\nUp direction: " + vUp.toString() + 
				"\nTo direction: " + vTo.toString() + 
				"\nRight direction: " + vRight.toString();
	}

	// ***************** Operations ******************** //
	/**
	 * 
	 * @param nx the number of columns
	 * @param ny the number of rows
	 * @param i the place of the pixel in rows
	 * @param j the pace of the pixel in columns
	 * @param screenDistance 
	 * @param screenWidth 
	 * @param screenHeight
	 * @return a Ray from the camera through the center of the pixel
	 */
	public Ray constructRayThroughPixel(int nx, int ny, int i, int j, double screenDistance, double screenWidth, double screenHeight) {
		Point pc = p0.addVector(vTo.scale(screenDistance));
		double xi = (j - (nx - 1) / 2d) * screenWidth / nx;
		double yj = (i - (ny - 1) / 2d) * screenHeight / ny;
		Point pij = pc;
		if(xi != 0) 
		{
		pij = pij.addVector(vRight.scale(xi));
		}
		if(yj != 0)
		{
		pij = pij.addVector(vUp.scale(-yj));
		}	
		Vector vij = pij.subtract(p0);
		return new Ray(p0,vij);
	}


}









