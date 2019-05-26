package renderer;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import scene.Scene;

/**
 *  Represents an integration of all relevant components to create a 3D scene and an image of it
 */
public class Render {
	/** ImageWriter object to write the image of the scene*/
	ImageWriter _imageWriter;
	/** the scene holds all the data of the 3D model and the camera*/
	Scene _scene;

	// ***************** Constructors ******************** //
	/**
	 * constructor that gets the image writer and the scene objects
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
	 * the method writes an image of the scene, for every pixel we calculate the color and write it
	 */
	public void renderImage() {
		//number of rows
		int nX = _imageWriter.getNx();
		//number of columns
		int nY = _imageWriter.getNy();
		double screenDistance = _scene.getScreenDistance();
		double screenHeight = _imageWriter.getHeight();
		double screenWidth = _imageWriter.getWidth();
		Color backgroundColor = _scene.getColorBackground();

		for (int i = 0; i < nX; ++i) {
			for (int j = 0; j < nY; ++j) {
				//create the ray that goes through the center of the current pixel
				Ray ray = _scene.getCamera().constructRayThroughPixel(nX, nY, j, i, screenDistance, screenWidth,
						screenHeight);
				//get all the intersection points of the ray with the model shapes
				List<GeoPoint> intersectPoints = _scene.getGeometries().findIntersections(ray);
				if (intersectPoints.isEmpty())
					//in case there are no intersections - paint the background color
					_imageWriter.writePixel(i, j, backgroundColor.getColor());
				else {
					//if there are intersections - find the closest point to the camera
					GeoPoint closestPoint = getClosestPoint(intersectPoints);
					//paint the pixel with the matching color
					_imageWriter.writePixel(i, j, calcColor(closestPoint).getColor());
				}
			}
		}	
	}

	/**
	 * calculates the color of a specific point on a geometric model
	 * @param point
	 * @return
	 */
	private Color calcColor(GeoPoint geoPoint) {
		Color color = _scene.getAmbientLight().getIntensity();
		color = color.add(geoPoint.geometry.getEmission());
		//return the emission color of the geometric body occupies the point
		return color;
	}

	/**
	 * gets a collection of geoPoints and returns the closest geoPoint to the camera position in the scene 
	 * @param points
	 * @return
	 */
	private GeoPoint getClosestPoint(List<GeoPoint> geoPoints) {
		//P0 is the point of the camera position
		Point p0 = _scene.getCamera().getP0();
		//we start with the first geoPoint in the list
		GeoPoint closest = geoPoints.get(0);
		for (GeoPoint current : geoPoints) {
			if (current.point.distance(p0) < closest.point.distance(p0))
				closest = current;
		}
		return closest;
	}
	
	private Color calcDiffusive(double kD, Vector n, Vector l, Color iL) {
		double lDotProductN = l.DotProduct(n);
		if (Util.isZero(lDotProductN))
			return Color.BLACK;
		if (lDotProductN < 0) 
			lDotProductN *= -1d;
		return iL.scale(kD * lDotProductN);
	}
	
	private Color calcSpecular(double kS, Vector l, Vector n, Vector v, int nShininess, Color iL) {
		Vector r = l.substract(n.scale(l.DotProduct(n) * 2d));
		double minusVDotProductR = v.scale(-1).DotProduct(r);
		if (minusVDotProductR < 0 || Util.isZero(minusVDotProductR))
			return Color.BLACK;
		
		if(nShininess == 0 )
			return iL.scale(kS);
		
		nShininess -=1;
		while(nShininess != 0) {
			minusVDotProductR *=minusVDotProductR;
			nShininess--;
		}
		return iL.scale(kS * minusVDotProductR);
	}

	/**
	 * a method to print a grid on the image after we printed the 3D model
	 * @param interval - the interval of the grid cells 
	 * @param gridColor - color for the grid
	 */
	public void printGrid(int interval, Color gridColor) {
		//to check if the current row is a row of the grid
		int pixelCheckRows = interval - 1;

		for (int i = 0; i < _imageWriter.getNx(); i++) {
			if (i == pixelCheckRows) {
				//in case we are in a grid row iteration - paint the whole row
				for (int j = 0; j < _imageWriter.getNy(); j++)
					_imageWriter.writePixel(i, j, gridColor.getColor());
				//move the next row check one interval forward
				pixelCheckRows += interval;
			} else {
				//in case we are not in a painted row iteration - we check the columns
				int pixelCheckColumns = interval - 1;
				for (int j = 0; j < _imageWriter.getNy(); j++) {
					if (j == pixelCheckColumns) {
						//in case its a column that should be painted - paint the pixel
						_imageWriter.writePixel(i, j, gridColor.getColor());
						//and move the column check value one interval forward
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
