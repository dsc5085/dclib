package dclib.physics;

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

	public final void addCollidedListener(final CollidedListener listener) {
		collidedDelegate.listen(listener);
	}

	@Override
	public final void update(final float delta) {
		Map<Entity, Set<Entity>> colliderToCollidees = getColliderToCollidees();
		for (Map.Entry<Entity, Set<Entity>> colliderToCollideesEntry : colliderToCollidees.entrySet()) {
			Entity collider = colliderToCollideesEntry.getKey();
			for (Entity collidee : colliderToCollideesEntry.getValue()) {
				// TODO: Also, put the bodys in the CollidedEvent
				collidedDelegate.notify(new CollidedEvent(collider, collidee));
			}
		}
	}

	private Map<Entity, Set<Entity>> getColliderToCollidees() {
		Map<Entity, Set<Entity>> colliderToCollidees = new HashMap<Entity, Set<Entity>>();
		for (Contact contact : world.getContactList()) {
			if (contact.isTouching()) {
				Entity e1 = getEntity(contact.getFixtureA());
				Entity e2 = getEntity(contact.getFixtureB());
				if (e1 != null && e2 != null) {
					CollectionUtils.get(colliderToCollidees, e1, new HashSet<Entity>()).add(e2);
					CollectionUtils.get(colliderToCollidees, e2, new HashSet<Entity>()).add(e1);
				}
			}
		}
		return colliderToCollidees;
	}
	
	private Entity getEntity(final Fixture fixture) {
		for (Entity entity : entityManager.getAll()) {
			if (fixture != null && fixture.getBody().getUserData() == entity) {
				return entity;
			}
		}
		return null;
	}
	
}
