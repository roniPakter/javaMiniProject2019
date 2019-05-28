
package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import geometries.*;
import geometries.Intersectable.GeoPoint;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
/**
 * test the Plane operations
 */
public class PlaneTest {
	Point pointInPlane = new Point(1,1,0);
	Vector planeNormal = new Vector(0,0,1);
	//a plane for testing = the X,Y plane
	Plane p1 = new Plane(pointInPlane ,planeNormal);
	
	/**
	 * Test method for a normal vector finding in Plane {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormalPoint()
	{
		//check the normal length is 1
		assertEquals(1, p1.getNormal().length(), 1e-10);	
		//check the normal dot product with a vector included in the plane is zero
		assertEquals(0 ,p1.getNormal().dotProduct(new Vector(pointInPlane)) , 1e-10);
	}
	/**
	 * Test method for a ray intersection finding in Plane {@link geometries.Plane#testFindIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections()
	{
		Vector parallelVector = new Vector(1,0,0);
		GeoPoint pointBelowPlane = new GeoPoint(new Point(0,0,-1), p1);
		GeoPoint pointAbovePlane = new GeoPoint(new Point(1,1,1), p1);
		List<GeoPoint> actualInsecPoints;
		
		/**
		 * a case for an orthogonal ray starts above the plane upwards    
		 * */
		Ray r1 = new Ray(pointAbovePlane.point,planeNormal);
		actualInsecPoints = p1.findIntersections(r1);
		assertEquals(0 ,p1.findIntersections(r1).size());
		
		/**
		 * a case for an orthogonal ray starts right in the plane upwards
		 */	
		Ray r2 = new Ray(pointInPlane, planeNormal);
		actualInsecPoints = p1.findIntersections(r2);
		assertTrue(actualInsecPoints.size() == 1 && actualInsecPoints.contains(new GeoPoint(pointInPlane, p1)));
		
		/**
		 * a case for a not-orthogonal ray intersects the plane
		 */
		Ray r3 = new Ray(pointBelowPlane.point,new Vector(1,1,1));
		actualInsecPoints = p1.findIntersections(r3);
		assertTrue(actualInsecPoints.size() == 1 && actualInsecPoints.contains(new GeoPoint(new Point(1,1,0), p1)));
		
		/**
		 * a case for an orthogonal ray starts below the plane
		 */
		Ray r4 = new Ray(pointBelowPlane.point, planeNormal);
		actualInsecPoints = p1.findIntersections(r4);
		assertTrue(actualInsecPoints.size() == 1 && actualInsecPoints.contains(new GeoPoint(Point.ZERO, p1)));
		
		/**
		 * a case that the ray is parallel with the plane
		 */
		Ray r5 = new Ray(pointAbovePlane.point, parallelVector);
		assertEquals(p1.findIntersections(r5).size(), 0);
		 
		/**
		 * a case the ray is included in the plane - should return empty list
		 */
		Ray r6 = new Ray(pointInPlane,parallelVector);	
		assertEquals(p1.findIntersections(r6).size(), 0);
		
	}	
}
