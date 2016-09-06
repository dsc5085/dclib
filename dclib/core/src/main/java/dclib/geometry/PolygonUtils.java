package dclib.geometry;

import com.badlogic.gdx.math.Vector2;

public final class PolygonUtils {

    public static final int NUM_TRIANGLE_VERTICES = 3;

	private PolygonUtils() {
	}

	public static final Vector2 relativeCenter(final Vector2 pivot, final Vector2 size) {
		Vector2 halfSize = size.cpy().scl(0.5f);
		return pivot.cpy().sub(halfSize);
	}

}
