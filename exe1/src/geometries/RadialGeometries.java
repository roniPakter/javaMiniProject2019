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

/**
 * a super class for all redials, representing the data of the radius
 */
abstract class RadialGeometries implements Geometry{
	private double radius;

	// ***************** Constructor ******************** //
	public RadialGeometries(double radiusParm) {
		if(radiusParm< 0)
			throw new IllegalArgumentException("ERROR! Radius must be positive");
		radius = radiusParm;
	}

	// ***************** Getter ******************** //
	public double getRadius() {
		return radius;
	}
	
	// ***************** Administration ******************** //
	/**
	 * Returns the radius value formated as a decimal number, two number after the point
	 */
	@Override
	public String toString() {
		return String.format("%.2f",  radius);
	}
	
	/**
	 * Checks if the radial object is with the same radius
	 */
	@Override
	public boolean equals(Object other) {		
		if (other == null || !(other instanceof RadialGeometries))
			return false;
		if (this == other)
			return true;
		RadialGeometries otherRadial = (RadialGeometries)other;
		return radius == otherRadial.getRadius();
	}

}
