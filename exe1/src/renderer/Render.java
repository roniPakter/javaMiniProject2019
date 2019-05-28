package renderer;

import java.util.List;

import elements.LightSource;
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
	/** ImageWriter object to write the image of the scene */
	ImageWriter _imageWriter;
	/** the scene holds all the data of the 3D model and the camera */
	Scene _scene;

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
					GeoPoint closestPoint = getClosestPoint(intersectPoints);
					// paint the pixel with the matching color
					_imageWriter.writePixel(i, j, calcColor(closestPoint).getColor());
				}
			}
		}
	}

	/**
	 * calculates the color of a specific point on a geometric model
	 * @param geoPoint of the point and the shape owns it
	 * @return the color to paint the point
	 */
	private Color calcColor(GeoPoint geoPoint) {
		//ambient light is added to the color
		Color color = _scene.getAmbientLight().getIntensity();
		//emission of the geometric body is added
		color = color.add(geoPoint.geometry.getEmission());
		//v is the normalized vector from the camera lens to the point
		Vector v = geoPoint.point.subtract(_scene.getCamera().getP0()).normalization();
		//n is the normal of the geometry at the point
		Vector n = geoPoint.geometry.getNormal(geoPoint.point);
		//the attenuation factors of the material
		int nShininess = geoPoint.geometry.getMaterial().getNShininess();
		double kd = geoPoint.geometry.getMaterial().getKd();
		double ks = geoPoint.geometry.getMaterial().getKs();
		
		//for each light source we add the color the light is adding to the point
		for (LightSource lightSource : _scene.getLights()) {
			//l is the vector from the light to the point
			Vector l = lightSource.getL(geoPoint.point);
			if (n.dotProduct(l) * n.dotProduct(v) > 0) { // both are with the same sign
				Color lightIntensity = lightSource.getIntensity(geoPoint.point);
				color = color.add(calcDiffusive(kd, n , l, lightIntensity),
						calcSpecular(ks, l, n, v, nShininess, lightIntensity));
			}
		}
		return color;
	}

	/**
	 * gets a collection of geoPoints and returns the closest geoPoint to the camera
	 * position in the scene
	 * 
	 * @param points
	 * @return
	 */
	private GeoPoint getClosestPoint(List<GeoPoint> geoPoints) {
		// P0 is the point of the camera position
		Point p0 = _scene.getCamera().getP0();
		// we start with the first geoPoint in the list
		GeoPoint closest = geoPoints.get(0);
		for (GeoPoint current : geoPoints) {
			if (current.point.distance(p0) < closest.point.distance(p0))
				closest = current;
		}
		return closest;
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
	 * @param kS - the specularity coefficient of the material
	 * @param l - the vector from the light source to the point
	 * @param n - the normal of the geometric body in the point
	 * @param v - the vector from P0 (the camera lens position) to the
	 *                   point
	 * @param nShininess - the shininess factor of the material (dealing it as
	 *                   integer)
	 * @param iL - the color created by the hit of the light in the point
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
