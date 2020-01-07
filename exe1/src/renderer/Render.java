package renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import elements.LightSource;
import elements.Pixel;
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
	/** boolean flag for rendering with or without adaptive sampling */
	boolean _adaptiveSampling;
	/** value for shifting ray */
	private static final double EPS = 0.1;
	/** the maximum amount of reflection rays calculated*/
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	/**threshold for color difference (the percentage is the value / 255) */
	private static final double COLOR_DIFFERENCE_THRESHOLD = 20;
	/** number of pixel rows in the picture*/
	int nX;
	/** number of pixel columns in the picture*/
	int nY;
	/** the distance of the view plane from the lens */
	double screenDistance;
	/** height of the view plane */
	double screenHeight;
	/** width of the view plane */
	double screenWidth;
	/** the focus length of the rendered scene*/
	double focusLength;
	/**size of aperture for focus calculation*/
	double apertureSize;
	/**amount of rays shot for dof calculating*/
	int dofRayBeamSize;
	/** the rendered scene background color */
	Color backgroundColor;
	/**the actual amount of processors in the computer */
	int actualCores;

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
		_adaptiveSampling = false;

		// number of rows
		nX = _imageWriter.getNx();
		// number of columns
		nY = _imageWriter.getNy();
		screenDistance = _scene.getScreenDistance();
		screenHeight = _imageWriter.getHeight();
		screenWidth = _imageWriter.getWidth();
		focusLength = _scene.getFocusLength();
		apertureSize = _scene.getApertureSize();
		dofRayBeamSize = _scene.getdofRayBeamSize();
		backgroundColor = _scene.getColorBackground();
		//get the number of cores in the running machine
		actualCores = Runtime.getRuntime().availableProcessors();
	}

	/**
	 * constructor that gets the image writer and the scene objects and flag for
	 * adaptive sampling
	 * 
	 * @param imageWriter
	 * @param scene
	 * @param adaptiveSampling - implementing supersampling adaptive or not
	 */
	public Render(ImageWriter imageWriter, Scene scene, boolean adaptiveSampling) {
		_imageWriter = imageWriter;
		_scene = scene;
		_adaptiveSampling = adaptiveSampling;
		// number of rows
		nX = _imageWriter.getNx();
		// number of columns
		nY = _imageWriter.getNy();
		screenDistance = _scene.getScreenDistance();
		screenHeight = _imageWriter.getHeight();
		screenWidth = _imageWriter.getWidth();
		focusLength = _scene.getFocusLength();
		apertureSize = _scene.getApertureSize();
		dofRayBeamSize = _scene.getdofRayBeamSize();
		backgroundColor = _scene.getColorBackground();
		//get the number of cores in the running machine
		actualCores = Runtime.getRuntime().availableProcessors();
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
	 * the method writes an image of the scene, for every pixel we calculate the color and write it in a separate thread
	 * @throws InterruptedException 
	 */
	public void renderImage() {
		//create a thread pool sized by the number of processors
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(actualCores);
		//for each [i,j] pixel
		for (int i = 0; i < nX; ++i) {
			for (int j = 0; j < nY; ++j) {
				int x = i;
				int y = j;
				//define the calculation to do for every pixel in a single thread
				Runnable runnable = () ->{
				Color resultColor = _adaptiveSampling ? pixelColorByAdaptiveSampling(x, y)
						: pixelColorByRegularSampling(x, y);
				_imageWriter.writePixel(x, y, resultColor.getColor());
				};
				executor.execute(runnable);
			}
		}
		//after all - finish the thread work
		executor.shutdown();
		try {
		while(!executor.awaitTermination(1, TimeUnit.HOURS));
		} catch (InterruptedException e) {}
	}

	/**
	 * calculate the color of [i,j] pixel in the picture - superSampling not adaptive
	 * @param i
	 * @param j
	 * @return the color to paint the pixel 
	 */
	private Color pixelColorByRegularSampling(int i, int j) {
		// create the rayBeam that goes through the current pixel
		List<Ray> rayBeam = _scene.getCamera().constructRaysThroughPixel(nX, nY, j, i, screenDistance, focusLength,
				apertureSize, screenWidth, screenHeight, dofRayBeamSize);
		return calcRayBeamColor(rayBeam);
	}

	/**
	 * calculate the color of [i,j] pixel in the picture - superSampling is adaptive
	 * @param i
	 * @param j
	 * @return the color to paint the pixel 
	 */
	private Color pixelColorByAdaptiveSampling(int i, int j) {
		Color resultColor = Color.BLACK;
		//create the pixel that is calculated by the camera
		Pixel pixel = _scene.getCamera().constructPixelCorners(nX, nY, j, i, screenDistance, focusLength, apertureSize,
				screenWidth, screenHeight, dofRayBeamSize);
		//save the colors of the pixel corners
		setPixelCornersColors(pixel);
		//in case the colors of the corners are close enough - the result is the average of the corners colors
		if (isSameColor(pixel)) {
			return resultColor.add(pixel.aCornerRays.color, pixel.bCornerRays.color, pixel.cCornerRays.color,
					pixel.dCornerRays.color).reduce(4);
		}
		
		//in case the colors are different - divide the pixel and check the sub pixels
		List<Pixel> pixels = new ArrayList<Pixel>();
		pixels.add(pixel);
		//every time sub pixels are added - the size of the list grows
		for (int k = 0; k < pixels.size(); ++k) {
			Pixel subPixel = pixels.get(k);
			setPixelCornersColors(subPixel);
			//in case the colors of the sub pixel are different and it is not the last level of division - divide it
			if (!isSameColor(subPixel) && subPixel.getRank() < 64)
				pixels.addAll(_scene.getCamera().dividePixel(subPixel, focusLength, apertureSize, dofRayBeamSize));
			//if colors are similar or the pixel is maximum divided - add the color part of the sub pixel
			else {
				Color tmpColor = subPixel.aCornerRays.color.add(subPixel.bCornerRays.color, subPixel.cCornerRays.color,
						subPixel.dCornerRays.color);
				tmpColor = tmpColor.reduce(4);
				//the part contributed to the result is 1 / rank
				resultColor = resultColor.add(tmpColor.reduce(subPixel.getRank()));
			}
		}
		return resultColor;
	}

	/**
	 * method calculates the average color of a rays beam
	 * @param rayBeam - list of rays
	 * @return the average color of the rays
	 */
	private Color calcRayBeamColor(List<Ray> rayBeam) {
		Color resultColor = Color.BLACK;
		// for each ray in the beam - result color is the intersection color or
		// background
		for (Ray ray : rayBeam) {
			GeoPoint closestPoint = findClosestIntersection(ray);
			resultColor = resultColor.add(closestPoint == null ? backgroundColor : calcColor(closestPoint, ray));
		}
		// reduce the result color by 1/numOfRays
		resultColor = resultColor.reduce(rayBeam.size());
		return resultColor;
	}

	/**
	 * calculate and save the color of a rayBeam in a pixel if it is not saved already
	 * @param pixel
	 */
	private void setPixelCornersColors(Pixel pixel) {
		//a corner that has not been checked for its color is null
		if (pixel.aCornerRays.color == null)
			pixel.aCornerRays.color = (calcRayBeamColor(pixel.aCornerRays.rayBeam));
		if (pixel.bCornerRays.color == null)
			pixel.bCornerRays.color = (calcRayBeamColor(pixel.bCornerRays.rayBeam));
		if (pixel.cCornerRays.color == null)
			pixel.cCornerRays.color = (calcRayBeamColor(pixel.cCornerRays.rayBeam));
		if (pixel.dCornerRays.color == null)
			pixel.dCornerRays.color = (calcRayBeamColor(pixel.dCornerRays.rayBeam));

	}

	/**
	 * checks if corners colors of a pixel are close enough to be considered as the same color
	 * @param pixel
	 * @return boolean same or not
	 */
	private boolean isSameColor(Pixel pixel) {
		// check if any corner is different dramatically from it's neighbors
		return (difference(pixel.aCornerRays.color, pixel.bCornerRays.color) < COLOR_DIFFERENCE_THRESHOLD
				&& difference(pixel.aCornerRays.color, pixel.dCornerRays.color) < COLOR_DIFFERENCE_THRESHOLD 
				&& difference(pixel.cCornerRays.color, pixel.bCornerRays.color) < COLOR_DIFFERENCE_THRESHOLD
				&& difference(pixel.cCornerRays.color, pixel.dCornerRays.color) < COLOR_DIFFERENCE_THRESHOLD);
	}

	/**
	 * checks the difference between two colors and returns the difference value
	 * @param color1
	 * @param color2
	 * @return difference value (called the delta in math)
	 */
	private double difference(Color color1, Color color2) {
		//difference of color is (|R1 - R2| + |G1 - G2| + |B1 - B2|) / 255
		double r = Math.abs(color1.getColor().getRed() - color2.getColor().getRed());
		double g = Math.abs(color1.getColor().getGreen() - color2.getColor().getGreen());
		double b = Math.abs(color1.getColor().getBlue() - color2.getColor().getBlue());
		// the value is still related to 255 whole value, the threshold is the fragment of 255 
		return r + g + b;
	}

	/**
	 * calculates the color of a specific ray by the closest intersection point
	 * 
	 * @param closestPoint - the closest geoPoint intersected
	 * @param ray          - the ray exits from the pixel
	 * @return color value for the pixel
	 */
	private Color calcColor(GeoPoint closestPoint, Ray ray) {
		// call the function with the parameters for the recursion
		Color color = calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, 1.0);
		// add the ambient light to the final calculated color
		return color.add(_scene.getAmbientLight().getIntensity());
	}

	/**
	 * calculates the color of a point on the scene with recursion calls for
	 * refraction & reflection
	 * 
	 * @param geoPoint
	 * @param inRay    - the ray for the current call (starts with the original
	 *                 pixel ray)
	 * @param level    - the max number of calls the recursion should run
	 * @return the color of the point without ambient
	 */
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
				double ktr = transparency(l, n, geoPoint);
				if (!Util.isZero(ktr * k)) {
					Color lightIntensity = lightSource.getIntensity(geoPoint.point).scale(ktr);
					color = color.add(calcDiffusive(kd, n, l, lightIntensity),
							calcSpecular(ks, l, n, v, nShininess, lightIntensity));
				}
			}
		}
		// Recursive call for a reflected ray
		double kr = geoPoint.geometry.getMaterial().getKr();
		Ray reflectedRay = constructReflectedRay(n, geoPoint.point, inRay);
		// reflected point is null if there is no intersection
		GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
		if (reflectedPoint != null && kr != 0)
			// calculate the color of points the new rays hit
			color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, k * kr).scale(kr));

		// Recursive call for a refracted ray
		double kt = geoPoint.geometry.getMaterial().getKt();
		Ray refractedRay = constructRefractedRay(geoPoint.point, inRay);
		// refracted point is null if there is no intersection
		GeoPoint refractedPoint = findClosestIntersection(refractedRay);
		if (refractedPoint != null && kt != 0)
			// calculate the color of points the new rays hit
			color = color.add(calcColor(refractedPoint, refractedRay, level - 1, k * kt)).scale(kt);
		return color;
	}

	/***
	 * create the refracted ray from a ray hitting a point
	 * 
	 * @param point
	 * @param ray   - hitting ray
	 * @return a new ray refracted
	 */
	private Ray constructRefractedRay(Point point, Ray ray) {
		return new Ray(point, ray.getVector());
	}

	/**
	 * get the closest point that is intersected by the ray
	 * 
	 * @param intersectingRay
	 * @return the closest intersection geoPoint - if there isn't return null
	 */
	private GeoPoint findClosestIntersection(Ray intersectingRay) {
		// check the intersection with a ray starts a bit out of the original ray
		Vector epsVector = intersectingRay.getVector().scale(EPS);
		Ray ray = new Ray(intersectingRay.getBasePoint().addVector(epsVector), intersectingRay.getVector());
		// get all the intersection points
		List<GeoPoint> intersectList = _scene.getGeometries().findIntersections(ray);
		if (intersectList.isEmpty())
			return null;
		return getClosestPoint(intersectList, intersectingRay.getBasePoint());
	}

	/**
	 * create the reflected ray from a ray hitting a point
	 * 
	 * @param n          - the normal vector to the hited point
	 * @param point
	 * @param hittingRay
	 * @return a new ray reflected by the ray in the point
	 */
	private Ray constructReflectedRay(Vector n, Point point, Ray hittingRay) {
		// in case the hitting ray is parallel to the object in the point
		if (Util.isZero(hittingRay.getVector().dotProduct(n)))
			// the new ray is the same vector from the point
			return new Ray(point, hittingRay.getVector());
		// reflected = v -( 2 * (v * n )* n)
		return new Ray(point, hittingRay.getVector().substract(n.scale(2 * hittingRay.getVector().dotProduct(n))));
	}

	/**
	 * gets a collection of geoPoints and returns the closest geoPoint to the camera
	 * position in the scene
	 * 
	 * @param geopoints list
	 * @param point     - a source point to check all points with
	 * 
	 * @return closest geoPoint of the geoPoints list
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

	/**
	 * return the value of light getting to a specific point, 1 if it is fully
	 * lighted, 0 if it is shaded
	 * 
	 * @param l        - the vector from the light to the point
	 * @param n        - the normal to the point
	 * @param geopoint
	 * @return 1: fully lighted, 0: fully shaded, values between are the strangth of
	 *         the light
	 */
	private double transparency(Vector l, Vector n, GeoPoint geopoint) {
		// from point to light source
		Vector lightDirection = l.scale(-1);
		// take the checking ray a bit out of the object
		Vector epsVector = n.scale(n.dotProduct(lightDirection) > 0 ? EPS : -EPS);
		Point point = geopoint.point.addVector(epsVector);

		Ray lightRay = new Ray(point, lightDirection);
		List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
		// in case there are no objects shading - the ktr remains 1
		double ktr = 1;
		for (GeoPoint gp : intersections)
			// every object shading adds the transparency level of it to the result
			ktr *= gp.geometry.getMaterial().getKt();
		return ktr;
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

	/**
	 * write the rendered image of the scene
	 */
	public void writeToImage() {
		_imageWriter.writeToImage();
	}
}
