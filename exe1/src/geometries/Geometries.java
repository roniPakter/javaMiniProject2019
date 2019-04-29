package geometries;

import java.util.ArrayList;
import java.util.List;
import primitives.Point;
import primitives.Ray;

/**
 * Represents a collection of geometry shapes in the space
 */
public class Geometries implements Intersectable {
	List<Intersectable> geometriesList;
	
	// ***************** Constructors ******************** //
	/**
	 * Ctor for initializing an empty collection
	 */
	public Geometries() {
		geometriesList  = new ArrayList<Intersectable>();
	}

	/**
	 * Ctor to make a collection from an existing list of shapes
	 * @param paramShapesList
	 */
	public Geometries(Intersectable... paramShapesList) {
		geometriesList = new ArrayList<Intersectable>();
		for (Intersectable paramShape : paramShapesList) {
			geometriesList.add(paramShape);
		}
	}
	
	// ***************** Operations ******************** //
	/**
	 * adding a list of shapes or a single shape to the collection
	 * @param paramShapesList
	 */
	public void add(Intersectable... paramShapesList) {
		for (Intersectable paramShape : paramShapesList) {
			geometriesList.add(paramShape);
		}
	}
	
	@Override
	public List<Point> findIntersections(Ray ray) {
		List<Point> intersectList = new ArrayList<Point>();
		for (Intersectable intersectable : geometriesList) {
			intersectList.addAll(intersectable.findIntersections(ray));
		}
		return intersectList;
	}
	
	// ***************** Administrations ******************** //
	/**
	 * string representation for a collection of shapes
	 */
	@Override
	public String toString() {
		String resultString = "Collection size: " + geometriesList.size() + "\n";
		for(Intersectable shape : geometriesList) {
			resultString += "\n" + shape.toString(); 
		}
		return resultString;
	}
}
