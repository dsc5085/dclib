package dclib.geometry;

import com.badlogic.gdx.math.Vector2;

public abstract class Transform {

	private final float z;

	public Transform(final float z) {
		this.z = z;
	}

	public final float getZ() {
		return z;
	}

	public abstract Vector2 getOrigin();

	public abstract Vector2 getScale();

	public abstract void setScale(final Vector2 scale);

	public abstract Vector2 getSize();

	public abstract Vector2 getPosition();

	public abstract Vector2 getCenter();

	public abstract void translate(final Vector2 offset);

	public abstract float getRotation();

	public abstract void setRotation(final float degrees);

	public final void setCenteredRotation(final float degrees) {
		Vector2 oldCenter = getCenter();
		setRotation(degrees);
		Vector2 offset = VectorUtils.offset(getCenter(), oldCenter);
		translate(offset);
	}

	/**
	 * Convert a point local to the polygon to a point in global space.
	 * @param localX local X within the untransformed polygon
	 * @param localY local Y within the untransformed polygon
	 * @return global point
	 */
	public final Vector2 toGlobal(final Vector2 local) {
		return local.cpy()
		.sub(getOrigin())
		.scl(getScale())
		.rotate(getRotation())
		.add(getPosition())
		.add(getOrigin());
	}

	/**
	 * Translates the polygon so that the local point of the polygon is at the global point.
	 * @param local local point
	 * @param newGlobal new global point
	 */
	public final void setGlobal(final Vector2 local, final Vector2 newGlobal) {
		Vector2 currentGlobal = toGlobal(local);
		Vector2 offset = VectorUtils.offset(currentGlobal, newGlobal);
		translate(offset);
	}

}
