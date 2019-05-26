package primitives;

public class Material {
 private double _kD;
 private double _kS;
 private int _nShininess;
 public Material(double kD,double kS,int nShininess) {
	 _kD = kD;
	 _kS = kS;
	 _nShininess = nShininess;	 
 }
}
