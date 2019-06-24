package primitives;

/**
 * Represents a material the geometric body is made of holds the factors of the
 * light return from the material
 */
public class Material {	
	/**factor for the diffusive light returned by the material*/
	private double _kD;
	/**factor for the specular reflection returned by the material*/
	private double _kS;
	/**factor for the shininess of the material (dealed as integer)*/
	private int _nShininess;
	/**factor for mirror reflection*/
	private double _kR;
	/**factor for transparency*/
	private double _kT;

	public final static Material NULL_MATERIAL = new Material();
//***************** Getters/Setters ******************** //
	/**
	 * default constructor that makes a zero material that influences nothing on the light returning
	 */
	public Material() {
		_kD = 0;
		_kS = 0;
		_nShininess = 0;
		_kR = 0;
		_kT = 0;
	}
	
	/**
	 * Constructor for material with parameters
	 * @param kD - diffusive factor
	 * @param kS - specular reflection factor
	 * @param nShininess - shininess factor as integer
	 */
	public Material(double kD, double kS, int nShininess) {
		_kD = kD;
		_kS = kS;
		_nShininess = nShininess;
		_kR = 0;
		_kT = 0;
	}
	
	/**
	 * Constructor for material with parameters for reflection and refraction
	 * @param kD - diffusive factor
	 * @param kS - specular reflection factor
	 * @param nShininess - shininess factor as integer
	 * @param kR - reflection factor
	 * @param kT - transparency factor
	 */
	public Material(double kD, double kS, int nShininess, double kR, double kT) {
		_kD = kD;
		_kS = kS;
		_nShininess = nShininess;
		_kR = kR;
		_kT = kT;
	}	
//***************** Getters/Setters ******************** //
	/**
	 * get the diffusive factor of the material
	 */
	public double getKd() {
		return _kD;
	}
	
	/**
	 * get the specular reflection factor of the material
	 */
	public double getKs() {
		return _kS;
	}
	
	/**
	 * get the shininess factor of the material
	 */
	public int getNShininess() {
		return _nShininess;
	}

	/**
	 * get the refraction factor of the material
	 */
	public double getKr() {
		return _kR;
	}

	/**
	 * get the transparency factor of the material
	 */
	public double getKt() {
		return _kT;
	}

}
