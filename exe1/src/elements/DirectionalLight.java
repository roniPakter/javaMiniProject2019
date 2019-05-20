package elements;

import primitives.Color;
import primitives.Vector;

public class DirectionalLight extends Light {

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
	public Color getIntensity() {
		return _color;
	}
	
}
