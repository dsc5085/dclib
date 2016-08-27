package dclib.epf.systems;

import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.epf.parts.AutoRotatePart;
import dclib.epf.parts.TransformPart;
import dclib.geometry.VectorUtils;

public final class AutoRotateSystem extends EntitySystem {

	public AutoRotateSystem(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public final void update(final float delta, final Entity entity) {
		if (entity.hasActive(AutoRotatePart.class)) {
			AutoRotatePart autoRotatePart = entity.get(AutoRotatePart.class);
			TransformPart transformPart = entity.get(TransformPart.class);
			Vector2 oldPosition = autoRotatePart.getOldPosition();
			if (oldPosition != null) {
				Vector2 offset = VectorUtils.offset(oldPosition, transformPart.getPosition());
				transformPart.setCenteredRotation(offset.angle());
			}
			autoRotatePart.setOldPosition(transformPart.getPosition());
		}
	}

}
