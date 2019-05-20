package geometries;

import java.util.ArrayList;
import java.util.List;
import primitives.Point;
import primitives.Ray;

/**
 * represents all kinds of geometry bodies (one or a group)
 */
public interface Intersectable {
	/**
	 * inner class representing a point and the geometric shape owns it
	 */
	public static class GeoPoint{
		public Geometry geometry;
		public Point point;
		/**
		 * constructor for a geoPoint
		 * @param parmPoint
		 * @param parmGeometry
		 */
		public GeoPoint(Point parmPoint, Geometry parmGeometry){
			point = parmPoint;
			geometry = parmGeometry;
		}
		
		// ***************** Administration ******************** //
		@Override
		public boolean equals(Object other) {
			if (this == other)
				return true;
			if (other == null || !(other instanceof GeoPoint))
				return false;
			GeoPoint temPoint = (GeoPoint) other;
			return (point.equals(temPoint.point) && geometry.equals(temPoint.geometry));
		}
	}
	/**
	 * a method to find all intersection point of a given ray with the geometry body
	 * @param ray
	 * @return a list of the intersection points
	 */
	public static final List<GeoPoint> EMPTY_LIST = new ArrayList<GeoPoint>();
	List<GeoPoint> findIntersections(Ray ray);
}
