/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import javax.print.attribute.standard.Sides;

import org.junit.Test;

import geometries.Triangle;
import primitives.Point;
import primitives.Vector;

/**
 * test the Triangle operations
 */
public class TriangleTest {
	Triangle triangle = new Triangle(new Point(0,0,0), new Point(1,0,0), new Point(0,1,0));
	Vector side = triangle.getA().subtract(triangle.getB());

	/**
	 * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormalPoint() {
		assertEquals(0.0, (triangle.getNormal(new Point(0.5, 0.5, 0)).DotProduct(side)), 1e-10);
	}

}
