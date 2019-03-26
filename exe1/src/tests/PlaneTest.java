
package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import geometries.*;
import primitives.Point;
import primitives.Vector;
/**
 * test the Plane operations
 */
public class PlaneTest {

	Plane p1 = new Plane(new Point(1,0,1),new Vector(0,1,0));
	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormalPoint() {
		assertEquals(p1.getNormal().DotProduct(new Vector(p1.getPoint())),0, 1e-10);
		assertEquals(p1.getNormal().norm(), 1, 1e-10);
				
	}

}
