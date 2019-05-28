package elements;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a point light source such as a bulb 
 */
public class PointLight extends Light implements LightSource{
	/**the position of the pointLight in space*/
	protected Point _pL;
	/**constant factor of the attenuation calculation*/
	protected double _kc;
	/**linear factor of the attenuation calculation*/
	protected double _kl;
	/**squared factor of the attenuation calculation*/
	protected double _kq;
	
	// ***************** Constructors ******************** //
	/**
	 * Constructor for a PointLight with attenuation factors and position
	 * @param color - the I0, the original color in the light source itself
	 * @param pL - the position of the pointLight
	 * @param kc - constant factor
	 * @param kl - linear factor 
	 * @param kq - squared factor
	 */
	public	PointLight(Color color,Point pL, double kc, double kl,double kq){
		super(color);
		_pL = pL;
		_kc = kc;
		_kl = kl;
		_kq = kq;
	}
	
	// ***************** Getters ******************** //
	@Override
	public Color getIntensity(Point point) {
		//Il = I0 / (Kc + Kl * (distance from light to point) + Kq * (distance^2 from ligth to point))
		double distance = point.distance(_pL);
		double denominator = _kc + _kl * distance +_kq * (distance * distance);
		return _i0.scale(1d/denominator);
	}
	
	@Override
	public Vector getL(Point point) {
		//in point light the direction is the exact direction from the light to the point
		return point.subtract(_pL).normalization();
	}
	
	@Override
	public Vector getD(Point point) {
		//point light is not directed to anywhere
		return point.subtract(_pL).normalization();
	}
	
}
