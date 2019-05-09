package renderer;

import java.util.List;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

public class Render {
	ImageWriter _imageWriter;
	Scene _scene;

	// ***************** Constructors ******************** //
	public Render(ImageWriter imageWriter, Scene scene) {
		_imageWriter = imageWriter;
		_scene = scene;
	}

	// ***************** Getters ******************** //

	// ***************** Administrations ******************** //
	/**
	 * string representation of the renderer
	 */
	@Override
	public String toString() {
		return "Scene:\n" + _scene + "\nImage:\n" + _imageWriter;
	}

	// ***************** Operations ******************** //
	public void renderImage() {
		int nX = _imageWriter.getNx();
		int nY = _imageWriter.getNy();
		double screenDistance = _scene.getScreenDistance();
		double screenHeight = _imageWriter.getHeight();
		double screenWidth = _imageWriter.getWidth();
		Color backgroundColor = _scene.getColorBackground();
		
		for (int i =0; i < nX; ++i) {
			for (int j = 0; j < nY; ++j) {
				Ray ray = _scene.getCamera().constructRayThroughPixel(nX, nY, i, j, screenDistance, screenWidth, screenHeight);
				List<Point> intersectPoints = _scene.getGeometries().findIntersections(ray);
				if (intersectPoints.isEmpty())
					_imageWriter.writePixel(i, j, backgroundColor.getColor());
				else {
					Point closestPoint = getClosestPoint(intersectPoints);
					_imageWriter.writePixel(i, j, calcColor(closestPoint).getColor());
				}
			}
		}
	}

	public Color calcColor(Point p) {
		return _scene.getAmbientLight().getIntensity();
	}

	public Point getClosestPoint(List<Point> points) {
		Point p0 = _scene.getCamera().getP0();
		Point temp = points.get(0);
		for (Point i : points) {
			if (i.distance(p0) < temp.distance(p0))
				temp = i;
		}
		return new Point(temp);
	}

	public void printGrid(int interval, Color gridColor) {

		int pixelCheckRows = interval - 1;

		for (int i = 0; i < 500; i++) {
			if (i == pixelCheckRows) {
				for (int j = 0; j < _imageWriter.getNx(); j++)
					_imageWriter.writePixel(i, j, gridColor.getColor());
				pixelCheckRows += interval;
			} else {
				int pixelCheckColumns = interval - 1;
				for (int j = 0; j < _imageWriter.getNy(); j++) {
					if (j == pixelCheckColumns) {
						_imageWriter.writePixel(i, j, gridColor.getColor());
						pixelCheckColumns += interval;
					}
				}
			}
		}
		_imageWriter.writeToImage();
	}
}
