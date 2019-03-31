/**
 * 
 */
package tests;
import geometries.*;
import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Cylinder;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * test the Cylinder operations
 */
public class CylinderTest {
	Cylinder cylinder = new Cylinder(1.0, 
			new Ray(new Point(0,0,0), new Vector(0,0,1)),
			3.0);
	Point point = new Point(1,1,1);
	Point pointOnUpperBase = new Point(0.5, 0.5, 3);
	Point pointOnLowerBase = new Point(0.5, 0.5, 0);

	/**
	 * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormal() {
		new Vector(1/Math.sqrt(2), 1/Math.sqrt(2), 0);
		assertEquals(new Vector(1/Math.sqrt(2), 1/Math.sqrt(2), 0), cylinder.getNormal(point));
		assertEquals(new Vector(0,0,1), cylinder.getNormal(pointOnUpperBase));
		assertEquals(new Vector(0,0,-1), cylinder.getNormal(pointOnLowerBase));
	}

}
