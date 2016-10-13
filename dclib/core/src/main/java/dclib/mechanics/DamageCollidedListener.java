package dclib.mechanics;

import java.util.function.Predicate;

import dclib.epf.parts.CollisionDamagePart;
import dclib.epf.parts.HealthPart;
import dclib.physics.collision.CollidedEvent;
import dclib.physics.collision.CollidedListener;

public final class DamageCollidedListener implements CollidedListener {

	private final Predicate<CollidedEvent> collisionPredicate;

	public DamageCollidedListener(final Predicate<CollidedEvent> collisionPredicate) {
		this.collisionPredicate = collisionPredicate;
	}

	@Override
	public final void collided(final CollidedEvent event) {
		CollisionDamagePart collisionDamagePart = event.getSource().getEntity().tryGet(CollisionDamagePart.class);
		HealthPart targetHealthPart = event.getTarget().getEntity().tryGet(HealthPart.class);
		if (collisionDamagePart != null && targetHealthPart != null) {
			if (collisionPredicate.test(event)) {
				targetHealthPart.decrease(collisionDamagePart.getDamage());
			}
		}
	}

}
