package elements;

import primitives.Color;
import primitives.Point;

public class PointLight extends Light{
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
	public Color getIntensity() {
	_color.scale()
		return null;
	} 
	
}
