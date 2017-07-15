package dclib.util;

import com.badlogic.gdx.math.MathUtils;

public final class Maths {

    public static final float DEGREES_MAX = 360;
	public static final float RADIANS_MAX = MathUtils.PI2;

	private Maths() {
	}

	public static final boolean between(final float value, final float min, final float max) {
		return value >= min && value <= max;
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

	public static final float degDistance(final float deg1, final float deg2) {
        float phi = distance(deg1, deg2) % DEGREES_MAX;
        return phi > DEGREES_MAX / 2 ? DEGREES_MAX - phi : phi;
    }

	/**
	 * Rounds down to the nearest number that is divisible by the interval length.
	 * @param value value to round down from
	 * @param intervalLength length between each interval
	 * @return rounded down value
	 */
	public static final float round(final float value, final float intervalLength) {
		return value - value % intervalLength;
	}

}
