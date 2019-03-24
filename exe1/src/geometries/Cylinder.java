/*
 * Aharon Packter
 * ID 201530508
 * Shlomo Perlov
 * ID 206914301
 * 19/03/2019
 * 
 * Mini project in Software Engineering
 * Exercise 1
 */
package geometries;

import primitives.Ray;

public class Cylinder extends Tube {
	private double height;
	
	// ***************** Constructors ******************** //
	public Cylinder(double radiusParm, Ray centerRayParm, double heightParm) {
		super(radiusParm, centerRayParm);
		height = heightParm;
	}

	// ***************** Getters ******************** //
	public double getHeight() {
		return height;
	}

	// ***************** Administration ******************** //
	@Override
	public String toString() {
		return super.toString() + "\nHeight: " + height;
	}

	// ***************** Operation ******************** //

}
