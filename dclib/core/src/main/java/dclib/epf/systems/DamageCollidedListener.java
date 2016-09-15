package dclib.epf.systems;

import dclib.epf.parts.CollisionDamagePart;
import dclib.epf.parts.CollisionPart;
import dclib.epf.parts.HealthPart;
import dclib.physics.CollidedListener;
import dclib.physics.Contacter;

public final class DamageCollidedListener implements CollidedListener {

	@Override
	public final void collided(final Contacter collider, final Contacter collidee) {
		CollisionDamagePart collisionDamagePart = collider.getEntity().tryGet(CollisionDamagePart.class);
		CollisionPart collideePart = collidee.getEntity().tryGet(CollisionPart.class);
		HealthPart collideeHealthPart = collidee.getEntity().tryGet(HealthPart.class);
		if (collisionDamagePart != null && collideePart != null && collideeHealthPart != null) {
			if (collideePart.containsAny(collisionDamagePart.getCollisionGroups())) {
				collideeHealthPart.decrease(collisionDamagePart.getDamage());
			}
		}
	}

}
