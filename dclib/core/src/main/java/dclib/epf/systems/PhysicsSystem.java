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
		PhysicsPart physicsPart = entity.tryGet(PhysicsPart.class);
		if (physicsPart != null && physicsPart.getBodyType() == BodyType.DYNAMIC) {
			applyGravity(entity, delta);
			applyDrag(entity, delta);
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
		float gravityScale = entity.get(PhysicsPart.class).getGravityScale();
		Vector2 velocity = new Vector2(0, gravity * gravityScale * delta);
		entity.get(TranslatePart.class).addVelocity(velocity);
	}

	private void applyDrag(final Entity entity, final float delta) {
		TranslatePart translatePart = entity.get(TranslatePart.class);
		Vector2 velocity = translatePart.getVelocity();
		translatePart.setVelocityX(getDragValue(velocity.x, delta));
		translatePart.setVelocityY(getDragValue(velocity.y, delta));
	}

	private float getDragValue(final float value, final float delta) {
		final float drag = 3;
		return Math.max(Math.abs(value) - drag * delta, 0) * Math.signum(value);
	}

}
