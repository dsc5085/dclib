package dclib.epf;

/**
 * Listener for entity create event.
 * @author David Chen
 *
 */
public interface EntityAddedListener {

	/**
	 * Handles the event that an entity is about to be created.
	 * @param entity entity to be created
	 */
	void added(final Entity entity);
	
}
