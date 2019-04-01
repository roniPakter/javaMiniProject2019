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
	//a cylinder for checking
	Cylinder cylinder = new Cylinder(1.0, 
			new Ray(new Point(0,0,0), new Vector(0,0,1)),
			3.0);
	//points to check their normal on the cylinder body
	Point point = new Point(1,1,1);
	//the expected normal from "point"
	Vector expectedNormal = new Vector(1/Math.sqrt(2), 1/Math.sqrt(2), 0);
	Point pointOnUpperBase = new Point(0.5, 0.5, 3);
	Point pointOnLowerBase = new Point(0.5, 0.5, 0);

	/**
	 * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormal() {
		//check the normal from a point on the body
		assertEquals(expectedNormal, cylinder.getNormal(point));
		//check the normal from the upper base
		assertEquals(new Vector(0,0,1), cylinder.getNormal(pointOnUpperBase));
		//check the normal from the bottom base
		assertEquals(new Vector(0,0,-1), cylinder.getNormal(pointOnLowerBase));
	}

}
