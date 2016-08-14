package dclib.physics;

import com.badlogic.gdx.math.Vector2;

public final class Joint {

	private final Limb limb;
	private final Vector2 local;

	public Joint(final Limb limb, final Vector2 local) {
		this.limb = limb;
		this.local = local;
	}

	public final Limb getLimb() {
		return limb;
	}

	public final Vector2 getLocal() {
		return local;
	}

}
