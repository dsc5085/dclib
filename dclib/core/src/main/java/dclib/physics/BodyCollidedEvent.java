package dclib.physics;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.eventing.Event;

public final class BodyCollidedEvent implements Event<BodyCollidedListener> {

	private final Entity entity;
	private final List<Vector2> offsets;

	public BodyCollidedEvent(final Entity entity, final List<Vector2> offsets) {
		this.entity = entity;
		this.offsets = offsets;
	}

	@Override
	public final void notify(final BodyCollidedListener listener) {
		listener.collided(entity, offsets);
	}

}
