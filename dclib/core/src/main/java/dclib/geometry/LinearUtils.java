package dclib.geometry;

public final class LinearUtils {

	private LinearUtils() {
	}

	public static final float distance(final float rate, final float elapsedTime) {
		return rate * elapsedTime;
	}

}
