package dclib.epf;

import java.util.ArrayList;
import java.util.List;

public final class DefaultEntitySystemManager implements EntitySystemManager {
	
	private final EntityManager entityManager;
	private final List<EntitySystem> systems = new ArrayList<EntitySystem>();
	
	public DefaultEntitySystemManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
		entityManager.addEntityAddedListener(entityAdded());
		entityManager.addEntityRemovedListener(entityRemoved());
	}
	
	@Override
	public final void add(final EntitySystem system) {
		// TODO: check if the system already exists, then throw exception
		systems.add(system);
	}
	
	@Override
	public final void update(final float delta) {
		for (EntitySystem system : systems) {
			for (Entity entity : entityManager.getAll()) {
				if (entity.isActive()) {
					system.update(delta, entity);
				}
			}
		}
	}
	
	private EntityAddedListener entityAdded() {
		return new EntityAddedListener() {
			@Override
			public void created(final Entity entity) {
				for (EntitySystem system : systems) {
					system.initialize(entity);
				}
			}
		};
	}
	
	private EntityRemovedListener entityRemoved() {
		return new EntityRemovedListener() {
			@Override
			public void removed(final Entity entity) {
				for (EntitySystem system : systems) {
					system.dispose(entity);
				}
			}
		};
	}

}
