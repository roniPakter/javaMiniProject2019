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

import java.nio.channels.SelectableChannel;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube implements Geometry{
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
	@Override
	public Vector getNormal(Point p) {
		if (p.subtract(super.getCenterRay().getBasePoint()).DotProduct(
				super.getCenterRay().getDirectionVector()) == 0)
			return new Vector(super.getCenterRay().getDirectionVector());
		else if (p.subtract((super.getCenterRay().getDirectionVector().scale(height)).getDirection())
				.DotProduct(super.getCenterRay().getDirectionVector()) == 0)
			return super.getCenterRay().getDirectionVector().scale(-1.0);
		else return super.getNormal(p);
					
	}

}
