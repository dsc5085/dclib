package dclib.util;

public final class Maths {

	private Maths() {
	}

	public static final boolean between(final float value, final float x, final float y) {
		return value >= Math.min(x, y) && value <= Math.max(x, y);
	}

	public static final float min(final float currentValue, final float newValue) {
		return Float.isNaN(currentValue) ? newValue : Math.min(currentValue, newValue);
	}

	public static final float max(final float currentValue, final float newValue) {
		return Float.isNaN(currentValue) ? newValue : Math.max(currentValue, newValue);
	}

	public static final float distance(final float value1, final float value2) {
		return Math.abs(value1 - value2);
	}

	/**
	 * Rounds down to the nearest integer that is divisible by the interval length.
	 * @param value value to round down from
	 * @param intervalLength length between each interval
	 * @return rounded down value
	 */
	public static final int round(final int value, final int intervalLength) {
		return value - value % intervalLength;
	}

}
