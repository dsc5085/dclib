package dclib.physics;

import dclib.epf.Entity;
import dclib.eventing.Event;

public class CollidedEvent implements Event<CollidedListener> {

	private final Entity collider;
	private final Entity collidee;
	
	public CollidedEvent(final Entity collider, final Entity collidee) {
		this.collider = collider;
		this.collidee = collidee;
	}
	
	@Override
	public final void notify(final CollidedListener listener) {
		listener.collided(collider, collidee);
	}

}
