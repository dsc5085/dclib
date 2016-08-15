package dclib.util;

import com.badlogic.gdx.math.MathUtils;

public final class IntRange {

	private final int min;
	private final int max;

	public IntRange(final int min, final int max) {
		if (min > max) {
			throw new IllegalArgumentException("Min argument of " + min
					+ " cannot be greater than max argument of " + max);
		}
		this.min = min;
		this.max = max;
	}

	public final int min() {
		return min;
	}

	public final int max() {
		return max;
	}

	public final int difference() {
		return max - min;
	}

	public final float clamp(final int value) {
		return MathUtils.clamp(value, min, max);
	}

	public final int random() {
		return MathUtils.random(min, max);
	}

}
