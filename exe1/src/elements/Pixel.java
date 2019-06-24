package elements;

import java.util.List;
import primitives.Point;
import primitives.Ray;

public class Pixel {
	public Point aPoint;
	public PixelRay aCornerRays;
	public Point bPoint;
	public PixelRay bCornerRays;
	public Point cPoint;
	public PixelRay cCornerRays;
	public Point dPoint;
	public PixelRay dCornerRays;
	int _rank;
	// ***************** Constructor ******************** //
	
	public Pixel(Point pointA, List<Ray> a,Point pointB, List<Ray> b, Point pointC, List<Ray> c, Point pointD, List<Ray> d, int PixelRank) {
		aPoint = pointA;
		aCornerRays = new PixelRay(a);
		bPoint = pointB;
		bCornerRays = new PixelRay(b);
		cPoint = pointC;
		cCornerRays = new PixelRay(c);
		dPoint = pointD;
		dCornerRays = new PixelRay(d);
		_rank = PixelRank;
	}
	// ***************** Getters ******************** //
	public int getRank() {
		return _rank;
	}
	
	// ***************** Operations ******************** //
}
