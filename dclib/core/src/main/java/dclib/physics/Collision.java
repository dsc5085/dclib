package dclib.physics;

import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.eventing.Event;

public final class Collision implements Event<CollidedListener> {

	private final Entity collider;
	private final Entity collidee;
	private final Vector2 offset;

	public Collision(final Entity collider, final Entity collidee, final Vector2 offset) {
		this.collider = collider;
		this.collidee = collidee;
		this.offset = offset;
	}

	public final Entity getCollider() {
		return collider;
	}

	public final Entity getCollidee() {
		return collidee;
	}

	public final Vector2 getOffset() {
		return offset.cpy();
	}

	@Override
	public final void notify(final CollidedListener listener) {
		listener.collided(collider, collidee, offset);
	}

}
