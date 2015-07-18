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
public final class EntityManager {
	
	private final EventDelegate<EntityAddedListener> entityAddedDelegate = new EventDelegate<EntityAddedListener>();
	private final EventDelegate<EntityRemovedListener> entityRemovedDelegate
	= new EventDelegate<EntityRemovedListener>();
	
	private final List<Entity> entities = new ArrayList<Entity>();
	
	public final void addEntityAddedListener(final EntityAddedListener listener) {
		entityAddedDelegate.listen(listener);
	}
	
	public final void addEntityRemovedListener(final EntityRemovedListener listener) {
		entityRemovedDelegate.listen(listener);
	}
	
	public final boolean contains(final Entity entity) {
		return entities.contains(entity);
	}
	
	/**
	 * @return all managed entities
	 */
	public final List<Entity> getAll() {
		return new ArrayList<Entity>(entities);
	}
	
	/**
	 * Adds and manages an entity on the next call to update.
	 * @param entity the entity to add and manage
	 */
	public final void add(final Entity entity) {
		entity.setActive(true);
		if (entities.contains(entity)) {
			throw new IllegalStateException("Could not add entity " + entity + ".  It already exists");
		}
		entities.add(entity);
		entityAddedDelegate.notify(new EntityAddedEvent(entity));
	}
	
	/**
	 * Adds the entities in the passed in collection on the next call to update.
	 * @param entities entities to add and manage
	 */
	public final void addAll(final Collection<Entity> entities) {
		for (Entity entity : entities) {
			add(entity);
		}
	}
	
	/**
	 * Removes an entity on the next call to update.
	 * @param entity entity to remove
	 */
	public final void remove(final Entity entity) {
		if (entities.remove(entity)) {
			entity.setActive(false);
			entityRemovedDelegate.notify(new EntityRemovedEvent(entity));
		}
	}
	
	public final void dispose() {
		Iterator<Entity> it = entities.iterator();
		while (it.hasNext()) {
			Entity entity = it.next();
			entity.setActive(false);
			it.remove();
		}
	}
	
}
