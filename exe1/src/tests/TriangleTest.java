/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.Intersectable.GeoPoint;
import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * test the Triangle operations
 */
public class TriangleTest {
	Point vertexA = new Point(0,0,0);
	Point vertexB = new Point(2,0,0);
	Point vertexC = new Point(0,2,0);
	Triangle triangle = new Triangle(vertexA, vertexB, vertexC);
	Vector sideA = vertexA.subtract(vertexB);
	Point pointInTriangle = new Point(0.5, 0.5, 0);
	
	/**
	 * Test method for a normal vector finding in Triangle {@link geometries.Triangle#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormalPoint() {
		assertEquals(0, (triangle.getNormal(pointInTriangle).DotProduct(sideA)), 1e-10);
	}
	
	/**
	 * Test method for a ray intersection finding in Triangle {@link geometries.Triangle#testFindIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections()
	{
		Vector normalToTriangleVector = new Vector(0,0,1);
		Vector parallelVector = new Vector(1,0,0);
		Point pointBelowTriangle = new Point(0.5 ,0.5 ,-1);
		Point pointAboveTriangle = new Point(0.5 ,0.5 ,1);
		List<GeoPoint> actualInsecPoints;
		
		/**
		 * a case for an orthogonal ray starts above the triangle upwards    
		 * */
		Ray r2 = new Ray(pointAboveTriangle, normalToTriangleVector);
		assertEquals(0, triangle.findIntersections(r2).size());
		
		/**
		 * a case for an orthogonal ray starts right in the triangle upwards
		 */	
		Ray r1 = new Ray(pointInTriangle, normalToTriangleVector);
		actualInsecPoints = triangle.findIntersections(r1);
		assertTrue(actualInsecPoints.size() == 1 && actualInsecPoints.contains(pointInTriangle));
		
		/**
		 * a case for an orthogonal ray starts below the triangle
		 */
		Ray r3 = new Ray(pointBelowTriangle ,normalToTriangleVector);
		actualInsecPoints = triangle.findIntersections(r3);
		assertTrue(actualInsecPoints.size() == 1 && actualInsecPoints.contains(pointInTriangle));
		
		/**
		 * a case for a not-orthogonal ray intersects the triangle
		 */
		Ray r4 = new Ray(new Point(0,0,-0.5),new Vector(0.5,0.5,0.5));
		actualInsecPoints = triangle.findIntersections(r4);
		assertTrue(actualInsecPoints.size() == 1 && actualInsecPoints.contains(new Point(0.5,0.5,0)));
		
		/**
		 * a case that the ray is parallel with the triangle
		 */
		Ray r5 = new Ray(pointAboveTriangle,parallelVector);
		assertTrue(triangle.findIntersections(r5).size() == 0);
		
		/**
		 * a case the ray is included in the plane - should return empty list
		 */
		Ray r6 = new Ray(pointInTriangle,parallelVector);
		assertTrue(triangle.findIntersections(r6).size() == 0);
	}

}

