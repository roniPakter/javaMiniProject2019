package tests;

import org.junit.Test;
import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.PointLight;
import elements.SpotLight;
import geometries.*;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

/**
 * test for the render class
 */
public class RenderTest {
	Scene scene = new Scene("Test scene");
	ImageWriter imageWriter = new ImageWriter("test0", 500, 500, 500, 500);
	Render render = new Render(imageWriter, scene);
	Camera camera = new Camera(new Point(0, 0, 0), Vector.Z_AXIS, Vector.NEGATIVE_Y_AXIS);
	Material m = new Material(0.5, 0.8, 40);

	/**
	 * test the actual rendering of a colored image - sphere and four triangles
	 */
	@Test
	public void basicRendering() {
		scene.setCameraAndDistance(150, camera);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(100, 100, 100), 0.3));
		Geometries geometries = new Geometries();
		scene.addGeometries(geometries);
		// add a sphere to the model
		geometries.add(new Sphere(50, new Point(0, 0, 150), new Color(100, 20, 0), m));

		// add 4 triangles to the model
		// blue triangle (on the right bottom)
		geometries.add(new Triangle(new Point(100, 0, 149), new Point(0, 100, 149), new Point(100, 100, 149),
				new Color(20, 0, 130), m));

		// red triangle (on the right upper corner)
		geometries.add(new Triangle(new Point(100, 0, 149), new Point(0, -100, 149), new Point(100, -100, 149),
				new Color(130, 0, 0), m));

		// black triangle (on the left bottom)
		geometries.add(new Triangle(new Point(-100, 0, 149), new Point(0, 100, 149), new Point(-100, 100, 149),
				new Color(20, 0, 130), m));

		// green triangle (on the left upper corner)
		geometries.add(new Triangle(new Point(-100, 0, 149), new Point(0, -100, 149), new Point(-100, -100, 149),
				new Color(0, 155, 30), m));

		scene.setLights(new PointLight(new Color(255, 100, 100), new Point(-100, -70, 50), 1, 0.0000001, 0.0000005));

		render.renderImage();
		// add a grid
		render.printGrid(50, Color.WHITE);
		// write the final image
		render.writeToImage();

	}

	@Test
	public void spotLightTest() {
		scene.setCameraAndDistance(100, camera);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(100, 100, 100), 0.3));
		Sphere sphere = new Sphere(800, new Point(0.0, 0.0, 1000), new Color(0, 0, 100));
		sphere.setMaterial(m);
		scene.addGeometries(sphere);
		scene.setLights(new SpotLight(new Vector(2, 2, 3), new Color(255, 100, 100), new Point(-200, -200, 100), 1,
				0.00001, 0.000005));
		scene.addLights(new DirectionalLight(new Vector(-2, 2, 3), new Color(255, 100, 100)));
		scene.addLights(new PointLight(new Color(255, 100, 100), new Point(-200, 200, 100), 1, 0.0001, 0.000005));

		ImageWriter imageWriter = new ImageWriter("Spot-point-direct on sphere test", 500, 500, 500, 500);

		Render render = new Render(imageWriter, scene);

		render.renderImage();
		// write the final image
		render.writeToImage();
	}

	@Test
	public void spotLightTestTriangles() {
		scene.setCameraAndDistance(100, camera);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(100, 100, 100), 0.3));

		Triangle triangle = new Triangle(new Point(3500, 3500, 1000), new Point(-3500, -3500, 2000),
				new Point(3500, -3500, 1000), Color.BLACK, m);

		Triangle triangle2 = new Triangle(new Point(3500, 3500, 1000), new Point(-3500, 3500, 1000),
				new Point(-3500, -3500, 2000), Color.BLACK, m);

		scene.addGeometries(triangle);
		scene.addGeometries(triangle2);

		scene.setLights(new SpotLight(new Vector(-2, 2, 3), new Color(255, 100, 100), new Point(200, -200, 100), 1,
				0.000001, 0.0000005));

		ImageWriter imageWriter = new ImageWriter("Spot test 2", 500, 500, 500, 500);

		Render render = new Render(imageWriter, scene);

		render.renderImage();
		// write the final image
		render.writeToImage();

	}

	@Test
	public void directionalLightTestTriangles() {
		scene.setCameraAndDistance(100, camera);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(100, 100, 100), 0.3));

		Triangle triangle = new Triangle(new Point(3500, 3500, 1000), new Point(-3500, -3500, 2000),
				new Point(3500, -3500, 1000), Color.BLACK, m);

		Triangle triangle2 = new Triangle(new Point(3500, 3500, 1000), new Point(-3500, 3500, 1000),
				new Point(-3500, -3500, 2000), Color.BLACK, m);

		scene.addGeometries(triangle);
		scene.addGeometries(triangle2);

		scene.setLights(new DirectionalLight(new Vector(-2, 2, 3), new Color(255, 100, 100)));

		ImageWriter imageWriter = new ImageWriter("Directional Light test 2", 500, 500, 500, 500);

		Render render = new Render(imageWriter, scene);

		render.renderImage();
		// write the final image
		render.writeToImage();

	}

	@Test
	public void pointLightTestTriangles() {
		scene.setCameraAndDistance(100, camera);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(100, 100, 100), 0.3));

		Triangle triangle = new Triangle(new Point(3500, 3500, 1000), new Point(-3500, -3500, 2000),
				new Point(3500, -3500, 1000), Color.BLACK, m);

		Triangle triangle2 = new Triangle(new Point(3500, 3500, 1000), new Point(-3500, 3500, 1000),
				new Point(-3500, -3500, 2000), Color.BLACK, m);

		scene.addGeometries(triangle);
		scene.addGeometries(triangle2);

		scene.setLights(new PointLight(new Color(255, 100, 100), new Point(200, -200, 100), 1, 0.000001, 0.0000005));

		ImageWriter imageWriter = new ImageWriter("Point test 2", 500, 500, 500, 500);

		Render render = new Render(imageWriter, scene);

		render.renderImage();
		// write the final image
		render.writeToImage();

	}
	
	@Test
	public void shadowTestTrianglesAndSphere() {
		scene.setCameraAndDistance(100, camera);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(100, 100, 100), 0.3));

		Triangle triangle = new Triangle(
				new Point(3500, 3500, 1000),
				new Point(-3500, -3500, 2000),
				new Point(3500, -3500, 2000),
				Color.BLACK, m);

		Triangle triangle2 = new Triangle(
				new Point(3500, 3500, 1000),
				new Point(-3500, -3500, 2000),
				new Point(-3500, 3500, 1000),
				Color.BLACK, m);
		Sphere sphere = new Sphere(700, new Point(0,-200,1500), new Color(0,0,100), m);
		scene.addGeometries(triangle, triangle2, sphere);
		scene.setLights(new PointLight(new Color(255, 100, 100), new Point(200, -400, 500), 1, 0.000001, 0.0000005));

		ImageWriter imageWriter = new ImageWriter("shadow test - sphere above triangles", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);
		render.renderImage();
		// write the final image
		render.writeToImage();

	}

	@Test
	public void sphereTritangleShadow() {
		scene.setCameraAndDistance(200, camera);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(100, 100, 100), 0.3));

		Sphere sphere = new Sphere(500, new Point(0.0, 0.0, 1000), new Color(0, 0, 100), m);
		scene.addGeometries(sphere);
		Triangle triangle = new Triangle(new Point(-125, 225, 260), new Point(-225, 125, 260),
				new Point(-225, 225, 270), new Color(0, 0, 100), m);
		scene.addGeometries(triangle);
		scene.setLights(new SpotLight(new Vector(2, -2, 3), new Color(255, 100, 100), new Point(-200, 200, 150), 1,
				0.00001, 0.000005));

		ImageWriter imageWriter = new ImageWriter("sphere triangle shadow", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);
		render.renderImage();
		render.writeToImage();

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		Triangle triangle1 = new Triangle(new Point(-75, 175, 260), new Point(-175, 75, 260), new Point(-175, 175, 270),
				new Color(0, 0, 100), m);
		scene.setGeometries(triangle1, sphere);

		imageWriter = new ImageWriter("sphere triangle shadow-triangleMove1", 500, 500, 500, 500);
		render = new Render(imageWriter, scene);
		render.renderImage();
		render.writeToImage();

		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		Triangle triangle2 = new Triangle(new Point(-25, 125, 260), new Point(-125, 25, 260), new Point(-125, 125, 270),
				new Color(0, 0, 100), m);
		scene.setGeometries(triangle2, sphere);

		imageWriter = new ImageWriter("sphere triangle shadow-triangelMove2", 500, 500, 500, 500);
		render = new Render(imageWriter, scene);
		render.renderImage();
		render.writeToImage();

		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		scene.setLights(new SpotLight(new Vector(2, -2, 3), new Color(255, 190, 190), new Point(-200, 200, 170), 1,
				0.00001, 0.000005));

		scene.setGeometries(triangle, sphere);
		imageWriter = new ImageWriter("sphere triangle shadow-lightMove1", 500, 500, 500, 500);
		render = new Render(imageWriter, scene);
		render.renderImage();
		render.writeToImage();

		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		scene.setLights(new SpotLight(new Vector(2, -2, 3), new Color(255, 100, 100), new Point(-200, 200, 190), 1, 0.00001, 0.000005));

		scene.setGeometries(triangle, sphere);
		imageWriter = new ImageWriter("sphere triangle shadow-lightMove2", 500, 500, 500, 500);
		render = new Render(imageWriter, scene);
		render.renderImage();
		render.writeToImage();

	}

//	@Test
//	public void closestPointTest(){
//		scene.setCameraAndDistance(100, camera);
//		
//		Point p1 = new Point(0.5, 0.3, 0.2);
//		List<Point> points = new ArrayList<Point>();
//		points.add(new Point(1,1,1));
//		points.add(new Point(1,1,0.5));
//		points.add(new Point(-2,-2,0.3));
//		points.add(new Point(0.5, 0.3, 0.2));
//		assertEquals(p1, render.getClosestPoint(points));
//		
//	}
}