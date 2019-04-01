/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import geometries.Sphere;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * test the Sphere operations
 */
public class SphereTest {
	Sphere sphere = new Sphere(1, new Point(0, 0, 1));
	Sphere sphere2 = new Sphere(2,  new Point(0,0,0));

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormal() {
		assertEquals(new Vector(0,0,1), sphere.getNormal(new Point(0,0,2)));
	}

	/**
	 * Test method for {@link geometries.Sphere#findIntersections}.
	 */
	@Test
	public void testFindIntersections() {
		//a list with all expected
		 List<Point> insecPoints = new ArrayList<Point>() {{
			 add(new Point(0,-1.93202,0.51699));
			 add(new Point(0,1.46144,1.36536));
		 }};
		Ray ray = new Ray(new Point(0,-4,0), new Vector(0,4,1));
		List<Point> list  = sphere2.findIntersections(ray);
		assertTrue(list.size() == 2);
		assertTrue( list.containsAll(insecPoints));
		
	}

}
