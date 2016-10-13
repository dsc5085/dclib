package dclib.physics.collision;

import dclib.eventing.Event;
import dclib.physics.Contacter;

public class CollidedEvent implements Event<CollidedListener> {

	private final Contacter source;
	private final Contacter target;

	public CollidedEvent(final Contacter source, final Contacter target) {
		this.source = source;
		this.target = target;
	}

	public final Contacter getSource() {
		return source;
	}

	public final Contacter getTarget() {
		return target;
	}

	@Override
	public final void notify(final CollidedListener listener) {
		listener.collided(this);
	}

}
