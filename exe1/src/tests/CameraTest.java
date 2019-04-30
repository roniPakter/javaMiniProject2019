package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import elements.Camera;
import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Unit tests for Camera functions
 */
public class CameraTest {

	Camera camera;
	Vector negativeZAxis = Vector.Z_AXIS.scale(-1);

	@Test
	public void testConstructRayThroughPixel() {
		camera = new Camera(Point.ZERO, Vector.Z_AXIS, Vector.NEGATIVE_Y_AXIS);

		// tests for a 4X4 view plane with size of 8X8
		Ray actualRay = camera.constructRayThroughPixel(4, 4, 0, 0, 2, 8, 8);
		assertEquals(new Ray(Point.ZERO, new Vector(-3, -3, 2)), actualRay);
		actualRay = camera.constructRayThroughPixel(4, 4, 0, 1, 2, 8, 8);
		assertEquals(new Ray(Point.ZERO, new Vector(-1, -3, 2)), actualRay);
		actualRay = camera.constructRayThroughPixel(4, 4, 1, 0, 2, 8, 8);
		assertEquals(new Ray(Point.ZERO, new Vector(-3, -1, 2)), actualRay);
		actualRay = camera.constructRayThroughPixel(4, 4, 1, 1, 2, 8, 8);
		assertEquals(new Ray(Point.ZERO, new Vector(-1, -1, 2)), actualRay);

		// tests for a 3X3 view plane with size of 6X6
		actualRay = camera.constructRayThroughPixel(3, 3, 0, 0, 2, 6, 6);
		assertEquals(new Ray(Point.ZERO, new Vector(-2, -2, 2)), actualRay);
		actualRay = camera.constructRayThroughPixel(3, 3, 0, 1, 2, 6, 6);
		assertEquals(new Ray(Point.ZERO, new Vector(0, -2, 2)), actualRay);
		actualRay = camera.constructRayThroughPixel(3, 3, 1, 0, 2, 6, 6);
		assertEquals(new Ray(Point.ZERO, new Vector(-2, 0, 2)), actualRay);
		actualRay = camera.constructRayThroughPixel(3, 3, 1, 1, 2, 6, 6);
		assertEquals(new Ray(Point.ZERO, new Vector(0, 0, 2)), actualRay);

	}

	/* camera = new Camera(Point.ZERO, negativeZAxis, negativeYAxis); */
	@Test
	public void testCameraRaysIntersection() {
		// tests for a Geometric shapes
		Camera camera = new Camera(new Point(0,0,0.5), negativeZAxis, Vector.NEGATIVE_Y_AXIS);
		assertTrue(countAllIntersectionsFromCamera(camera, 3, 3, 1, 3, 3, new Sphere(1, new Point(0, 0, -3))) == 2);
		assertTrue(countAllIntersectionsFromCamera(camera, 3, 3, 1, 3, 3, new Sphere(2.5, new Point(0, 0, -2.5))) == 18);
		assertTrue(countAllIntersectionsFromCamera(camera, 3, 3, 1, 3, 3, new Sphere(2, new Point(0, 0, -2))) == 10);
		assertTrue(countAllIntersectionsFromCamera(camera, 3, 3, 1, 3, 3, new Sphere(10, new Point(0, 0, -2))) == 9);
		assertTrue(countAllIntersectionsFromCamera(camera, 3, 3, 1, 3, 3, new Sphere(0.5, new Point(0, 0, 3))) == 0);
		assertTrue(countAllIntersectionsFromCamera(camera, 3, 3, 1, 3, 3, new Plane(new Point(0,0,-2), Vector.Z_AXIS)) == 9);
		assertTrue(countAllIntersectionsFromCamera(camera, 3, 3, 1, 3, 3, new Triangle(new Point(1,1,-2),new Point(-1,1,-2),new Point(0,-1,-2))) == 1);
		assertTrue(countAllIntersectionsFromCamera(camera, 3, 3, 1, 3, 3, new Triangle(new Point(1,1,-2),new Point(-1,1,-2),new Point(0,-20,-2))) == 2);
		assertTrue(countAllIntersectionsFromCamera(camera, 3, 3, 1, 3, 3,new Plane(new Point(0,-1,-1.75),new Vector(0,-1,0.5))) == 6);
		assertTrue(countAllIntersectionsFromCamera(camera, 3, 3, 1, 3, 3,new Plane(new Point(0,0,-0.5),new Vector(0,-5,10))) == 9);

	}

	/**
	 * counts the intersection points of all rays from given camera and view-plane with a given intersectable shape 
	 * @param camera
	 * @param nx the number of rows
	 * @param ny the number of columns
	 * @param screenDistance distance of the view-plane from the camera
	 * @param screenWidth 
	 * @param screenHeight
	 * @param shape
	 * @return
	 */
	private int countAllIntersectionsFromCamera(Camera camera, int nx, int ny, double screenDistance, double screenWidth, double screenHeight, Intersectable shape) {
		ArrayList<Point> intersectionList = new ArrayList<Point>();
		for (int i = 0; i < nx; ++i) {
			for (int j = 0; j < ny; ++j) {
				Ray ray = camera.constructRayThroughPixel(nx, ny, i, j, screenDistance, screenWidth, screenHeight);
				intersectionList.addAll(shape.findIntersections(ray));
			}
		}
		return intersectionList.size();
	}
}
