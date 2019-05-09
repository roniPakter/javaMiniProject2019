/**
 * 
 */
package tests;

import org.junit.Test;

import primitives.Color;
import renderer.ImageWriter;
import renderer.Render;

/**
 * @author ronip
 *
 */
public class ImageWriterTest {

	@Test
	public void test() {
		Color blackColor = new Color();
		Color whiteColor = new Color(255, 255, 255);
		Color redColor = new Color( 255,0,0);
		ImageWriter imageWriter = new ImageWriter("gridWithNothing", 500, 500, 500, 500);
		int pixelCheckRows = 49;

		for (int i = 0; i < 500; i++) {
			if (i == pixelCheckRows) {
				for (int j = 0; j < 500; j++)
					imageWriter.writePixel(i, j, whiteColor.getColor());
				pixelCheckRows += 50;
			} else {
				int pixelCheckColumns = 49;
				for (int j = 0; j < 500; j++) {
					if (j == pixelCheckColumns) {
						imageWriter.writePixel(i, j, whiteColor.getColor());
						pixelCheckColumns += 50;
					} else {
						imageWriter.writePixel(i, j, blackColor.getColor());
					}
				}
			}
		}
	}

}
