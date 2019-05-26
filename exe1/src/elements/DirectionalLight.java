package elements;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {

	public DirectionalLight(Vector direction,Color color)
	{
		super(color);
		_direction = new Vector(direction);	
	}
	public DirectionalLight(DirectionalLight directionalLight)
	{
		super(directionalLight._color);
		_direction = new Vector(directionalLight._direction);
	}
 	private Vector _direction;
	
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
