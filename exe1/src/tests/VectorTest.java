/*
 * Aharon Packter ID 201530508
 * Shlomo Perlov ID 206914301
 * 25/03/2019
 * 
 * Mini project in Software Engineering
 * Exercise 2
 */
package tests;

import static org.junit.Assert.*;
import primitives.*;

import org.junit.Test;

/**
 * test the Vector class operations
 */
public class VectorTest
{
	Vector vector1 = new Vector(3, 3, 3);
	Vector vector2 = new Vector(1, 2, 3);
	Vector vector3 = new Vector(-3,-3,-3);
	Vector testVector;
	
	/**
	 * Test method for the zero vector exception{@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	public void testZeroVector() {
		try {
			new Vector(0,0,0);	
			fail();
		}
		catch (IllegalArgumentException e)
		{
			//Check if the exception is the zero vector exception
			assertEquals("Flags = 'ERROR! vector can't be: (0,0,0)'", e.getMessage());
		}
		
	}
	
	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	public void testAdd() {	
		assertEquals(new Vector(4,5,6),vector1.add(vector2));
		try 
		{
			vector1.add(vector3);
			fail();
		} catch (IllegalArgumentException e) {
			//Check if the exception is the zero vector exception
			assertEquals("Flags = 'ERROR! vector can't be: (0,0,0)'", e.getMessage());
		}
	}
	

	/**
	 * Test method for {@link primitives.Vector#substract(primitives.Vector)}.
	 */
	@Test
	public void testSubstract() {
		assertEquals(new Vector(2,1,0), vector1.substract(vector2));
		try 
		{
			vector1.substract(vector1);
			fail();
		} catch (IllegalArgumentException e) {
			//Check if the exception is the zero vector exception
			assertEquals("Flags = 'ERROR! vector can't be: (0,0,0)'", e.getMessage());
		}
	}

	/**
	 * Test method for {@link primitives.Vector#scale(java.lang.Double)}.
	 */
	@Test
	public void testScale() {
		assertEquals(new Vector(36, 36, 36) ,vector1.scale(12.0));
		try {					
			vector1.scale(0.0);
			fail();
		}
		catch (IllegalArgumentException e) {
			//Check if the exception is the zero vector exception
			assertEquals("Flags = 'ERROR! vector can't be: (0,0,0)'", e.getMessage());
		}
	}

	/**
	 * Test method for {@link primitives.Vector#DotProduct(primitives.Vector)}.
	 */
	@Test
	public void testDotProduct() {
		assertEquals(0.0, vector1.DotProduct(new Vector(3, 0, -3)), 1e-10);
		assertEquals(18.0 ,vector1.DotProduct(vector2), 1e-10);
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	public void testCrossProduct() {
		try {
			assertEquals(vector1.crossProduct(vector2), new Vector(3, -6, 3));
			vector1.crossProduct(vector1);
			fail();
		}
		catch(IllegalArgumentException e) {
			//Check if the exception is the zero vector exception
			assertEquals("Flags = 'ERROR! vector can't be: (0,0,0)'", e.getMessage());
		}	
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	public void testNorm() {
		assertEquals(Math.sqrt(27), vector1.length(), 1e-10);
		
	}

	/**
	 * Test method for {@link primitives.Vector#normalization()}.
	 */
	@Test
	public void testNormalization() {
		assertEquals(1, vector1.normalization().length(), 1e-10);
	}

}
