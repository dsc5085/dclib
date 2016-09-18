package dclib.physics;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.epf.EntitySystem;
import dclib.epf.parts.AutoRotatePart;
import dclib.epf.parts.TransformPart;

public final class AutoRotateSystem extends EntitySystem {

	public AutoRotateSystem(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected final void update(final float delta, final Entity entity) {
		if (entity.has(AutoRotatePart.class)) {
			Transform transform = entity.get(TransformPart.class).getTransform();
			float velocityAngle = transform.getVelocity().angle();
			transform.setCenteredRotation(velocityAngle);
		}
	}

}
