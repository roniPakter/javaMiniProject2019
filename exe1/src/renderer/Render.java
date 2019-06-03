package renderer;

import java.util.ArrayList;
import java.util.List;

import elements.LightSource;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import scene.Scene;

/**
 * Represents an integration of all relevant components to create a 3D scene and
 * an image of it
 */
public class Render {
	private static final double MIN_CALC_COLOR_K = 0.001;
	/** ImageWriter object to write the image of the scene */
	ImageWriter _imageWriter;
	/** the scene holds all the data of the 3D model and the camera */
	Scene _scene;
	private static final double EPS = 1.0;
	private static final int MAX_CALC_COLOR_LEVEL = 10;

	// ***************** Constructors ******************** //
	/**
	 * constructor that gets the image writer and the scene objects
	 * 
	 * @param imageWriter
	 * @param scene
	 */
	public Render(ImageWriter imageWriter, Scene scene) {
		_imageWriter = imageWriter;
		_scene = scene;
	}

	// ***************** Administrations ******************** //
	/**
	 * string representation of the renderer
	 */
	@Override
	public String toString() {
		return "Scene:\n" + _scene + "\nImage:\n" + _imageWriter;
	}

	// ***************** Operations ******************** //

	/**
	 * the method writes an image of the scene, for every pixel we calculate the
	 * color and write it
	 */
	public void renderImage() {
		// number of rows
		int nX = _imageWriter.getNx();
		// number of columns
		int nY = _imageWriter.getNy();
		double screenDistance = _scene.getScreenDistance();
		double screenHeight = _imageWriter.getHeight();
		double screenWidth = _imageWriter.getWidth();
		Color backgroundColor = _scene.getColorBackground();

		for (int i = 0; i < nX; ++i) {
			for (int j = 0; j < nY; ++j) {
				// create the ray that goes through the center of the current pixel
				Ray ray = _scene.getCamera().constructRayThroughPixel(nX, nY, j, i, screenDistance, screenWidth,
						screenHeight);
				// get all the intersection points of the ray with the model shapes
				List<GeoPoint> intersectPoints = _scene.getGeometries().findIntersections(ray);
				if (intersectPoints.isEmpty())
					// in case there are no intersections - paint the background color
					_imageWriter.writePixel(i, j, backgroundColor.getColor());
				else {
					// if there are intersections - find the closest point to the camera
					GeoPoint closestPoint = getClosestPoint(intersectPoints, _scene.getCamera().getP0());
					// paint the pixel with the matching color
					_imageWriter.writePixel(i, j, calcColor(closestPoint, ray).getColor());
				}
			}
		}
	}

	private Color calcColor(GeoPoint closestPoint, Ray ray) {
		Color color = calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, 1.0);
		return color.add(_scene.getAmbientLight().getIntensity());
	}

	
	private Color calcColor(GeoPoint geoPoint, Ray inRay, int level, double k) {
		if (level == 0 || k < MIN_CALC_COLOR_K)
			return Color.BLACK;
		// emission of the geometric body is added
		Color color = geoPoint.geometry.getEmission();
		// v is the normalized vector from the camera lens to the point
		Vector v = geoPoint.point.subtract(_scene.getCamera().getP0()).normalization();
		// n is the normal of the geometry at the point
		Vector n = geoPoint.geometry.getNormal(geoPoint.point);
		// the attenuation factors of the material
		int nShininess = geoPoint.geometry.getMaterial().getNShininess();
		double kd = geoPoint.geometry.getMaterial().getKd();
		double ks = geoPoint.geometry.getMaterial().getKs();

		// for each light source we add the color the light is adding to the point
		for (LightSource lightSource : _scene.getLights()) {
			// l is the vector from the light to the point
			Vector l = lightSource.getL(geoPoint.point);
			if (n.dotProduct(l) * n.dotProduct(v) > 0) { // both are with the same sign
				if (unshaded(l, n, geoPoint)) {
					Color lightIntensity = lightSource.getIntensity(geoPoint.point);
					color = color.add(calcDiffusive(kd, n, l, lightIntensity),
							calcSpecular(ks, l, n, v, nShininess, lightIntensity));
				}
			}
		}

		// Recursive call for a reflected ray
		double kr = geoPoint.geometry.getMaterial().getKr();
		Ray reflectedRay = constructReflectedRay(n, geoPoint.point, inRay);
		GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
		if (reflectedPoint != null)
			color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, k * kr).scale(kr));

		// Recursive call for a refracted ray
		double kt = geoPoint.geometry.getMaterial().getKt();
		Ray refractedRay = constructRefractedRay(geoPoint.point, inRay);
		GeoPoint refractedPoint = findClosestIntersection(refractedRay);
		if (refractedPoint != null)
			color = color.add(calcColor(refractedPoint, refractedRay, level - 1, k * kt)).scale(kt);
		return color;
	}

	private Ray constructRefractedRay(Point point, Ray inRay) {
		return new Ray(point, inRay.getVector());
	}

	private GeoPoint findClosestIntersection(Ray reflectedRay) {
		Vector epsVector = reflectedRay.getVector().scale(EPS);
		Ray ray = new Ray(reflectedRay.getBasePoint().addVector(epsVector), reflectedRay.getVector());
		List<GeoPoint> intersectList = _scene.getGeometries().findIntersections(ray);
		if (intersectList.isEmpty())
			return null;
		return getClosestPoint(intersectList, reflectedRay.getBasePoint());

	}

	private Ray constructReflectedRay(Vector n, Point point, Ray inRay) {
		if (Util.isZero(inRay.getVector().dotProduct(n)))
			return new Ray(point, inRay.getVector());
		return new Ray(point, inRay.getVector().substract(n.scale(2 * inRay.getVector().dotProduct(n))));
	}

	/**
	 * gets a collection of geoPoints and returns the closest geoPoint to the camera
	 * position in the scene
	 * 
	 * @param geopoints list
	 * @param point     - a source point to check all points with
	 * @return
	 */
	private GeoPoint getClosestPoint(List<GeoPoint> geoPoints, Point point) {
		// P0 is the point of the camera position
		Point p0 = point;
		// we start with the first geoPoint in the list
		GeoPoint closest = geoPoints.get(0);
		for (GeoPoint current : geoPoints) {
			if (current.point.distance(p0) < closest.point.distance(p0))
				closest = current;
		}
		return closest;
	}

	private boolean unshaded(Vector l, Vector n, GeoPoint geopoint) {

		Vector lightDirection = l.scale(-1); // from point to light source
		Vector epsVector = n.scale(n.dotProduct(lightDirection) > 0 ? EPS : -EPS);
		Point point = geopoint.point.addVector(epsVector);

		Ray lightRay = new Ray(point, lightDirection);
		List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
		return intersections.isEmpty();
	}

	/**
	 * calculates the color contributed to a point by the diffusive light of a light
	 * source
	 * 
	 * @param kD - the diffusive coefficient of the material
	 * @param n  - the normal of the geometric body in the point
	 * @param l  - the vector from the light source to the point
	 * @param iL - the color created by the hit of the light in the point
	 * @return
	 */
	private Color calcDiffusive(double kD, Vector n, Vector l, Color iL) {
		// calculate l*n to check angle between the normal and the light hit direction
		double lDotProductN = l.dotProduct(n);
		// in case the angle is 90: no diffusive color is returned
		if (Util.isZero(lDotProductN))
			return Color.BLACK;
		// take the absolute value of the dot product
		if (lDotProductN < 0)
			lDotProductN = -lDotProductN;
		// result is: Kd * |n*l| * Il
		return iL.scale(kD * lDotProductN);
	}

	/**
	 * calculates the the color contributed to a point by the specular light of a
	 * light source
	 * 
	 * @param kS         - the specularity coefficient of the material
	 * @param l          - the vector from the light source to the point
	 * @param n          - the normal of the geometric body in the point
	 * @param v          - the vector from P0 (the camera lens position) to the
	 *                   point
	 * @param nShininess - the shininess factor of the material (dealing it as
	 *                   integer)
	 * @param iL         - the color created by the hit of the light in the point
	 * @return
	 */
	private Color calcSpecular(double kS, Vector l, Vector n, Vector v, int nShininess, Color iL) {
		// r is the reflective vector of l, reflecting the hit of the light ray
		// r = l - (2 * (l*n)*n)
		Vector r = l.substract(n.scale(l.dotProduct(n) * 2d));
		// we calculate -v*r to check the position of the camera relatively to the point
		double minusVDotProductR = -v.dotProduct(r);
		// in case the dot product is zero or less - no specular light is returned
		minusVDotProductR = Util.alignZero(minusVDotProductR);
		if (minusVDotProductR <= 0)
			return Color.BLACK;

		// the nSininess is the exponent, if it is 0 : (-v*r)^Nshininess = 1
		if (nShininess == 0)
			return iL.scale(kS);
		// the result is Ks * (-v*r)^Nshininess * Il
		return iL.scale(kS * Math.pow(minusVDotProductR, nShininess));
	}

	/**
	 * a method to print a grid on the image after we printed the 3D model
	 * 
	 * @param interval  - the interval of the grid cells
	 * @param gridColor - color for the grid
	 */
	public void printGrid(int interval, Color gridColor) {
		// to check if the current row is a row of the grid
		int pixelCheckRows = interval - 1;

		for (int i = 0; i < _imageWriter.getNx(); i++) {
			if (i == pixelCheckRows) {
				// in case we are in a grid row iteration - paint the whole row
				for (int j = 0; j < _imageWriter.getNy(); j++)
					_imageWriter.writePixel(i, j, gridColor.getColor());
				// move the next row check one interval forward
				pixelCheckRows += interval;
			} else {
				// in case we are not in a painted row iteration - we check the columns
				int pixelCheckColumns = interval - 1;
				for (int j = 0; j < _imageWriter.getNy(); j++) {
					if (j == pixelCheckColumns) {
						// in case its a column that should be painted - paint the pixel
						_imageWriter.writePixel(i, j, gridColor.getColor());
						// and move the column check value one interval forward
						pixelCheckColumns += interval;
					}
				}
			}
		}
	}

	public void writeToImage() {
		_imageWriter.writeToImage();
	}
}
