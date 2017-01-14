package dclib.util;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;

public final class FloatRange {

	private final float min;
	private final float max;

	public FloatRange(final float a, final float b) {
		this.min = Math.min(a, b);
		this.max = Math.max(a, b);
	}

	public final float min() {
		return min;
	}

	public final float max() {
		return max;
	}

	public final float difference() {
		return max - min;
	}

	public final float clamp(final float value) {
		return MathUtils.clamp(value, min, max);
	}

	public final float interpolate(final float value) {
		return Interpolation.linear.apply(min, max, value);
	}

	public final float random() {
		return MathUtils.random(min, max);
	}

}
