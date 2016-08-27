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
		if (collider.hasActive(CollisionDamagePart.class) && collidee.hasActive(PhysicsPart.class)) {
			CollisionDamagePart collisionDamagePart = collider.get(CollisionDamagePart.class);
			PhysicsPart physicsPart = collidee.get(PhysicsPart.class);
			if (physicsPart.containsAny(collisionDamagePart.getCollisionTypes())) {
				collidee.get(HealthPart.class).decrease(collisionDamagePart.getDamage());
			}
		}
	}

}
