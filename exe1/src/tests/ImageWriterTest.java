/**
 * 
 */
package tests;

import org.junit.Test;

import primitives.Color;
import renderer.ImageWriter;

/**
 * @author ronip
 *
 */
public class ImageWriterTest {

	@Test
	public void test() {
		Color blackColor = new Color();
		Color whiteColor = new Color(255,255,255);
		Color redColor = new Color(255,0,0);
		ImageWriter imageWriter = new ImageWriter("gridWithNothing", 500, 500, 500, 500);
		for (int i = 0; i < 500; i++) {
			for (int j = 0; j < 500; j++) {
				imageWriter.writePixel(i, j, redColor.getColor());
			}
		}
		imageWriter.writeToImage();
	}

}
