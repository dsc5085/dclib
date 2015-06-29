package dclib.epf;

import dclib.eventing.Event;

/**
 * Notify that an entity is ready to be removed.
 * @author David Chen
 *
 */
public final class EntityRemovedEvent implements Event<EntityRemovedListener> {

	private final Entity entity;
	
	/**
	 * Constructor.
	 * @param entity entity to be removed
	 */
	public EntityRemovedEvent(final Entity entity) {
		this.entity = entity;
	}
	
	@Override
	public final void notify(final EntityRemovedListener listener) {
		listener.removed(entity);
	}

}
