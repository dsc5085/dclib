package dclib.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import dclib.geometry.VectorUtils;

public abstract class Transform {

	private final float z;

	public Transform(final float z) {
		this.z = z;
	}

	public final float getZ() {
		return z;
	}

	public abstract float[] getVertices();

	public abstract Vector2 getOrigin();

	public abstract Vector2 getScale();

	public abstract void setScale(final Vector2 scale);

	public abstract Vector2 getSize();

    public final Vector2 getWorldSize() {
        return getSize().scl(getScale());
    }

	public abstract Vector2 getPosition();

	public final Vector3 getPosition3() {
		Vector2 position = getPosition();
		return new Vector3(position.x, position.y, z);
	}

	public abstract void setPosition(final Vector2 position);

	public final Vector2 getCenter() {
		return getBounds().getCenter(new Vector2());
	}

	public final void translate(final Vector2 offset) {
		Vector2 newPosition = getPosition().add(offset);
		setPosition(newPosition);
	}

	public abstract Rectangle getBounds();

	public abstract float getRotation();

	public abstract void setRotation(final float degrees);

	public final void setCenteredRotation(final float degrees) {
		Vector2 oldCenter = getCenter();
		setRotation(degrees);
		Vector2 offset = VectorUtils.offset(getCenter(), oldCenter);
		translate(offset);
	}

	public final Vector2 toLocal(final Vector2 world) {
		Vector2 scale = getScale();
		return world.cpy()
				.sub(getOrigin())
				.sub(getPosition())
				.rotate(-getRotation())
				.scl(VectorUtils.inv(scale))
				.add(getOrigin());
	}

	/**
     * Convert a point local to the origin to a point in world space.
     * @param local local point within the untransformed polygon
     * @return world point
     */
    public final Vector2 toWorld(final Vector2 local) {
        return local.cpy()
		.sub(getOrigin())
		.scl(getScale())
		.rotate(getRotation())
		.add(getPosition())
		.add(getOrigin());
	}

	/**
     * Translate so that the local point of at the world point.
     * @param local local point
     * @param newWorld new world point
     */
    public final void setWorld(final Vector2 local, final Vector2 newWorld) {
        Vector2 currentWorld = toWorld(local);
        Vector2 offset = VectorUtils.offset(currentWorld, newWorld);
        translate(offset);
	}

	public abstract Vector2 getVelocity();

	public abstract void setVelocity(final Vector2 velocity);

	public abstract void applyImpulse(final Vector2 impulse);

}
