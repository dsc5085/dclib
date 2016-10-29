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
				Contacter c1 = createContacter(contact.getFixtureA());
				Contacter c2 = createContacter(contact.getFixtureB());
				if (c1 != null && c2 != null) {
					collidedDelegate.notify(new CollidedEvent(c1, c2));
					collidedDelegate.notify(new CollidedEvent(c2, c1));
				}
			}
		}
	}

	private Contacter createContacter(final Fixture fixture) {
		Contacter contacter = null;
		if (fixture != null && fixture.getBody() != null) {
			Entity entity = (Entity)fixture.getBody().getUserData();
			if (entity != null && entity.isActive()) {
				contacter = new Contacter(fixture, entity);
			}
		}
		return contacter;
	}

}
