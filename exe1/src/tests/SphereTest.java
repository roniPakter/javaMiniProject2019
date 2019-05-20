/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.Intersectable.GeoPoint;
import geometries.Sphere;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * test the Sphere operations
 */
public class SphereTest {
	Sphere sphere = new Sphere(1, new Point(0, 0, 1));
	Sphere sphere2 = new Sphere(2,  Point.ZERO);

	/**
	 * Test method for a normal vector finding in Sphere {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormal() {
		assertEquals(new Vector(0,0,1), sphere.getNormal(new Point(0,0,2)));
	}

	/**
	 * Test method for a ray intersection finding in Sphere {@link geometries.Sphere#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Vector directionVector = new Vector(0,4,1);
		//expected intersections
		
		Point insec1 = new Point(0,-1.9320241296301,0.5169939675925);
		Point insec2 = new Point(0,1.461435894336,1.365358973584);
		 //a ray intersects the sphere twice
		Point point = new Point(0,-4,0);
		Ray ray = new Ray(point, directionVector);
		List<GeoPoint> actualInsecPoints  = sphere2.findIntersections(ray);
		
		/**
		 * Check a regular two-intersections case
		 */
		assertTrue(actualInsecPoints.size() == 2 && actualInsecPoints.contains(insec1) && actualInsecPoints.contains(insec2));
		
		/**
		 * Check the opposite ray - no intersections
		 */
		Ray oppositeRay = new Ray(new Point(0,-4,0), directionVector.scale(-1));
		actualInsecPoints = sphere2.findIntersections(oppositeRay);
		assertTrue(sphere2.findIntersections(oppositeRay).isEmpty());
		
		/**
		 * Check one-intersection case a ray from within the sphere
		 */
		Ray fromWithinRay = new Ray(new Point(0,0,1), directionVector);
		actualInsecPoints = sphere2.findIntersections(fromWithinRay);
		assertTrue(actualInsecPoints.size() == 1);
		assertTrue(actualInsecPoints.contains(insec2));
		
		/**
		 * Check no-intersection case - the ray in not in the direction of the sphere at all
		 */
		Ray otherDirectionRay = new Ray(new Point(0,0,3), new Vector(0,5,-3));
		assertTrue(sphere2.findIntersections(otherDirectionRay).isEmpty());
		
		/**
		 * Check the case the ray starts on the sphere and goes through it
		 */
		Ray goesThroughRay = new Ray(insec1, directionVector);
		actualInsecPoints = sphere2.findIntersections(goesThroughRay);
		assertTrue(actualInsecPoints.size() == 2);
		assertTrue( actualInsecPoints.contains(insec1));
		assertTrue(actualInsecPoints.contains(insec2));
		
		/**
		 * Check the case the ray starts on the sphere and goes outwards
		 */
		Ray goesOutwardsRay = new Ray(insec2, directionVector);
		actualInsecPoints = sphere2.findIntersections(goesOutwardsRay);
		assertTrue(actualInsecPoints.size() == 1 && actualInsecPoints.contains(insec2));
		
		//Check the case the ray goes through the center point
		directionVector = new Vector(0,1,0);
		Ray throughCenteRay = new Ray(point, directionVector);
		insec1 = new Point(0,-2,0);
		insec2 = new Point(0,2,0);
		actualInsecPoints = sphere2.findIntersections(throughCenteRay);
		/**
		 * the ray starts before the sphere and goes through the center point
		 */
		assertTrue(actualInsecPoints.size() == 2 && actualInsecPoints.contains(insec1) && actualInsecPoints.contains(insec2));
		
		/**
		 * the ray goes through the center point and starts in the sphere
		 */
		throughCenteRay = new Ray(new Point(0,0.5,0), directionVector);
		actualInsecPoints = sphere2.findIntersections(throughCenteRay);
		assertTrue(actualInsecPoints.size() == 1 && actualInsecPoints.contains(insec2));
		
		/**
		 * the ray goes through the center point and starts from the center
		 */
		throughCenteRay = new Ray(Point.ZERO, directionVector);
		actualInsecPoints = sphere2.findIntersections(throughCenteRay);
		assertTrue(actualInsecPoints.size() == 1 && actualInsecPoints.contains(insec2));
		
		/**
		 * the ray start on the sphere outwards and is in the direction of the center
		 */
		throughCenteRay = new Ray(insec2, directionVector);
		actualInsecPoints = sphere2.findIntersections(throughCenteRay);
		assertTrue(actualInsecPoints.size() == 1 && actualInsecPoints.contains(insec2));
		
		/**
		 * the ray starts on the sphere inwards and goes through the center
		 */
		throughCenteRay = new Ray(insec1, directionVector);
		actualInsecPoints = sphere2.findIntersections(throughCenteRay);
		assertTrue(actualInsecPoints.size() == 2 && actualInsecPoints.contains(insec1) && actualInsecPoints.contains(insec2));
		
		/**
		 * the ray doesn't touch the sphere but starts orthogonal to the center point
		 */
		ray = new Ray(point ,new Vector(0,0,1));
		actualInsecPoints = sphere2.findIntersections(throughCenteRay);
		assertTrue(sphere2.findIntersections(ray).isEmpty());
		
		/**
		 * the ray osculates the sphere and starts before it
		 */
		ray = new Ray(new Point(0,-4,2), directionVector);
		Point topPoint = new Point(0,0,2);
		actualInsecPoints = sphere2.findIntersections(ray);
		assertTrue(actualInsecPoints.size() == 1 && actualInsecPoints.contains(topPoint));
		
		/**
		 * the ray osculates the sphere and starts within it
		 */
		ray = new Ray(topPoint, directionVector);
		actualInsecPoints = sphere2.findIntersections(ray);
		assertTrue(actualInsecPoints.size() == 1 && actualInsecPoints.contains(topPoint));
		
		/**
		 * the ray could osculate the sphere but starts after it
		 */
		ray = new Ray(new Point(0,1,2), directionVector);
		actualInsecPoints = sphere2.findIntersections(ray);
		assertTrue(actualInsecPoints.isEmpty());
	}
}
