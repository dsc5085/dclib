package dclib.physics;

import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.eventing.Event;

public final class CollidedEvent implements Event<CollidedListener> {

	private final Entity collider;
	private final Entity collidee;
	private final Vector2 offset;

	public CollidedEvent(final Entity collider, final Entity collidee, final Vector2 offset) {
		this.collider = collider;
		this.collidee = collidee;
		this.offset = offset;
	}

	@Override
	public final void notify(final CollidedListener listener) {
		listener.collided(collider, collidee, offset);
	}

}
