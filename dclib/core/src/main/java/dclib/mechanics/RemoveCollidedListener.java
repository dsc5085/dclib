package dclib.mechanics;

import java.util.function.Predicate;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.epf.parts.CollisionRemovePart;
import dclib.physics.collision.CollidedEvent;
import dclib.physics.collision.CollidedListener;

public final class RemoveCollidedListener implements CollidedListener {

	private final EntityManager entityManager;
	private final Predicate<CollidedEvent> collisionPredicate;

	public RemoveCollidedListener(final EntityManager entityManager, final Predicate<CollidedEvent> collisionPredicate) {
		this.entityManager = entityManager;
		this.collisionPredicate = collisionPredicate;
	}

	@Override
	public final void collided(final CollidedEvent event) {
		Entity sourceEntity = event.getSource().getEntity();
		CollisionRemovePart collisionRemovePart = sourceEntity.tryGet(CollisionRemovePart.class);
		if (collisionRemovePart != null) {
			boolean isTargetStatic = event.getTarget().getBody().getType() == BodyType.StaticBody;
			if (isTargetStatic || collisionPredicate.test(event)) {
				entityManager.remove(sourceEntity);
			}
		}
	}

}
