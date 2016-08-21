package dclib.epf;

import java.util.Collection;
import java.util.List;

public interface EntityManager {

	void addEntityAddedListener(final EntityAddedListener listener);
	void addEntityRemovedListener(final EntityRemovedListener listener);
	boolean contains(final Entity entity);
	List<Entity> getAll();
	void add(final Entity entity);
	void addAll(final Collection<Entity> entities);
	void remove(final Entity entity);
	void removeAll(final Collection<Entity> entities);
	void dispose();

}
