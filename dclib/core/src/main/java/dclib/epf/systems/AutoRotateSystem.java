package dclib.epf.systems;

import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.epf.EntitySystem;
import dclib.epf.parts.AutoRotatePart;
import dclib.epf.parts.TransformPart;
import dclib.geometry.VectorUtils;

public final class AutoRotateSystem extends EntitySystem {

	@Override
	public final void initialize(final Entity entity) {
		if (entity.hasActive(AutoRotatePart.class)) {
			Vector2 oldPosition = entity.get(TransformPart.class).getPosition();
			entity.get(AutoRotatePart.class).setOldPosition(oldPosition);
		}
	}

	@Override
	public final void update(final float delta, final Entity entity) {
		if (entity.hasActive(AutoRotatePart.class)) {
			AutoRotatePart autoRotatePart = entity.get(AutoRotatePart.class);
			TransformPart transformPart = entity.get(TransformPart.class);
			Vector2 oldPosition = autoRotatePart.getOldPosition();
			Vector2 offset = VectorUtils.offset(oldPosition, transformPart.getPosition());
			transformPart.setCenteredRotation(offset.angle());
			autoRotatePart.setOldPosition(transformPart.getPosition());
		}
	}

}
