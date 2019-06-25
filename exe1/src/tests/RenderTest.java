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
	
	Material m = new Material(0.5, 0.8, 120);

	/**
	 * test the actual rendering of a colored image - sphere and four triangles
	 */
	@Test
	public void basicRendering() throws InterruptedException {
		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point(0, 0, -2000), Vector.Z_AXIS, Vector.NEGATIVE_Y_AXIS);
		scene.setCameraAndDistance(1200, camera);
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


		ImageWriter imageWriter = new ImageWriter("test0ASS", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene,true);
		render.renderImage();
		// add a grid
		render.printGrid(50, Color.WHITE);
		// write the final image
		render.writeToImage();

	}

	@Test
	public void spotLightTest() throws InterruptedException {
		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point(0, 0, -2000), Vector.Z_AXIS, Vector.NEGATIVE_Y_AXIS);
		scene.setCameraAndDistance(600, camera);
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

		Render render = new Render(imageWriter, scene, true);

		render.renderImage();
		// write the final image
		render.writeToImage();
	}

	@Test
	public void spotLightTestTriangles() throws InterruptedException {
		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point(0, 0, -2000), Vector.Z_AXIS, Vector.NEGATIVE_Y_AXIS);
		scene.setCameraAndDistance(200, camera);
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

		Render render = new Render(imageWriter, scene,true);

		render.renderImage();
		// write the final image
		render.writeToImage();

	}

	@Test
	public void directionalLightTestTriangles() throws InterruptedException {
		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point(0, 0, -2000), Vector.Z_AXIS, Vector.NEGATIVE_Y_AXIS);
		scene.setCameraAndDistance(200, camera);
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

		Render render = new Render(imageWriter, scene,true);

		render.renderImage();
		// write the final image
		render.writeToImage();

	}

	@Test
	public void pointLightTestTriangles() throws InterruptedException {
		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point(0, 0, -2000), Vector.Z_AXIS, Vector.NEGATIVE_Y_AXIS);
		scene.setCameraAndDistance(200, camera);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(100, 100, 100), 0.3));

		Triangle triangle = new Triangle(new Point(3500, 3500, 1000), new Point(-3500, -3500, 2000),
				new Point(3500, -3500, 1000), Color.BLACK, m);

		Triangle triangle2 = new Triangle(new Point(3500, 3500, 1000), new Point(-3500, 3500, 1000),
				new Point(-3500, -3500, 2000), Color.BLACK, m);

		scene.addGeometries(triangle);
		scene.addGeometries(triangle2);

		scene.setLights(new PointLight(new Color(255, 100, 100), new Point(200, -200, 100), 1, 0.000001, 0.0000005));

		ImageWriter imageWriter = new ImageWriter("Point test 2SS", 500, 500, 500, 500);

		Render render = new Render(imageWriter, scene, true);

		render.renderImage();
		// write the final image
		render.writeToImage();

	}
	
	@Test
	public void shadowTestTrianglesAndSphere() throws InterruptedException {
		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point(0, 0, -2000), Vector.Z_AXIS, Vector.NEGATIVE_Y_AXIS);
		scene.setCameraAndDistance(300, camera);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(100, 100, 100), 0.3));

		Triangle triangle = new Triangle(
				new Point(3500, 3500, 1000),
				new Point(-3500, -3500, 2000),
				new Point(3500, -3500, 2000),
				Color.BLACK, new Material(0.5, 0.8, 120));

		Triangle triangle2 = new Triangle(
				new Point(3500, 3500, 1000),
				new Point(-3500, -3500, 2000),
				new Point(-3500, 3500, 1000),
				Color.BLACK, m);
		Sphere sphere = new Sphere(300, new Point(0,0,500), new Color(0,0,100), new Material(0.5, 0.8, 120));
		scene.addGeometries(triangle, triangle2, sphere);
		scene.setLights(new PointLight(new Color(255, 100, 0), new Point(-100,-100,0), 1, 0.000001, 0.0000005));

		ImageWriter imageWriter = new ImageWriter("shadow test - sphere above triangles", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene,true);
		render.renderImage();
		// write the final image
		render.writeToImage();

	}

	@Test
	public void sphereTritangleShadow() throws InterruptedException {
		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point(0, 0, -2000), Vector.Z_AXIS, Vector.NEGATIVE_Y_AXIS);
		scene.setCameraAndDistance(700, camera);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(100, 100, 100), 0.3));

		Sphere sphere = new Sphere(500, new Point(0.0, 0.0, 1000), new Color(0, 0, 100), m);
		scene.addGeometries(sphere);
		Triangle triangle = new Triangle(new Point(-125, 225, 260), new Point(-225, 125, 260),
				new Point(-225, 225, 270), new Color(0, 0, 100), m);
		scene.addGeometries(triangle);
		scene.setLights(new SpotLight(new Vector(2, -2, 3), new Color(255, 100, 100), new Point(-200, 200, 150), 1,
				0.00001, 0.000005));

		ImageWriter imageWriter = new ImageWriter("sphere triangle shadowSS", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene,true);
		render.renderImage();
		render.writeToImage();

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		Triangle triangle1 = new Triangle(new Point(-75, 175, 260), new Point(-175, 75, 260), new Point(-175, 175, 270),
				new Color(0, 0, 100), m);
		scene.setGeometries(triangle1, sphere);

		imageWriter = new ImageWriter("sphere triangle shadow-triangleMove1SS", 500, 500, 500, 500);
		render = new Render(imageWriter, scene,true);
		render.renderImage();
		render.writeToImage();

		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		Triangle triangle2 = new Triangle(new Point(-25, 125, 260), new Point(-125, 25, 260), new Point(-125, 125, 270),
				new Color(0, 0, 100), m);
		scene.setGeometries(triangle2, sphere);

		imageWriter = new ImageWriter("sphere triangle shadow-triangelMove2SS", 500, 500, 500, 500);
		render = new Render(imageWriter, scene,true);
		render.renderImage();
		render.writeToImage();

		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		scene.setLights(new SpotLight(new Vector(2, -2, 3), new Color(255, 190, 190), new Point(-200, 200, 170), 1,
				0.00001, 0.000005));

		scene.setGeometries(triangle, sphere);
		imageWriter = new ImageWriter("sphere triangle shadow-lightMove1SS", 500, 500, 500, 500);
		render = new Render(imageWriter, scene,true);
		render.renderImage();
		render.writeToImage();

		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		scene.setLights(new SpotLight(new Vector(2, -2, 3), new Color(255, 100, 100), new Point(-200, 200, 190), 1, 0.00001, 0.000005));

		scene.setGeometries(triangle, sphere);
		imageWriter = new ImageWriter("sphere triangle shadow-lightMove2SS", 500, 500, 500, 500);
		render = new Render(imageWriter, scene,true);
		render.renderImage();
		render.writeToImage();

	}
	
	@Test
	public void mirrorTestTriangleAndSphere() throws InterruptedException {	
		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point(0, 0, -2000), Vector.Z_AXIS, Vector.NEGATIVE_Y_AXIS);
		scene.setCameraAndDistance(600, camera);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(100, 100, 100), 0.3));

		Triangle triangle = new Triangle(
				new Point(-3300, 3300, 500),
				new Point(3300, 3300, 900),
				new Point(-3300, -3300, 1000),
				Color.BLACK, new Material(0, 1, 300, 1, 0));

		Triangle triangle2 = new Triangle(
				new Point(3300, -1500, -1700),
				new Point(3300, 3300, 900),
				new Point(-3300, -3300, 1000),
				Color.BLACK, new Material(0, 1, 300, 1, 0));
		Sphere sphere = new Sphere(300, new Point(-300,300,500), new Color(0,0,100), new Material(0.2, 0.8, 120, 0, 1));
		Sphere innerSphere = new Sphere(200, new Point(-300,300,500), new Color(0,150,100), new Material(0.5, 0.8, 120));
		scene.addGeometries(triangle, triangle2, sphere, innerSphere);
		scene.setLights(new PointLight(new Color(255, 100, 100), new Point(-200,-200,-200), 1, 0.000001, 0.0000005));

		ImageWriter imageWriter = new ImageWriter("mirror test - sphere against mirror blur", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene,true);
		render.renderImage();
		// write the final image
		render.writeToImage();

	}
	
	@Test
	public void tranperancyTestSphereInSphere() throws InterruptedException {
		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point(0, 0, -2000), Vector.Z_AXIS, Vector.NEGATIVE_Y_AXIS);
		scene.setCameraAndDistance(500, camera);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(100, 100, 100), 0.3));

		Sphere sphere = new Sphere(400, new Point(0,0,500), new Color(0,0,100), new Material(0.4, 0.8, 120, 0, 0.8));
		Sphere innerSphere = new Sphere(200, new Point(0,0,500), new Color(200,0,0), new Material(0.5, 0.8, 120));
		scene.addGeometries(innerSphere ,sphere);
		scene.setLights(new PointLight(new Color(255, 100, 100), new Point(-100,-100,0), 1, 0.000001, 0.0000005));

		ImageWriter imageWriter = new ImageWriter("sphere within sphere", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene,true);
		render.renderImage();
		// write the final image
		render.writeToImage();

	}
	
	@Test
	public void focusTestSphertesOnFloor() throws InterruptedException {
		Camera camera = new Camera(new Point(0, 0, -2000), Vector.Z_AXIS, Vector.NEGATIVE_Y_AXIS);
		Scene scene = new Scene("spheres-on-floor");
		scene.setDofRayBeamSize(20);
		scene.setApertureSize(0);
		scene.setFocusLength(3400);
		scene.setCameraAndDistance(600, camera);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(100, 100, 100), 0.3));
		Material mirrorMaterial = new Material(0.5, 0.8, 200, 1, 0);
		Material glassMaterial = new Material(0.1, 0.7, 150, 0.05, 1); 

		Sphere sphere = new Sphere(200, new Point(300,600,500), new Color(0,0,150), new Material(0.4, 0.8, 120));
		Sphere sphere2 = new Sphere(200, new Point(-300,600,500), new Color(0,200,0), new Material(0.5, 0.8, 120));
		Sphere sphere3 = new Sphere(200, new Point(0,600,1500), new Color(200,0,0), new Material(0.5, 0.8, 120));
		Sphere sphere4 = new Sphere(100, new Point(-500,700,2000), new Color(0,200,200), new Material(0.5, 0.8, 120));
		Sphere sphere5 = new Sphere(300, new Point(-900,500,2300), new Color(100,200,0), new Material(0.5, 0.8, 120));
		Sphere sphere6 = new Sphere(300, new Point(800,500,2500), new Color(200,100,0), new Material(0.5, 0.8, 120));
		Sphere sphere7 = new Sphere(300, new Point(1300,500,2800), new Color(200,0,100), new Material(0.5, 0.8, 120));
		Sphere sphere8 = new Sphere(450, new Point(-1500,350,3900), new Color(60,90,170), new Material(0.5, 0.8, 120));
		Sphere sphere9 = new Sphere(1000, new Point(-1300,-200,5800), new Color(60,70,70), mirrorMaterial);
		Sphere sphere10 = new Sphere(1000, new Point(1100,-200,5990), new Color(60,190,220), m);
		Sphere sphere11 = new Sphere(50, new Point(2100,750,4000), new Color(40,100,0), mirrorMaterial);
		Sphere sphere12 = new Sphere(50, new Point(1800,750,2400), new Color(40,100,0), mirrorMaterial);
		Sphere sphere13 = new Sphere(50, new Point(1300,750,1900), new Color(40,100,0), mirrorMaterial);
		Sphere sphere14 = new Sphere(50, new Point(900,750,1200), new Color(40,100,0), mirrorMaterial);
		Sphere sphere15 = new Sphere(50, new Point(300,750,200), new Color(40,100,0), mirrorMaterial);
		Sphere sphere16 = new Sphere(50, new Point(-1900,750,3000), new Color(40,0,100), glassMaterial);
		Sphere sphere17 = new Sphere(50, new Point(-1200,750,2400), new Color(40,0,100), glassMaterial);
		Sphere sphere18 = new Sphere(50, new Point(-1000,750,1100), new Color(40,0,100), glassMaterial);	
		Sphere sphere19 = new Sphere(80, new Point(-200,720,200), new Color(40,0,100), glassMaterial);	
		
		Plane floor = new Plane(new Point(0, 800,0), new Vector(0,1,0), new Color(50, 60, 0), m);
		
		
		scene.addGeometries(sphere, sphere2 ,sphere3, sphere4, sphere5, sphere7, sphere6, sphere8 ,sphere9,sphere10,sphere11,sphere12,sphere13,sphere14,sphere15,sphere16,sphere17,sphere18,sphere19, floor);
		scene.setLights(
				new PointLight(new Color(255, 100, 100), new Point(0,-500,1200), 1, 0.000001, 0.0000005),
				new PointLight(new Color(150, 200, 0), new Point(0,-1300,4200), 1, 0.000001, 0.0000005));

		ImageWriter imageWriter = new ImageWriter("spheres integration test - not adaptive", 500, 500, 1000, 1000);
		Render render = new Render(imageWriter, scene);
		render.renderImage();
		// write the final image
		render.writeToImage();
		
//		scene.setApertureSize(18);
//		scene.setFocusLength(5000);
//		imageWriter = new ImageWriter("spheres focus test - far focus2", 500, 500, 1000, 1000);
//		render = new Render(imageWriter, scene);
//		render.renderImage();
//		// write the final image
//		render.writeToImage();
	}
	
	@Test
	public void adaptiveSSTest() throws InterruptedException {
		Camera camera = new Camera(new Point(0, 0, -2000), Vector.Z_AXIS, Vector.NEGATIVE_Y_AXIS);
		Scene scene = new Scene("ASS-test");
		scene.setCameraAndDistance(700, camera);
		scene.setDofRayBeamSize(20);
		scene.setApertureSize(35);
		scene.setFocusLength(3700);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(100, 100, 100), 0.3));

		Sphere sphere = new Sphere(200, new Point(600,0,4500), new Color(0,0,200), new Material(0.4, 0.8, 120));
		Sphere rightSphere = new Sphere(200, new Point(0,0,1800), new Color(200,0,0), new Material(0.5, 0.8, 120));
		Sphere leftSphere = new Sphere(200, new Point(-300,0,0), new Color(0,200,0), new Material(0.5, 0.8, 120));
		scene.addGeometries(rightSphere, leftSphere, sphere);
		scene.addLights(new DirectionalLight(new Vector(0,-1,0), new Color(120,200,100)));
		ImageWriter imageWriter = new ImageWriter("spheres SS with focus", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);
		render.renderImage();
		// write the final image
		render.writeToImage();

	}
}