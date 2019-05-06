/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import primitives.Point;

/**
 * test cases for Point methods
 */
public class PointTest {
	Point p1 = new Point(3,7,0);
	Point p2 = new Point(6,3,0);
	/**
	 * Test method for distance^2 calculating from point to point{@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	public void testDistanceSquared() {
		//from point (3,7,0) to (6,3,0) the distance is 5. and the squared distance is 25
		assertEquals(25d, p1.distanceSquared(p2), 1e-10);
		assertEquals(25d, p2.distanceSquared(p1), 1e-10);
		//from a point to itself the distance is 0
		assertEquals(0, p1.distanceSquared(p1), 1e-10);
	}

	/**
	 * Test method for distance calculating from point to point {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	public void testDistance() {
		//from point (3,7,0) to (6,3,0) the distance is 5.
		assertEquals(5d, p1.distance(p2), 1e-10);
		assertEquals(5d, p2.distance(p1), 1e-10);
		//from a point to itself the distance is 0
		assertEquals(0, p1.distance(p1), 1e-10);
	}

}
