package dclib.geometry;

import com.badlogic.gdx.math.Vector2;
import dclib.physics.Transform;

public final class Centrum {

	private final Transform transform;
	private final Vector2 local;

	public Centrum(final Transform transform, final Vector2 local) {
		this.transform = transform;
		this.local = local;
	}

	public final Vector2 getPosition() {
        return transform.toWorld(local);
    }

	public final float getRotation() {
		return transform.getRotation();
	}

}
