package elements;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
	
	public 	PointLight(Color color,Point pL, double kc, double kl,double kq){
		super(color);
		_pL = pL;
		_kc = kc;
		_kl = kl;
		_kq = kq;
	}
	
	protected Point _pL;
	protected double _kc;
	protected double _kl;
	protected double _kq;
	
	@Override
	public Color getIntensity(Point point) {
		double denominator = _kc + _kl*point.distance(_pL) +_kq*point.distanceSquared(_pL);
		return _color.scale(1d/denominator);
	}
	
	@Override
	public Vector getL(Point point) {
		return point.subtract(_pL);
	}
	
	@Override
	public Vector getD(Point point) {
		return point.subtract(_pL);
	}
	
}
