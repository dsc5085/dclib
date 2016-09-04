package dclib.epf.systems;

import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.epf.parts.LimbsPart;
import dclib.epf.parts.PhysicsPart;
import dclib.epf.parts.TransformPart;
import dclib.epf.parts.TranslatePart;
import dclib.physics.BodyType;
import dclib.physics.CollidedListener;

public final class PhysicsSystem extends EntitySystem {
	private final float gravity;

	public PhysicsSystem(final float gravity, final EntityManager entityManager,
			final CollisionSystem collisionSystem) {
		super(entityManager);
		this.gravity = gravity;
		collisionSystem.addCollidedListener(collided());
	}

	@Override
	protected final void update(final float delta, final Entity entity) {
		if (entity.has(PhysicsPart.class)) {
			applyGravity(entity, delta);
		}
	}

	private CollidedListener collided() {
		return new CollidedListener() {
			@Override
			public void collided(final Entity collider, final Entity collidee, final Vector2 offset) {
				PhysicsPart colliderPhysicsPart = collider.tryGet(PhysicsPart.class);
				PhysicsPart collideePhysicsPart = collidee.tryGet(PhysicsPart.class);
				if (colliderPhysicsPart != null && collideePhysicsPart != null) {
					if (colliderPhysicsPart.getBodyType() == BodyType.DYNAMIC
							&& collideePhysicsPart.getBodyType() == BodyType.STATIC) {
						translate(collider, offset);
						TranslatePart translatePart = collider.get(TranslatePart.class);
						if (offset.x != 0) {
							translatePart.setVelocityX(0);
						}
						if (offset.y != 0) {
							translatePart.setVelocityY(0);
						}
					}
				}
			}
		};
	}

	private void translate(final Entity entity, final Vector2 offset) {
		entity.get(TransformPart.class).translate(offset);
		LimbsPart limbsPart = entity.tryGet(LimbsPart.class);
		if (limbsPart != null) {
			limbsPart.update();
		}
	}

	private void applyGravity(final Entity entity, final float delta) {
		PhysicsPart physicsPart = entity.get(PhysicsPart.class);
		if (physicsPart.getBodyType() == BodyType.DYNAMIC) {
			float gravityScale = entity.get(PhysicsPart.class).getGravityScale();
			Vector2 velocity = new Vector2(0, gravity * gravityScale * delta);
			entity.get(TranslatePart.class).addVelocity(velocity);
		}
	}

}
