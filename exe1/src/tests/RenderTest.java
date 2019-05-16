package tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import elements.AmbientLight;
import elements.Camera;
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
	ImageWriter imageWriter = new ImageWriter("test0", 500, 500, 1000, 1000);
	Render render = new Render(imageWriter, scene);
	Camera camera = new Camera(Point.ZERO,  Vector.Z_AXIS, Vector.NEGATIVE_Y_AXIS);
	
	/**
	 * test the actual rendering of an image
	 */
	@Test
	public void basicRendering(){
		
		scene.setCameraAndDistance(150, camera);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(100, 100, 100), 2));
		Geometries geometries = new Geometries();
		scene.setGeometries(geometries);
		//add a sphere to the model
		geometries.add(new Sphere(50, new Point(0, 0, 150)));
		
		//add 4 triangles to the model
		geometries.add(new Triangle(new Point( 100, 0, 149),
				 					new Point(  0, 100, 149),
				 					new Point( 100, 100, 149)));
		
		geometries.add(new Triangle(new Point( 100, 0, 149),
				 			 		new Point(  0, -100, 149),
				 			 		new Point( 100,-100, 149)));
		
		geometries.add(new Triangle(new Point(-100, 0, 149),
				 					new Point(  0, 100, 149),
				 					new Point(-100, 100, 149)));

		geometries.add(new Triangle(new Point(-100, 0, 149),
				 			 		new Point(  0,  -100, 149),
				 			 		new Point(-100, -100, 149)));

		render.renderImage();
		//add a grid
		render.printGrid(50, Color.WHITE);
		//write the final image
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