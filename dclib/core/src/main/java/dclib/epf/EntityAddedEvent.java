package dclib.epf;

import dclib.eventing.Event;

/**
 * Notify that an entity is ready to be created.
 * @author David Chen
 *
 */
public final class EntityAddedEvent implements Event<EntityAddedListener> {

	private final Entity entity;
	
	/**
	 * Constructor.
	 * @param entity entity to be created
	 */
	public EntityAddedEvent(final Entity entity) {
		this.entity = entity;
	}
	
	@Override
	public final void notify(final EntityAddedListener listener) {
		listener.created(entity);
	}
	
}
