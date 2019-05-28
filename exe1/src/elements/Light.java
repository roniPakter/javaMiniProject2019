package elements;

import primitives.Color;

/**
 * Abstract class for every kind of light
 */
public abstract class Light {
	/** the original color and strength of the light*/
	protected Color _i0;

	// ***************** Constructors ******************** //
	/**
	 * Constructor gets the color of the light to be the I0 
	 * @param color
	 */
	public Light(Color color) {
		_i0 = color;
	}

	// ***************** Operations ******************** //
	/**
	 * Return I0 - original light intensity color
	 * @return I0
	 */
	public Color getIntensity() {
		return _i0;
	}
}
