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
	protected final void update(final float delta, final Entity entity) {
		AutoRotatePart autoRotatePart = entity.tryGet(AutoRotatePart.class);
		if (autoRotatePart != null) {
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
