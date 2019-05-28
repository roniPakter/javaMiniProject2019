package elements;

import primitives.Color;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

/**
 * Represents a spot light source (like a bulb directed to some direction)
 */
public class SpotLight extends PointLight {
	/**the direction the spot light is directed to*/
	Vector _dir;
	
	// ***************** Constructors ******************** //
	/**
	 * Constructor for spotLight with attenuation factors, position, and direction
	 * @param direction - the vector the source is directed to
	 * @param color - the original intensity of the light in the source
	 * @param pL - the position of the light source
	 * @param kc - constant factor
	 * @param kl - linear factor 
	 * @param kq - squared factor
	 */
	public SpotLight(Vector direction, Color color, Point pL, double kc, double kl, double kq) {
		super(color, pL, kc, kl, kq);
		_dir = direction.normalization();
	}

	// ***************** Operations ******************** //
	@Override
	public Color getIntensity(Point point) {
		Vector l = point.subtract(_pL).normalization();
		double dirDotProductL = _dir.dotProduct(l);
		if (dirDotProductL < 0 || Util.isZero(dirDotProductL))
			return Color.BLACK;
		return super.getIntensity(point).scale(dirDotProductL);
	}

	@Override
	public Vector getD(Point point) {
		//the spot light has a direction. it is D vector
		return _dir;
	}

	@Override
	public Vector getL(Point point) {
		// L direction is the direction from the light to the point
		return point.subtract(_pL).normalization();
	}
}
