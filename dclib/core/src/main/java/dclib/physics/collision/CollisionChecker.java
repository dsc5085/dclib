package dclib.physics.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import dclib.epf.Entity;
import dclib.eventing.EventDelegate;
import dclib.physics.Contacter;
import dclib.system.Updater;

public final class CollisionChecker implements Updater {

	private final EventDelegate<CollidedListener> collidedDelegate = new EventDelegate<CollidedListener>();

	private final World world;

	public CollisionChecker(final World world) {
		this.world = world;
	}

	public final void listen(final CollidedListener listener) {
		collidedDelegate.listen(listener);
	}

	@Override
	public final void update(final float delta) {
		for (Contact contact : world.getContactList()) {
			if (contact.isTouching()) {
				Contacter e1 = createContacter(contact.getFixtureA());
				Contacter e2 = createContacter(contact.getFixtureB());
				if (e1 != null && e1.getEntity().isActive() && e2 != null && e2.getEntity().isActive()) {
					collidedDelegate.notify(new CollidedEvent(e1, e2));
					collidedDelegate.notify(new CollidedEvent(e2, e1));
				}
			}
		}
	}

	private Contacter createContacter(final Fixture fixture) {
		Contacter contacter = null;
		if (fixture != null) {
			Entity entity = (Entity)fixture.getBody().getUserData();
			if (entity != null) {
				contacter = new Contacter(fixture, entity);
			}
		}
		return contacter;
	}

}
