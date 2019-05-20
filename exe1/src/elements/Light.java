package elements;

import primitives.Color;

public abstract class Light {
	protected Color _color;
	public Light() {
		_color = Color.WHITE;
	}
	public Light(Color color) {
		_color = color;
	}
 public abstract Color getIntensity(); 
}
