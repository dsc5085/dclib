package dclib.epf.systems;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.system.Updater;

public abstract class EntitySystem implements Updater {

	private final EntityManager entityManager;

	public EntitySystem(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public final void update(final float delta) {
		for (Entity entity : entityManager.getAll()) {
			if (entity.isActive()) {
				update(delta, entity);
			}
		}
	}

	protected abstract void update(final float delta, final Entity entity);

}
