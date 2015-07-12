package dclib.geometry;

import com.badlogic.gdx.math.Rectangle;

public final class RectangleUtils {

	private RectangleUtils() {
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
	
	/**
	 * Translates the y of the rectangle a specified amount while keeping the top of the rectangle the same.
	 * @param rectangle rectangle
	 * @param offsetY amount to translate y
	 */
	public static final void translateY(final Rectangle rectangle, final float offsetY) {
		rectangle.setY(rectangle.y + offsetY);
		rectangle.setHeight(rectangle.height - offsetY);
	}
	
}
