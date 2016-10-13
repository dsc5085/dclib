package dclib.mechanics;

import com.google.common.base.Predicate;

import dclib.epf.parts.CollisionDamagePart;
import dclib.epf.parts.HealthPart;
import dclib.physics.collision.CollidedEvent;
import dclib.physics.collision.CollidedListener;

public final class DamageCollidedListener implements CollidedListener {

	private final Predicate<CollidedEvent> filter;

	public DamageCollidedListener(final Predicate<CollidedEvent> filter) {
		this.filter = filter;
	}

	@Override
	public final void collided(final CollidedEvent event) {
		CollisionDamagePart collisionDamagePart = event.getSource().getEntity().tryGet(CollisionDamagePart.class);
		HealthPart targetHealthPart = event.getTarget().getEntity().tryGet(HealthPart.class);
		if (collisionDamagePart != null && targetHealthPart != null) {
			if (filter.apply(event)) {
				targetHealthPart.decrease(collisionDamagePart.getDamage());
			}
		}
	}

}
