package dclib.geometry;

public final class LinearUtils {

	private LinearUtils() {
	}
	
	public static final float distance(final float rate, final float elapsedTime) {
		return rate * elapsedTime;
	}

	// TODO: rename variables to make more sense
	public static float relativeMiddle(final float pivotLength, final float objectLength) {
		return pivotLength / 2f - objectLength / 2f;
	}
	
}
