package elements;

import primitives.Color;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

public class SpotLight extends PointLight {
	Vector _dir;

	public SpotLight(Vector direction, Color color, Point pL, double kc, double kl, double kq) {
		super(color, pL, kc, kl, kq);
		_dir = direction;
	}

	@Override
	public Color getIntensity(Point point) {
		Vector l = point.subtract(_pL);
		double dirDotProductL = _dir.DotProduct(l);
		if (dirDotProductL < 0 || Util.isZero(dirDotProductL))
			return Color.BLACK;
		return super.getIntensity(point).scale(dirDotProductL);
	}

	@Override
	public Vector getD(Point point) {
		return _dir;
	}

	@Override
	public Vector getL(Point point) {
		return point.subtract(_pL);
	}
}
