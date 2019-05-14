package scene;

import elements.*;
import primitives.Color;
import geometries.*;

/**
 * Represents a scene of a 3D model and the light and colors in front of a camera
 */
public class Scene {

	/** the name of the scene*/
	String _sceneName;
	/** the background color of the scene*/
	Color _background;
	/** the ambient light for default light of objects (color,intensity factor) */
	AmbientLight _ambientLight;
	/** a collection of geometric bodies creating a 3D model of the scene*/
	Geometries _geometriesModel;
	/** the camera shoots the picture of the scene*/
	Camera _camera;
	/**the distance of the view-plane from the camera lens*/
	double _screenDistance;

	// ***************** Constructors ********************** //
	/**ctor for a scene with only scene name
	 * @param sceneName
	 */
	public Scene(String sceneName) {
		_sceneName = sceneName;
		_geometriesModel = new Geometries();
	}

	// ***************** Setters ********************** //

	/**
	 * sets the scene name
	 * @param sceneName
	 */
	public void setSceneName(String sceneName) {
		_sceneName = sceneName;
	}

	/**
	 * sets the color of the background in the scene
	 * @param color
	 */
	public void setBackground(Color color) {
		_background = new Color(color);
	}

	/**
	 * sets the default light - the ambient light
	 * @param ambientLight
	 */
	public void setAmbientLight(AmbientLight ambientLight) {
		_ambientLight = new AmbientLight(ambientLight);
	}

	/**
	 * sets the camera in front of the scene and the distance of the view plane
	 * @param screenDistance
	 * @param camera
	 */
	public void setCameraAndDistance(double screenDistance, Camera camera) {
		_screenDistance = screenDistance;
		_camera = new Camera(camera.getP0(), camera.getToVector(), camera.getUpVector());
	}

	// ***************** Getters ********************** //
	
	/**
	 * returns the name of the scene
	 */
	public String getSceneName() {
		return _sceneName;
	}

	/**
	 * @return the color of the scene's background
	 */
	public Color getColorBackground() {
		return _background;
	}

	/**
	 * @return the ambient light of the scene
	 */
	public AmbientLight getAmbientLight() {
		return _ambientLight;
	}

	/**
	 * @return the camera shoots the scene
	 */
	public Camera getCamera() {
		return _camera;
	}
	
	/**
	 * @return the collection of the geometric bodies of the list
	 */
	public Geometries getGeometries() {
		return _geometriesModel;
	}
	
	/**
	 * @return the distance of the view plane from the camera lens
	 */
	public double getScreenDistance() {
		return _screenDistance;
	}
	// ***************** Administration ********************** //
	
	/**
	 * a string representation of the scene
	 */
	@Override
	public String toString() {
		return "Scene Name: " + _sceneName + 
				"\nBackground color: " + _background + 
				"\nAmbient Light: " + _ambientLight + 
				"\nCamera Positioning: " + _camera + 
				"Distance from View-Plane: " + _screenDistance + 
				"Geometric 3D Model: \n" + _geometriesModel ;
	}

	// ***************** Operation ********************** //
	
	/**
	 * Add a shape or collection of shapes to the scene
	 * @param shapes
	 */
	public void setGeometries(Intersectable... shapes) {
		_geometriesModel.add(shapes);
	}

}
