package dclib.geometry;

import com.badlogic.gdx.math.Vector2;

public final class VectorUtils {
	
	private VectorUtils() {
	}
	
	public static final Vector2 unit(final Vector2 vector) {
		float length = vector.len();
		return vector.cpy().scl(1 / length);
	}
	
	public static final Vector2 lengthened(final Vector2 vector, final float length) {
		return vector.cpy().scl(length / vector.len());
	}
	
	public static final float distance(final Vector2 vector1, final Vector2 vector2) {
		return offset(vector1, vector2).len();
	}
	
	public static final Vector2 offset(final Vector2 from, final Vector2 to) {
		return to.cpy().sub(from);
	}
	
	public static final Vector2 fromAngle(final float degrees) {
		return fromAngleAndLength(degrees, 1);
	}
	
	public static final Vector2 fromAngleAndLength(final float degrees, final float length) {
		Vector2 vector = new Vector2(length, 0);
		return vector.rotate(degrees);
	}
	
}
