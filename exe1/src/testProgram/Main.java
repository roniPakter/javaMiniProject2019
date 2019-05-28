/*
 * Aharon Packter ID 201530508
 * Shlomo Perlov ID 206914301
 * 25/03/2019
 * 
 * Mini project in Software Engineering
 * Exercise 2
 */
package testProgram;
import primitives.*;
import geometries.*;


public class Main {

	public static void main(String[] args) {
		try {
			Cylinder cylinder3 = new Cylinder(1.0, 
					new Ray(new Point(0,0,0), new Vector(0,0,1)),
					3.0);
			Point point = new Point(1,1,1);
			cylinder3.getNormal(point);
			//only a comment for checking!
			//let us try another time...
			Coordinate xCoordinate = new Coordinate(1.5);
			Coordinate yCoordinate = new Coordinate(2.1);
			Coordinate zCoordinate = new Coordinate(1);
			Point point1 = new Point(xCoordinate, yCoordinate, zCoordinate);
			Point point2 = new Point(new Coordinate(1), new Coordinate(2), new Coordinate(1));
			Point point3 = new Point(xCoordinate, yCoordinate, new Coordinate(10));
			Triangle triangle = new Triangle(point1, point2, point3);
			Vector vector1 = new Vector(new Point(3,2,1));
			Vector vector2 = new Vector(point3);
			Ray ray1 = new Ray(point1, vector2);
			
			Sphere sphere = new Sphere(4.4, point2);
			Cylinder cylinder = new Cylinder(5, ray1, 20);
			Tube tube = new Tube(0.7, ray1);			
			Plane plane = new Plane(point1,  vector2);
			
			System.out.println("Point1:\n" + point1.toString());
			System.out.println("Point2:\n" + point2.toString());
			System.out.println("Point3:\n" + point3.toString());
			
			System.out.println("Vector1:\n" + vector1.toString());
			System.out.println("Vector2:\n" + vector2.toString());
			System.out.println("Ray1:\n" + ray1.toString());
			
			System.out.println("Triangle: " + triangle.toString());
			System.out.println("Sphere:\n" + sphere.toString());
			System.out.println("Tube:\n" + tube.toString());
			System.out.println("Cylinder:\n" + cylinder.toString());
			System.out.println("Plane:\n" + plane.toString());
			
			//--------------
			System.out.println("Vector Subtraction:\n" + vector1.substract(vector2));
			System.out.println("Point Subtraction:\n" + point1.subtract(point3));
			System.out.println("Scale multiplication:\n" + vector1.scale(6.0));
			System.out.println("Cross product:\n" + vector1.crossProduct(vector2));
			System.out.println("Normalization:\n" + vector2.normalization());
			System.out.println("Dot Product:\n" + vector2.dotProduct(vector2));
			System.out.println("norm:\n" + vector2.normalization().length());
		}
		
		catch(IllegalArgumentException e){
			System.out.println(e.getMessage());
		}
		

	}

}

