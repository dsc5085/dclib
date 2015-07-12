package dclib.util;

public final class Maths {
	
	/**
	 * Buffer used for floating point calculations.
	 * Floating point calculations don't always round well to exact numbers, so this buffer can be used to check if any 
	 * attribute of the vector (e.g. x, y, length) is near the expected number.
	 */
	public static final float EPSILON = 1e-5f;

	private Maths() {
	}
	
	/**
	 * Rounds down to the nearest integer that is divisible by the interval length.
	 * @param value value to round down from
	 * @param intervalLength length between each interval
	 * @return rounded down value
	 */
	public static int round(final int value, final int intervalLength) {
		return value - value % intervalLength;
	}
	
	public static boolean isBetween(final int value, final int min, final int max) {
		return value >= min && value <= max;
	}
	
}
