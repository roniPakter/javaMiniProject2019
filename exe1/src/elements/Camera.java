package elements;

import java.util.ArrayList;
import java.util.List;
import geometries.Intersectable.GeoPoint;
import geometries.Plane;
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
		double orthogonalCheck = toDirection.dotProduct(upDirection);
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
	 * given the details of the camera's current view plane (screen), create a beam
	 * of rays goes from the camera through the center of an asked pixel, if the
	 * aperture size is 0 - the beam is only one ray
	 * 
	 * @param nx             the number of columns
	 * @param ny             the number of rows
	 * @param i              the place of the pixel in rows
	 * @param j              the pace of the pixel in columns
	 * @param screenDistance
	 * @param focusLength
	 * @param apetureSize
	 * @param screenWidth
	 * @param screenHeight
	 * @param rayBeamSize    - for the blur of the unfocused objects
	 * @return a Ray beam from the camera through the center of the [i,j] pixel
	 */
	public List<Ray> constructRaysThroughPixel(int nx, int ny, int j, int i, double screenDistance, double focusLength,
			double apertureSize, double screenWidth, double screenHeight, int rayBeamSize) {
		// Pc is the center point of the view plane: P0 + d*vTo
		Point pc = p0.addVector(vTo.scale(screenDistance));
		// ratio factors: rx is the width of each pixel
		double rx = screenWidth / nx;
		double ry = screenHeight / ny;
		// Xi and Yj are the coefficients that would take us to the asked point from the
		// Pc point
		// Xi for moving in the X axis direction (right / left)
		double xi = ((i - (nx / 2d)) * rx) + rx / 2d;
		// Yj for moving in the Y axis direction (down / up)
		double yj = ((j - (ny / 2d)) * ry) + ry / 2d;
		// in case the both coefficients are zero, the asked point is the Pc point
		Point pixelCenterPoint = pc;
		if (!Util.isZero(xi)) {
			pixelCenterPoint = pixelCenterPoint.addVector(vRight.scale(xi));
		}
		if (!Util.isZero(yj)) {
			pixelCenterPoint = pixelCenterPoint.addVector(vUp.scale(-yj));
		}

		// create a list of ray which be our rays beam for colors calculating
		List<Ray> raysBeam = new ArrayList<Ray>();
		double halfXRatio = rx / 2d;
		double halfYRatio = ry / 2d;

		for (int k = 0; k < 81; ++k) {
			Vector shifterVector = vRight.scale(Util.getNotZeroRandom() * halfXRatio)
					.add(vUp.scale(Util.getNotZeroRandom() * halfYRatio));
			Point shiftedPixelPoint = pixelCenterPoint.addVector(shifterVector);
			raysBeam.addAll(
					calcDOFRays(new Ray(p0, shiftedPixelPoint.subtract(p0)), focusLength, apertureSize, rayBeamSize));
		}
		return raysBeam;
	}

	public Pixel constructPixelCorners(int nx, int ny, int j, int i, double screenDistance, double focusLength,
			double apertureSize, double screenWidth, double screenHeight, int rayBeamSize) {
		// Pc is the center point of the view plane: P0 + d*vTo
		Point pc = p0.addVector(vTo.scale(screenDistance));
		// ratio factors: rx is the width of each pixel
		double rx = screenWidth / nx;
		double ry = screenHeight / ny;
		// Xi and Yj are the coefficients that would take us to the asked point from the
		// Pc point
		// Xi for moving in the X axis direction (right / left)
		double xi = ((i - (nx / 2d)) * rx);
		// Yj for moving in the Y axis direction (down / up)
		double yj = ((j - (ny / 2d)) * ry);
		// in case the both coefficients are zero, the asked point is the Pc point
		Point upperLeftPoint = pc;
		if (!Util.isZero(xi)) {
			upperLeftPoint = upperLeftPoint.addVector(vRight.scale(xi));
		}
		if (!Util.isZero(yj)) {
			upperLeftPoint = upperLeftPoint.addVector(vUp.scale(-yj));
		}
		// find 3 more points for corners of the pixel
		Point upperRightPoint = upperLeftPoint.addVector(vRight.scale(rx));
		Point lowerRightPoint = upperRightPoint.addVector(vUp.scale(ry));
		Point lowerLeftPoint = upperLeftPoint.addVector(vUp.scale(ry));
		// build for rays in the corners of the pixel
		List<Ray> aCorner = calcDOFRays(new Ray(p0, upperLeftPoint.subtract(p0)), focusLength, apertureSize,
				rayBeamSize);
		List<Ray> bCorner = calcDOFRays(new Ray(p0, upperRightPoint.subtract(p0)), focusLength, apertureSize,
				rayBeamSize);
		List<Ray> cCorner = calcDOFRays(new Ray(p0, lowerRightPoint.subtract(p0)), focusLength, apertureSize,
				rayBeamSize);
		List<Ray> dCorner = calcDOFRays(new Ray(p0, lowerLeftPoint.subtract(p0)), focusLength, apertureSize,
				rayBeamSize);

		Pixel pixel = new Pixel(upperLeftPoint, aCorner, upperRightPoint, bCorner, lowerRightPoint, cCorner,
				lowerLeftPoint, dCorner, 1);
		return pixel;
	}

	public List<Pixel> dividePixel(Pixel mainPixel, double focusLength, double apertureSize, int rayBeamSize){
		//caculate the width and hight of the pixel
		double pixWidth = mainPixel.aPoint.distance(mainPixel.bPoint);
		double pixHeight = mainPixel.aPoint.distance(mainPixel.dPoint);
		Vector halfWidthRightShifter = vRight.scale(pixWidth / 2d);
		Vector halfHeightDownShifter = vUp.scale(pixHeight / 2d);
		Point abMiddlePoint = mainPixel.aPoint.addVector(halfWidthRightShifter);
		Point dcMiddlePoint = mainPixel.dPoint.addVector(halfWidthRightShifter);
		Point adMiddlePoint = mainPixel.aPoint.addVector(halfHeightDownShifter);
		Point bcMiddlePoint = mainPixel.bPoint.addVector(halfHeightDownShifter);
		Point pixCenterPoint = abMiddlePoint.addVector(halfHeightDownShifter);
		
		List<Ray> abMiddle = calcDOFRays(new Ray(p0, abMiddlePoint.subtract(p0)), focusLength, apertureSize, rayBeamSize);
		List<Ray> dcMiddle = calcDOFRays(new Ray(p0, dcMiddlePoint.subtract(p0)), focusLength, apertureSize, rayBeamSize);
		List<Ray> adMiddle = calcDOFRays(new Ray(p0, adMiddlePoint.subtract(p0)), focusLength, apertureSize, rayBeamSize);
		List<Ray> bcMiddle = calcDOFRays(new Ray(p0, bcMiddlePoint.subtract(p0)), focusLength, apertureSize, rayBeamSize);
		List<Ray> pixCenter = calcDOFRays(new Ray(p0, pixCenterPoint.subtract(p0)), focusLength, apertureSize, rayBeamSize);
		
		int subdivisionRank = mainPixel.getRank() * 4;
		List<Pixel> subPixels = new ArrayList<Pixel>();
		subPixels.add(new Pixel(mainPixel.aPoint, mainPixel.aCornerRays.rayBeam, abMiddlePoint, abMiddle, pixCenterPoint, pixCenter, adMiddlePoint, adMiddle,subdivisionRank));
		subPixels.add(new Pixel(abMiddlePoint, abMiddle, mainPixel.bPoint, mainPixel.bCornerRays.rayBeam, bcMiddlePoint, bcMiddle, pixCenterPoint, pixCenter, subdivisionRank));
		subPixels.add(new Pixel(adMiddlePoint, adMiddle, pixCenterPoint, pixCenter, dcMiddlePoint, dcMiddle, mainPixel.dPoint, mainPixel.dCornerRays.rayBeam, subdivisionRank));
		subPixels.add(new Pixel(pixCenterPoint, pixCenter, bcMiddlePoint, bcMiddle, mainPixel.cPoint, mainPixel.cCornerRays.rayBeam, dcMiddlePoint, dcMiddle,subdivisionRank));
		return subPixels;
	}

	private List<Ray> calcDOFRays(Ray originalRay, double focusLength, double apertureSize, int rayBeamSize) {
		List<Ray> raysBeam = new ArrayList<Ray>();
		if (Util.isZero(apertureSize) || rayBeamSize == 0) {
			raysBeam.add(originalRay);
			return raysBeam;
		}

		Point basePoint = originalRay.getBasePoint();
		// calculate focalPoint by building a plane for focus distance
		Point focalPlaneCenter = basePoint.addVector(vTo.scale(focusLength));
		Plane focalPlane = new Plane(focalPlaneCenter, vTo);
		List<GeoPoint> itersections = focalPlane.findIntersections(originalRay);
		Point focalPoint = itersections.get(0).point;

		// create rays randomly within the range of the aperture size, directed to the
		// focal point
		double halfAperture = apertureSize / 2d;
		for (int count = 0; count < rayBeamSize; count++) {
			Point shiftedPoint = basePoint.addVector(vRight.scale(Util.getNotZeroRandom() * halfAperture));
			shiftedPoint = basePoint.addVector(vUp.scale(Util.getNotZeroRandom() * halfAperture));
			Ray ray = new Ray(shiftedPoint, focalPoint.subtract(shiftedPoint));
			raysBeam.add(ray);
		}
		return raysBeam;
	}
}
