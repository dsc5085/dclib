package dclib.epf.systems;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.epf.parts.TimedDeathPart;

public final class TimedDeathSystem extends EntitySystem {

	private final EntityManager entityManager;

	public TimedDeathSystem(final EntityManager entityManager) {
		super(entityManager);
		this.entityManager = entityManager;
	}

	@Override
	public void update(final float delta, final Entity entity) {
		if (entity.hasActive(TimedDeathPart.class)) {
			TimedDeathPart timedDeathPart = entity.get(TimedDeathPart.class);
			timedDeathPart.update(delta);
			if (timedDeathPart.isDead()) {
				entityManager.remove(entity);
			}
		}
	}

}
