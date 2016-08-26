package dclib.physics;

import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.eventing.Event;

public final class BodyCollidedEvent implements Event<BodyCollidedListener> {

	private final Entity collider;
	private final Entity collidee;
	private final Vector2 offset;

	public BodyCollidedEvent(final Entity collider, final Entity collidee, final Vector2 offset) {
		this.collider = collider;
		this.collidee = collidee;
		this.offset = offset;
	}

	@Override
	public final void notify(final BodyCollidedListener listener) {
		listener.collided(collider, collidee, offset);
	}

}
