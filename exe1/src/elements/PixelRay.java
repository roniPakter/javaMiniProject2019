package elements;

import java.util.List;

import primitives.Color;
import primitives.Ray;

/**
 *Represents a ray in adaptive-sampled pixel with the ray color
 */
public class PixelRay {
	/**list of rays (for DOF rays) in one point on the pixel */
	public List<Ray> rayBeam;
	/**the color of the point on the pixel - null until colored by the renderer*/
	public Color color;

	// ***************** Constructor ******************** //
	/**
	 * construct a point in the pixel with its rays beam
	 * @param rays - list of rays
	 */
	public PixelRay(List<Ray> rays) {
		rayBeam = rays;
	}
}
