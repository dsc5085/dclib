package dclib.epf.systems;

import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.epf.parts.CollisionDamagePart;
import dclib.epf.parts.HealthPart;
import dclib.epf.parts.PhysicsPart;
import dclib.physics.CollidedListener;

public final class DamageCollidedListener implements CollidedListener {

	@Override
	public final void collided(final Entity collider, final Entity collidee, final Vector2 offsets) {
		CollisionDamagePart collisionDamagePart = collider.tryGet(CollisionDamagePart.class);
		PhysicsPart collideePhysicsPart = collidee.tryGet(PhysicsPart.class);
		if (collisionDamagePart != null && collideePhysicsPart != null) {
			if (collideePhysicsPart.containsAny(collisionDamagePart.getCollisionGroups())) {
				collidee.get(HealthPart.class).decrease(collisionDamagePart.getDamage());
			}
		}
	}

}
