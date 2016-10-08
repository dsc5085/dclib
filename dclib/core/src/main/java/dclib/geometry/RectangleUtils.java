package dclib.geometry;

import com.badlogic.gdx.math.Rectangle;

import dclib.util.Maths;

public final class RectangleUtils {

	private RectangleUtils() {
	}

	public static final boolean containsX(final Rectangle rectangle, final float x) {
		return Maths.between(x, rectangle.x, right(rectangle));
	}

	public static final float top(final Rectangle rectangle) {
		return rectangle.y + rectangle.height;
	}

	public static final float right(final Rectangle rectangle) {
		return rectangle.x + rectangle.width;
	}

	public static final Rectangle scale(final Rectangle rectangle, final float scale) {
		rectangle.width *= scale;
		rectangle.height *= scale;
		rectangle.x *= scale;
		rectangle.y *= scale;
		return rectangle;
	}

}
