package geometries;

import java.util.ArrayList;
import java.util.List;
import primitives.Ray;

/**
 * Represents a collection of geometry shapes in the space
 */
public class Geometries implements Intersectable {
	List<Intersectable> _geometriesList;
	
	// ***************** Constructors ******************** //
	/**
	 * Ctor for initializing an empty collection
	 */
	public Geometries() {
		_geometriesList  = new ArrayList<Intersectable>();
	}

	/**
	 * Ctor to make a collection from an existing list of shapes
	 * @param paramShapesList
	 */
	public Geometries(Intersectable... paramShapesList) {
		_geometriesList = new ArrayList<Intersectable>();
		for (Intersectable paramShape : paramShapesList) {
			_geometriesList.add(paramShape);
		}
	}
	
	// ***************** Operations ******************** //
	/**
	 * adding a list of shapes or a single shape to the collection
	 * @param paramShapesList
	 */
	public void add(Intersectable... paramShapesList) {
		for (Intersectable paramShape : paramShapesList) {
			_geometriesList.add(paramShape);
		}
	}
	
	/**
	 * find all intersections of a ray with the shapes in the collection
	 */
	@Override
	public List<GeoPoint> findIntersections(Ray ray) {
		//todo yeeool
		List<GeoPoint> intersectList = new ArrayList<GeoPoint>();
		for (Intersectable intersectable : _geometriesList) {
			intersectList.addAll(intersectable.findIntersections(ray));
		}
		return intersectList;
	}
	
	/**
	 * clear the list of geometries to be empty
	 */
	public void clear() {
		_geometriesList.clear();
	}
	
	// ***************** Administrations ******************** //
	/**
	 * string representation for a collection of shapes
	 */
	@Override
	public String toString() {
		String resultString = "Collection size: " + _geometriesList.size() + "\n";
		for(Intersectable shape : _geometriesList) {
			resultString += "\n" + shape.toString(); 
		}
		return resultString;
	}
}
