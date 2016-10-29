package dclib.physics.collision;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;

import dclib.eventing.Event;
import dclib.physics.Contacter;

public class CollidedEvent implements Event<CollidedListener> {

	private final Contact contact;
	private final Contacter source;
	private final Contacter target;

	public CollidedEvent(final Contact contact, final Contacter source, final Contacter target) {
		this.contact = contact;
		this.source = source;
		this.target = target;
	}

	public final Vector2 getNormal() {
		return contact.getWorldManifold().getNormal();
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
