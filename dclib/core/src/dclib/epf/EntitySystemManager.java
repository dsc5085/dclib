package dclib.epf;

import java.util.ArrayList;
import java.util.List;

public final class EntitySystemManager {
	
	private final List<EntitySystem> systems = new ArrayList<EntitySystem>();
	
	public EntitySystemManager(final EntityManager entityManager) {
		entityManager.addEntityAddedListener(entityAdded());
		entityManager.addEntityRemovedListener(entityRemoved());
	}
	
	public final void add(final EntitySystem system) {
		systems.add(system);
	}
	
	public final void dispose() {
		for (EntitySystem system : systems) {
			system.dispose();
		}
	}
	
	public final void update(final float delta, final Entity entity) {
		for (EntitySystem system : systems) {
			system.update(delta, entity);
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
					system.cleanup(entity);
				}
			}
		};
	}

}
