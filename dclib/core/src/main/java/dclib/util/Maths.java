package dclib.util;

public final class Maths {
	
	private Maths() {
	}
	
	public static float distance(final float value1, final float value2) {
		return Math.abs(value1 - value2);
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
	
	public static boolean isBetween(final float value, final float min, final float max) {
		return value >= min && value <= max;
	}
	
}
