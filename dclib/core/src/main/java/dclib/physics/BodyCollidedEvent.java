package dclib.physics;

import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.eventing.Event;

public final class BodyCollidedEvent implements Event<BodyCollidedListener> {

	private final Entity entity;
	private final Vector2 offset;

	public BodyCollidedEvent(final Entity entity, final Vector2 offset) {
		this.entity = entity;
		this.offset = offset;
	}

	@Override
	public final void notify(final BodyCollidedListener listener) {
		listener.collided(entity, offset);
	}

}
