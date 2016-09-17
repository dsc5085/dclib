package dclib.gamelogic;

import dclib.epf.Entity;
import dclib.epf.EntityAddedListener;
import dclib.epf.EntityManager;
import dclib.epf.parts.HealthPart;
import dclib.eventing.DefaultListener;

public final class RemoveOnNoHealthEntityAddedListener implements EntityAddedListener {

	private final EntityManager entityManager;

	public RemoveOnNoHealthEntityAddedListener(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public final void added(final Entity entity) {
		HealthPart healthPart = entity.tryGet(HealthPart.class);
		if (healthPart != null) {
			healthPart.listen(noHealth(entity));
		}
	}

	private DefaultListener noHealth(final Entity entity) {
		return new DefaultListener() {
			@Override
			public void executed() {
				entityManager.remove(entity);
			}
		};
	}

}
