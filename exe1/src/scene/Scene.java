package scene;

import elements.*;
import primitives.Color;
import geometries.*;

public class Scene {

	String _sceneName;
	Color _background;
	AmbientLight _ambientLight;
	Geometries _geometriesModel;
	Camera _camera;
	double _screenDistance;

	// ***************** Constructors ********************** //
	public Scene(String sceneName) {
		_sceneName = sceneName;
		_geometriesModel = new Geometries();
		_ambientLight = new AmbientLight(_background, 0);
	}

	// ***************** Setters/Getters ********************** //

	public void setSceneName(String sceneName) {
		_sceneName = sceneName;

	}

	public void setColorBackground(Color other) {
		_background = new Color(other);
	}

	public void setAmbientLight(AmbientLight ambientLight) {
		_ambientLight = new AmbientLight(ambientLight);
	}

	public void setCameraAndDistance(double screenDistance, Camera other) {
		_screenDistance = screenDistance;
		_camera = new Camera(other.getP0(), other.getToVector(), other.getUpVector());
	}

	public String getSceneName() {
		return _sceneName;
	}

	public Color getColorBackground() {
		return _background;
	}

	public AmbientLight getAmbientLight() {
		return _ambientLight;
	}

	public Camera getCamera() {
		return _camera;
	}
	
	public Geometries getGeometries() {
		return _geometriesModel;
	}
	
	public double getScreenDistance() {
		return _screenDistance;
	}

	// ***************** Operation ********************** //
	
	public void addModel(Intersectable... shapes) {
		_geometriesModel.add(shapes);
	}

}
