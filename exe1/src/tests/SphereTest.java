/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Sphere;
import primitives.Point;
import primitives.Vector;

/**
 * test the Sphere operations
 */
public class SphereTest {
	Sphere sphere = new Sphere(1.0,new Point(0, 0, 0));

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormal() {
		assertEquals(new Vector(1,0,0), sphere.getNormal(new Point(1,0,0)));
	}

}
