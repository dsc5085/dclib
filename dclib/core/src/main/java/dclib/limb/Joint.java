package dclib.limb;

import com.badlogic.gdx.math.Vector2;

public final class Joint {

	private final Limb limb;
	private final Vector2 parentLocal;
	private final Vector2 childLocal;
	private float rotation;

	public Joint(final Limb limb, final Vector2 parentLocal, final Vector2 childLocal, final float rotation) {
		this.limb = limb;
		this.parentLocal = parentLocal;
		this.childLocal = childLocal;
		this.rotation = rotation;
	}

	public final Limb getLimb() {
		return limb;
	}

	public final Vector2 getParentLocal() {
		return parentLocal.cpy();
	}

	public final Vector2 getChildLocal() {
		return childLocal.cpy();
	}

	public final float getRotation() {
		return rotation;
	}

	public final void setRotation(final float rotation) {
		this.rotation = rotation;
	}

}
