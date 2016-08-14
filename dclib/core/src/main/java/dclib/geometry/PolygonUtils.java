package dclib.geometry;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public final class PolygonUtils {

	private PolygonUtils() {
	}

	public static final Vector2 size(final Polygon polygon) {
		float rotation = polygon.getRotation();
		polygon.setRotation(0);
		Vector2 size = polygon.getBoundingRectangle().getSize(new Vector2());
		polygon.setRotation(rotation);
		return size;
	}

	public static final Vector2 center(final Polygon polygon) {
		return polygon.getBoundingRectangle().getCenter(new Vector2());
	}

	public static final Vector2 relativeCenter(final Vector2 pivot, final Vector2 size) {
		Vector2 halfObjectSize = size.cpy().scl(0.5f);
		return pivot.cpy().sub(halfObjectSize);
	}

	/**
	 * Convert a point local to the polygon to a point in global space.
	 * @param local local position within the untransformed polyon
	 * @param polygon polygon
	 * @return global point
	 */
	public static Vector2 toGlobal(final Vector2 local, final Polygon polygon) {
		return toGlobal(local.x, local.y, polygon);
	}

	/**
	 * Translates the polygon so that the local point of the polygon is at the global point.
	 * @param polygon polygon
	 * @param local local point
	 * @param newGlobal new global point
	 */
	public static final void setGlobal(final Polygon polygon, final Vector2 local, final Vector2 newGlobal) {
		Vector2 currentGlobal = toGlobal(local.x, local.y, polygon);
		Vector2 offset = VectorUtils.offset(currentGlobal, newGlobal);
		polygon.translate(offset.x, offset.y);
	}

	/**
	 * Convert a point local to the polygon to a point in global space.
	 * @param localX local X within the untransformed polygon
	 * @param localY local Y within the untransformed polygon
	 * @param polygon polygon
	 * @return global point
	 */
	public static Vector2 toGlobal(final float localX, final float localY, final Polygon polygon) {
		return new Vector2(localX, localY)
		.sub(polygon.getOriginX(), polygon.getOriginY())
		.scl(polygon.getScaleX(), polygon.getScaleY())
		.rotate(polygon.getRotation())
		.add(polygon.getX(), polygon.getY())
		.add(polygon.getOriginX(), polygon.getOriginY());
	}

}
