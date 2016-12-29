package dclib.geometry;

import com.badlogic.gdx.math.Vector2;

public final class VectorUtils {

	private VectorUtils() {
	}

    public static final Vector2 inv(final Vector2 vector) {
        return new Vector2(1 / vector.x, 1 / vector.y);
    }

    public static final Vector2 sign(final Vector2 vector) {
        return new Vector2(Math.signum(vector.x), Math.signum(vector.y));
    }

	public static final Vector2 unit(final Vector2 vector) {
		float length = vector.len();
		return vector.cpy().scl(1 / length);
	}

	public static final Vector2 offset(final Vector2 from, final Vector2 to) {
		return to.cpy().sub(from);
	}

	/**
	 * Returns the x component of a line between from and to, given the y value.
	 * @param p1
	 * @param p2
	 * @param lineY absolute y of point along line
	 * @return
	 */
	public static final float getLineX(final Vector2 p1, final Vector2 p2, final float lineY) {
		Vector2 offset = offset(p1, p2);
		float slope = offset.y / offset.x;
		return p1.x + (lineY - p1.y) / slope;
	}

	public static final float getLineY(final Vector2 p1, final Vector2 p2, final float lineX) {
		Vector2 offset = offset(p1, p2);
		float slope = offset.y / offset.x;
		return p1.y + (lineX - p1.x) * slope;
	}

    public static final Vector2 toVector2(final float degrees, final float length) {
        return new Vector2(length, 0).setAngle(degrees);
    }

}
