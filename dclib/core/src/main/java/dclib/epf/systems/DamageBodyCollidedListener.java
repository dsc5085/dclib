package dclib.epf.systems;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.physics.BodyCollidedListener;

public final class DamageBodyCollidedListener implements BodyCollidedListener {

	@Override
	public void collided(final Entity entity, final List<Vector2> offset) {
//		if (entity.hasActive(CollisionDamagePart.class)) {
//			CollisionDamagePart damageOnCollisionPart = entity.get(CollisionDamagePart.class);
//			int[] damageCollisionTypes = damageOnCollisionPart.getCollisionTypes();
//			if (damageCollisionTypes.contains(otherCollisionTypePart.getCollisionType())) {
//				entity.get(HealthPart.class).decrease(value);
////						other.get(HealthPart.class).decrease(damageOnCollisionPart.getDamage());
//			}
//		}
	}

}
