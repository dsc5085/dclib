package dclib.physics.collision;

import dclib.eventing.Event;
import dclib.physics.Contacter;

public class CollidedEvent implements Event<CollidedListener> {

	private final Contacter collider;
	private final Contacter collidee;
	
	public CollidedEvent(final Contacter collider, final Contacter collidee) {
		this.collider = collider;
		this.collidee = collidee;
	}
	
	@Override
	public final void notify(final CollidedListener listener) {
		listener.collided(collider, collidee);
	}

}
