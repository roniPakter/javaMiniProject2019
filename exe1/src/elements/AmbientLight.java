package elements;
/**
 * represents the default "filling" light for graphic elements
 */

import primitives.Color;

public class AmbientLight {
	/** the basic RBG components of the ambient light */
	Color _iA;
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
		_iA = new Color(iA);
		_kA = kA;
		_intensity = iA.scale(kA);
	}

	/**
	 * Copy ctor for AmbientLight
	 * @param ambientLight
	 */
	public AmbientLight(AmbientLight ambientLight) {
		_intensity = new Color(ambientLight.getIntensity());
	}

	// ***************** Getters/Setters ********************** //
	public Color getIntensity() {
		return _intensity;
	}
	// ***************** Administration ********************** //
	
	/**
	 * string representation for the ambient light
	 */
	@Override
	public String toString() {
		return "Ia = " + _iA + ", Ka = " + _kA + ", Intensity = " + _intensity;
	}

}
