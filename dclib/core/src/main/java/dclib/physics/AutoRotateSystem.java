package dclib.physics;

import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.epf.EntitySystem;
import dclib.epf.parts.AutoRotatePart;
import dclib.epf.parts.TransformPart;
import dclib.geometry.VectorUtils;

public final class AutoRotateSystem extends EntitySystem {

	public AutoRotateSystem(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected final void update(final float delta, final Entity entity) {
		AutoRotatePart autoRotatePart = entity.tryGet(AutoRotatePart.class);
		if (autoRotatePart != null) {
			Transform transform = entity.get(TransformPart.class).getTransform();
			Vector2 center = transform.getCenter();
			Vector2 oldCenter = autoRotatePart.getOldCenter();
			if (oldCenter != null) {
				Vector2 offset = VectorUtils.offset(oldCenter, center);
				transform.setCenteredRotation(offset.angle());
			}
			autoRotatePart.setOldCenter(center);
		}
	}

}
