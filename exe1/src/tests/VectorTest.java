/**
 * 
 */
package tests;

import static org.junit.Assert.*;
import primitives.*;

import org.junit.Test;

/**
 * @author ronip
 *
 */
public class VectorTest {

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	public void testAdd() {
		try {
			Vector point1 = new Vector(3, 3, 3);
			Vector point2 = new Vector(-3, -3, -3);
			Vector testVector = point1.add(point2);
			fail();
		}
		catch (IllegalArgumentException e) {
			assertEquals("Flags = 'ERROR! vector can't be: (0,0,0)'", e.getMessage());
		}
		
	}

	/**
	 * Test method for {@link primitives.Vector#substract(primitives.Vector)}.
	 */
	@Test
	public void testSubstract() {
		try {
			
			Vector point1 = new Vector(3, 3, 3);
			Vector point2 = new Vector(3, 3, 3);
			
			Vector testVector = point1.substract(point2);
			fail();
		}
		catch (IllegalArgumentException e) {
			assertEquals("Flags = 'ERROR! vector can't be: (0,0,0)'", e.getMessage());
		}
	}

	/**
	 * Test method for {@link primitives.Vector#scale(java.lang.Double)}.
	 */
	@Test
	public void testScale() {
		try {
			
			Vector vector1 = new Vector(3, 3, 3);
			assertEquals(vector1.scale(5.0), new Vector(15, 15, 15));
			Vector testVector = vector1.scale(0.0);
			fail();
		}
		catch (IllegalArgumentException e) {
			assertEquals("Flags = 'ERROR! vector can't be: (0,0,0)'", e.getMessage());
		}
	}
	//comment!

	/**
	 * Test method for {@link primitives.Vector#DotProduct(primitives.Vector)}.
	 */
	@Test
	public void testDotProduct() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	public void testCrossProduct() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#norm()}.
	 */
	@Test
	public void testNorm() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#normalization()}.
	 */
	@Test
	public void testNormalization() {
		fail("Not yet implemented");
	}

}
