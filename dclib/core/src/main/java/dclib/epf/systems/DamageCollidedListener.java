package dclib.epf.systems;

import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.epf.parts.CollisionDamagePart;
import dclib.epf.parts.CollisionPart;
import dclib.epf.parts.HealthPart;
import dclib.physics.CollidedListener;

public final class DamageCollidedListener implements CollidedListener {

	@Override
	public final void collided(final Entity collider, final Entity collidee, final Vector2 offsets) {
		CollisionDamagePart collisionDamagePart = collider.tryGet(CollisionDamagePart.class);
		CollisionPart collideePart = collidee.tryGet(CollisionPart.class);
		if (collisionDamagePart != null && collideePart != null) {
			if (collideePart.containsAny(collisionDamagePart.getCollisionGroups())) {
				collidee.get(HealthPart.class).decrease(collisionDamagePart.getDamage());
			}
		}
	}

}
