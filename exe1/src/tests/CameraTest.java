package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import elements.Camera;
import geometries.Intersectable;
import geometries.Sphere;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Unit tests for Camera functions
 */
public class CameraTest {

	Camera camera;
	Vector negativeYAxis = Vector.Y_AXIS.scale(-1);
	Vector negativeZAxis = Vector.Z_AXIS.scale(-1);
	
	@Test
	public void testConstructRayThroughPixel() {
		camera = new Camera(Point.ZERO, Vector.Z_AXIS, negativeYAxis);
		
		//tests for a 4X4 view plane with size of 8X8
		Ray actualRay = camera.constructRayThroughPixel(4, 4, 0, 0, 2, 8, 8);
		assertEquals(new Ray(Point.ZERO , new Vector(-3,-3,2)), actualRay);
		actualRay = camera.constructRayThroughPixel(4, 4, 0, 1, 2, 8, 8);
		assertEquals(new Ray(Point.ZERO , new Vector(-1,-3,2)), actualRay);
		actualRay = camera.constructRayThroughPixel(4, 4, 1, 0, 2, 8, 8);
		assertEquals(new Ray(Point.ZERO , new Vector(-3,-1,2)), actualRay);
		actualRay = camera.constructRayThroughPixel(4, 4, 1, 1, 2, 8, 8);
		assertEquals(new Ray(Point.ZERO , new Vector(-1,-1,2)), actualRay);
		
		//tests for a 3X3 view plane with size of 6X6
		actualRay = camera.constructRayThroughPixel(3, 3, 0, 0, 2, 6, 6);
		assertEquals(new Ray(Point.ZERO, new Vector(-2,-2,2)), actualRay);
		actualRay = camera.constructRayThroughPixel(3, 3, 0, 1, 2, 6, 6);
		assertEquals(new Ray(Point.ZERO, new Vector(0,-2,2)), actualRay);
		actualRay = camera.constructRayThroughPixel(3, 3, 1, 0, 2, 6, 6);
		assertEquals(new Ray(Point.ZERO, new Vector(-2,0,2)), actualRay);
		actualRay = camera.constructRayThroughPixel(3, 3, 1, 1, 2, 6, 6);
		assertEquals(new Ray(Point.ZERO, new Vector(0,0,2)), actualRay);
	}
	
	/*
	@Test
	public void testCameraRaysIntersection() {
		camera = new Camera(Point.ZERO, negativeZAxis, negativeYAxis);
		ArrayList<Point> intersectionList = new ArrayList<Point>();
		Sphere sphere = new Sphere(1, new Point(0,0,-3));
		
		for (int i = 0; i < 3; ++i) {
			for(int j = 0; j < 3; ++j) {
				Ray ray = camera.constructRayThroughPixel(3, 3, i, j, 1, 3, 3);
				intersectionList.addAll(sphere.findIntersections(ray));
			}
		}
	}
	
	private ArrayList<Point> findAllIntersectionsFromCamera(Camera camera , int nx, int ny, double screenDistance, double screenWidth, double screenHeight, Intersectable shape){
		return null;
	}
*/
}
