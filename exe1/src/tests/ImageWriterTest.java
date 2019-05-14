/**
 * 
 */
package tests;

import org.junit.Test;

import primitives.Color;
import renderer.ImageWriter;

/**
 * testing the image writer
 */
public class ImageWriterTest {

	@Test
	public void test() {
		Color blackColor = Color.BLACK;
		Color whiteColor = Color.WHITE;
		
		ImageWriter imageWriter = new ImageWriter("gridWithNothing", 500, 500, 500, 500);
		//to check if the current row is a row of the grid
		int pixelCheckRows = 49;

		for (int i = 0; i < 500; i++) {
			if (i == pixelCheckRows) {
				//in case we are in a grid row iteration - paint the whole row
				for (int j = 0; j < 500; j++)
					imageWriter.writePixel(i, j, whiteColor.getColor());
				//move the next row check 50 pixels forward
				pixelCheckRows += 50;
			} else {
				//in case we are not in a painted row iteration - we check the columns
				int pixelCheckColumns = 49;
				for (int j = 0; j < 500; j++) {
					if (j == pixelCheckColumns) {
						//in case its a column that should be painted - paint the pixel
						imageWriter.writePixel(i, j, whiteColor.getColor());
						//and move the column check value 50 pixels forward
						pixelCheckColumns += 50;
					} else {
						//otherwise - paint black the pixels
						imageWriter.writePixel(i, j, blackColor.getColor());
					}
				}
			}
		}
		//write the final image
		imageWriter.writeToImage();
	}

}
