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

	/**
	 * Return I0 - original light intensity color
	 * @return I0
	 */
	public Color getIntensity() {
		return _color;
	}
}
