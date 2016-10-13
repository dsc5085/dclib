package dclib.mechanics;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.google.common.base.Predicate;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.epf.parts.CollisionRemovePart;
import dclib.physics.collision.CollidedEvent;
import dclib.physics.collision.CollidedListener;

public final class RemoveCollidedListener implements CollidedListener {

	private final EntityManager entityManager;
	private final Predicate<CollidedEvent> filter;

	public RemoveCollidedListener(final EntityManager entityManager, final Predicate<CollidedEvent> filter) {
		this.entityManager = entityManager;
		this.filter = filter;
	}

	@Override
	public final void collided(final CollidedEvent event) {
		Entity sourceEntity = event.getSource().getEntity();
		CollisionRemovePart collisionRemovePart = sourceEntity.tryGet(CollisionRemovePart.class);
		if (collisionRemovePart != null) {
			boolean isTargetStatic = event.getTarget().getBody().getType() == BodyType.StaticBody;
			if (isTargetStatic || filter.apply(event)) {
				entityManager.remove(sourceEntity);
			}
		}
	}

}
