package elements;
/**
 * represents the default "filling" light for graphic elements
 */

import primitives.Color;

/**
 * represents a light for default state without any light source
 */
public class AmbientLight extends Light {
	
	/** the intensity coefficient of the ambient light*/
	double _kA;
	/** the final color of the light: Ia * Ka*/
	Color _intensity;
	
	// ***************** Constructors ********************** //
	/**
	 * ctor that gets the color of the light and the intensity coefficient. calculates the final color for the ambient light.
	 * @param iA
	 * @param kA
	 */
	public AmbientLight(Color iA, double kA) {
		super(iA);
		_kA = kA;
		_intensity = iA.scale(kA);
	}

	/**
	 * Copy ctor for AmbientLight
	 * @param ambientLight
	 */
	public AmbientLight(AmbientLight ambientLight) {
		 super(ambientLight._i0);
		_intensity = new Color(ambientLight.getIntensity());
	}

	// ***************** Getters/Setters ********************** //
	@Override
	public Color getIntensity() {
		return _intensity;
	}
	// ***************** Administration ********************** //
	
	/**
	 * string representation for the ambient light
	 */
	@Override
	public String toString() {
		return "Ia = " + _i0 + ", Ka = " + _kA + ", Intensity = " + _intensity;
	}

}
