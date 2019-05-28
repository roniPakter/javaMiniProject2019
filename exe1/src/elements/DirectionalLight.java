package elements;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a directional light source (such as the sun)
 */
public class DirectionalLight extends Light implements LightSource {
 	private Vector _direction;
	
 // ***************** Constructors ******************** /
 	/**
 	 * Constructor for directional light with direction and color
 	 * @param direction
 	 * @param color
 	 */
	public DirectionalLight(Vector direction,Color color)
	{
		super(color);
		_direction = direction.normalization();	
	}
	
	// ***************** Operations ******************** /
	@Override
	public Color getIntensity(Point point) {
		return super.getIntensity();
	}
	
	@Override
	public Vector getL(Point point) {
		return _direction;
	}
	
	@Override
	public Vector getD(Point point) {
		return _direction;
	}
	
}
