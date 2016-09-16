package dclib.epf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import dclib.eventing.EventDelegate;

/**
 * Manages a group of entities.  Provides accessing, removing, and adding entities.
 * @author David Chen
 *
 */
public final class DefaultEntityManager implements EntityManager {

	private final EventDelegate<EntityAddedListener> entityAddedDelegate = new EventDelegate<EntityAddedListener>();
	private final EventDelegate<EntityRemovedListener> entityRemovedDelegate
	= new EventDelegate<EntityRemovedListener>();

	private final List<Entity> entities = new ArrayList<Entity>();

	public DefaultEntityManager(final Entity...entities) {
		for (Entity entity : entities) {
			add(entity);
		}
	}

	@Override
	public final void listen(final EntityAddedListener listener) {
		entityAddedDelegate.listen(listener);
	}

	@Override
	public final void listen(final EntityRemovedListener listener) {
		entityRemovedDelegate.listen(listener);
	}

	@Override
	public final boolean contains(final Entity entity) {
		return entities.contains(entity);
	}

	/**
	 * @return all managed entities
	 */
	@Override
	public final List<Entity> getAll() {
		return new ArrayList<Entity>(entities);
	}

	/**
	 * Adds and manages an entity.
	 * @param entity the entity to add and manage
	 */
	@Override
	public final void add(final Entity entity) {
		entity.setActive(true);
		if (entities.contains(entity)) {
			throw new IllegalArgumentException("Could not add entity " + entity + ".  It already exists");
		}
		entities.add(entity);
		entityAddedDelegate.notify(new EntityAddedEvent(entity));
	}

	/**
	 * Adds the entities in the passed in collection.
	 * @param entities entities to add and manage
	 */
	@Override
	public final void addAll(final Collection<Entity> entities) {
		for (Entity entity : entities) {
			add(entity);
		}
	}

	/**
	 * Removes an entity.
	 * @param entity entity to remove
	 */
	@Override
	public final void remove(final Entity entity) {
		if (entities.remove(entity)) {
			entity.setActive(false);
			entityRemovedDelegate.notify(new EntityRemovedEvent(entity));
		}
	}

	/**
	 * Removes the entities in the passed in collection.
	 * @param entities entities to remove
	 */
	@Override
	public void removeAll(final Collection<Entity> entities) {
		for (Entity entity : entities) {
			remove(entity);
		}
	}

	@Override
	public final void dispose() {
		Iterator<Entity> it = entities.iterator();
		while (it.hasNext()) {
			Entity entity = it.next();
			entity.setActive(false);
			it.remove();
		}
	}

}
