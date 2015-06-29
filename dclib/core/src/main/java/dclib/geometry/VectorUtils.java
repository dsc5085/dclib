package dclib.geometry;

import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public final class VectorUtils {
	
	/**
	 * Buffer used for floating point calculations involving vectors.
	 * Vector calculations don't always round well to exact numbers, so this buffer can be used to check if any 
	 * attribute of the vector (e.g. x, y, length) is near the expected number.
	 */
	public static final float BUFFER = 1e-5f;
	
	private VectorUtils() {
	}
	
	public static final Vector2 unit(final Vector2 vector) {
		float length = vector.len();
		return vector.cpy().scl(1 / length);
	}
	
	public static final Vector2 lengthened(final Vector2 vector, final float length) {
		return vector.cpy().scl(length / vector.len());
	}
	
	public static final Vector2 offset(final Vector2 from, final Vector2 to) {
		return to.cpy().sub(from);
	}

	public static final float angleToPoint(final Vector2 from, final Vector2 to) {
		Vector2 offset = offset(from, to);
		return MathUtils.atan2(offset.y, offset.x);
	}
	
	public static final Vector2 fromAngle(final float degrees) {
		return fromAngle(degrees, 1);
	}
	
	public static final Vector2 fromAngle(final float degrees, final float length) {
		Vector2 vector = new Vector2(length, 0);
		return vector.rotate(degrees);
	}
	
	public static final float getCumulativeDistance(final List<Vector2> vectors) {
		float distance = 0;
		for (int i = 1; i < vectors.size(); i++) {
			distance += offset(vectors.get(i - 1), vectors.get(i)).len();
		}
		return distance;
	}
	
}
