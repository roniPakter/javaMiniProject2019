/*
 * Aharon Packter ID 201530508
 * Shlomo Perlov ID 206914301
 * 
 * 19/03/2019 
 * Mini project in Software Engineering
 * Exercise 1
 */
package geometries;

import primitives.Ray;

public class Tube extends RadialGeometries {
	private Ray centerRay;

	// ***************** Constructors ******************** //
	public Tube(double radiusParm, Ray centerRayParm) {
		super(radiusParm);
		centerRay = new Ray(centerRayParm);
	}
	
	// ***************** Getters ******************** //
	public Ray getCenterRay() {
		return centerRay;
	}
	
	// ***************** Administration ******************** //
	@Override
	public String toString() {
		return "Center ray: " + centerRay.toString() + "\nRadius: " + super.toString();
	}

	// ***************** Operations ******************** //
}

