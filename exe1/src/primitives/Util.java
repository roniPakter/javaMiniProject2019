/*
 * Aharon Packter ID 201530508
 * Shlomo Perlov ID 206914301
 * 25/03/2019
 * 
 * Mini project in Software Engineering
 * Exercise 2
 */
package primitives;

import java.util.Random;

public abstract class Util {
	// It is binary, equivalent to ~1/1,000,000,000,000 in decimal (12 digits)
	private static final int ACCURACY = -40;
	private static Random rand = new Random();

	// double store format (bit level): seee eeee eeee (1.)mmmm � mmmm
	// 1 bit sign, 11 bits exponent, 53 bits (52 stored) normalized mantissa
	// the number is m+2^e where 1<=m<2
	// NB: exponent is stored "normalized" (i.e. always positive by adding 1023)
	private static int getExp(double num) {
		// 1. doubleToRawLongBits: "convert" the stored number to set of bits
		// 2. Shift all 52 bits to the right (removing mantissa)
		// 3. Zero the sign of number bit by mask 0x7FF
		// 4. "De-normalize" the exponent by subtracting 1023
		return (int)((Double.doubleToRawLongBits(num) >> 52) & 0x7FFL) - 1023;
	}

	public static double usubtract(double lhs, double rhs) {
		int lhsExp = getExp(lhs);
		int rhsExp = getExp(rhs);

		// if other is too small relatively to our coordinate
		// return the original coordinate
		if (rhsExp - lhsExp < ACCURACY) return lhs;

		// if our coordinate is too small relatively to other
		// return negative of other coordinate
		if (lhsExp - rhsExp < ACCURACY) return -rhs;

		double result = lhs - rhs;
		int resultExp = getExp(result);
		// if the result is relatively small - tell that it is zero
		return resultExp - lhsExp < ACCURACY ? 0.0 : result;
	}

	public static double uadd(double lhs, double rhs) {
		int lhsExp = getExp(lhs);
		int rhsExp = getExp(rhs);

		// if other is too small relatively to our coordinate
		// return the original coordinate
		if (rhsExp - lhsExp < ACCURACY) return lhs;

		// if our coordinate is too small relatively to other
		// return other coordinate
		if (lhsExp - rhsExp < ACCURACY) return rhs;

		double result = lhs + rhs;
		int resultExp = getExp(result);
		// if the result is relatively small - tell that it is zero
		return resultExp - lhsExp < ACCURACY ? 0.0 : result;
	}

	public static double uscale(double lhs, double factor) {
		double deltaExp = getExp(factor - 1);
		return deltaExp < ACCURACY ? lhs : lhs * factor;
	}

	public static boolean isZero(double number) {
		return getExp(number) < ACCURACY;
	}

	public static boolean isOne(double number) {
		return getExp(number - 1) < ACCURACY;
	}

	public static double alignZero(double number) {
		return getExp(number) < ACCURACY ? 0.0 : number;
	}
	
	/**
	 * Ruffles a double number between -1 and 1 but is not zero
	 * @return a random notZero double 
	 */
	public static double getNotZeroRandom() {
		double result = (rand.nextDouble() * 2d) - 1d;
		while (isZero(result)) {
			result = (rand.nextDouble() * 2d) - 1d;
		}
		return result;
	}
	
	/**
	 * Ruffles a double number between 0 and 1 but is not zero
	 * @return a random double 
	 */
	public static double getNotZeroPositiveRandom() {
		double result = rand.nextDouble();
		while (isZero(result)) {
			result = rand.nextDouble();
		}
		return result;
	}
}
