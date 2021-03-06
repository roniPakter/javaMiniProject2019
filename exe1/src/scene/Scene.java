 package scene;

import java.util.List;

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
	/** list of the light sources of the scene */
	List<LightSource> _lights;
	/** a collection of geometric bodies creating a 3D model of the scene*/
	Geometries _geometriesModel;
	/** the camera shoots the picture of the scene*/
	Camera _camera;
	/**the distance of the view-plane from the camera lens*/
	double _screenDistance;
	/**the distance of the focus-plane from the camera lens - determines how close would be an in-focus object*/
	double _focusLength;
	/**the size of the camera aperture - determines how blur the object not in focus would be (zero by default)*/
	double _apertureSize;
	/**the size of rayBeam - determines how smooth blur the object not in focus would be*/
	int _dofRayBeamSize;
	// ***************** Constructors ********************** //
	/**ctor for a scene with only scene name 
	 * @param sceneName
	 */
	public Scene(String sceneName) {
		_sceneName = sceneName;
		_geometriesModel = new Geometries();
		_lights = LightSource.EMPTY_LIST;
		_apertureSize = 0;
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
	
	/**
	 * sets the light of the scene (clearing former lights)
	 * @param lights
	 */
	public void setLights(LightSource... lights) {
		_lights.clear();
		for (LightSource lightSource : lights) {
			_lights.add(lightSource);
		}
	}
	
	/**
	 * sets the geometric bodies of the scene (clearing former geometries)
	 * @param geometries - one or more geometric body
	 */
	public void setGeometries(Intersectable...geometries ) {
		_geometriesModel.clear();
		for (Intersectable geometry : geometries) {
			_geometriesModel.add(geometry);
		}
	}
	
	/**
	 * set the focus length of the scene - how far the focused objects would be
	 * @param focusLength
	 */
	public void setFocusLength(double focusLength) {
		_focusLength = focusLength;
	}
	
	/**
	 * set the aperture of the scene - how blur the unfocused objects would be
	 * @param focusLength
	 */
	public void setApertureSize(double apertureSize) {
		_apertureSize = apertureSize;
	}
	
	/**
	 * set the amount of ray generated for the blur of the unfocused objects
	 * @param rayBeamSize 
	 */
	public void setDofRayBeamSize(int rayBeamSize) {
		_dofRayBeamSize = rayBeamSize;
	}

	// ***************** Getters ********************** //
	
	/**
	 * returns the name of the scene
	 */
	public String getSceneName() {
		return _sceneName;
	}
	
	/**
	 * @return aperture size - how blur the unfocused objects are
	 */
	public double getApertureSize() {
	return _apertureSize;
	}
	
	/**
	 * @return focus length of the scene - how far the focused objects would be
	 */
	public double getFocusLength() {
		return _focusLength;
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
	
	/**
	 * @return the list of the light sources in the scene
	 */
	public List<LightSource> getLights() {
		return _lights;
	}
	
	/**
	 * 
	 * @return the amount of rays for the unfocused blur
	 */
	public int getdofRayBeamSize() {
		return _dofRayBeamSize;
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
	public void addGeometries(Intersectable... shapes) {
		_geometriesModel.add(shapes);
	}
	
	/**
	 * Add a light source or collection of lights to the scene
	 * @param lights
	 */
	public void addLights(LightSource... lights) {
		for (LightSource lightSource : lights) {
			_lights.add(lightSource);
		}
	}

}
