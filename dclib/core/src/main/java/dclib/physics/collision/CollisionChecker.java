package dclib.physics.collision;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.eventing.EventDelegate;
import dclib.physics.Contacter;
import dclib.system.Updater;
import dclib.util.CollectionUtils;

public final class CollisionChecker implements Updater {

	private final EventDelegate<CollidedListener> collidedDelegate = new EventDelegate<CollidedListener>();

	private final EntityManager entityManager;
	private final World world;
	
	public CollisionChecker(final EntityManager entityManager, final World world) {
		this.entityManager = entityManager;
		this.world = world;
	}

	public final void listen(final CollidedListener listener) {
		collidedDelegate.listen(listener);
	}

	@Override
	public final void update(final float delta) {
		Map<Contacter, Set<Contacter>> colliderToCollidees = getColliderToCollidees();
		for (Map.Entry<Contacter, Set<Contacter>> colliderToCollideesEntry : colliderToCollidees.entrySet()) {
			Contacter collider = colliderToCollideesEntry.getKey();
			if (collider.getEntity().isActive()) {
				for (Contacter collidee : colliderToCollideesEntry.getValue()) {
					if (collidee.getEntity().isActive()) {
						collidedDelegate.notify(new CollidedEvent(collider, collidee));
					}
				}
			}
		}
	}

	private Map<Contacter, Set<Contacter>> getColliderToCollidees() {
		Map<Contacter, Set<Contacter>> colliderToCollidees = new HashMap<Contacter, Set<Contacter>>();
		for (Contact contact : world.getContactList()) {
			if (contact.isTouching()) {
				Contacter e1 = createContacter(contact.getFixtureA());
				Contacter e2 = createContacter(contact.getFixtureB());
				if (e1 != null && e2 != null) {
					CollectionUtils.get(colliderToCollidees, e1, new HashSet<Contacter>()).add(e2);
					CollectionUtils.get(colliderToCollidees, e2, new HashSet<Contacter>()).add(e1);
				}
			}
		}
		return colliderToCollidees;
	}
	
	private Contacter createContacter(final Fixture fixture) {
		for (Entity entity : entityManager.getAll()) {
			if (fixture != null && fixture.getBody().getUserData() == entity) {
				return new Contacter(fixture.getBody(), entity);
			}
		}
		return null;
	}
	
}
