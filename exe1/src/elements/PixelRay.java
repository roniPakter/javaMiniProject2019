package elements;

import java.util.List;

import primitives.Color;
import primitives.Ray;

public class PixelRay {

	public List<Ray> rayBeam;
	public Color color;

	// ***************** Constructor ******************** //
	public PixelRay(List<Ray> rays) {
		rayBeam = rays;
	}
	
	public void setColor(Color colorParm) {
		color = colorParm;
	}
}
