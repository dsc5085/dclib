package dclib.collision;

import dclib.epf.parts.CollisionDamagePart;
import dclib.epf.parts.HealthPart;
import dclib.physics.Contacter;

public final class DamageCollidedListener implements CollidedListener {

	@Override
	public final void collided(final Contacter collider, final Contacter collidee) {
		CollisionDamagePart collisionDamagePart = collider.getEntity().tryGet(CollisionDamagePart.class);
		HealthPart collideeHealthPart = collidee.getEntity().tryGet(HealthPart.class);
		if (collisionDamagePart != null && collideeHealthPart != null) {
			if (collidee.getEntity().isAny(collisionDamagePart.getCollisionGroups())) {
				collideeHealthPart.decrease(collisionDamagePart.getDamage());
			}
		}
	}

}
