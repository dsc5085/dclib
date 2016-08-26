package dclib.epf.systems;

import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.epf.EntitySystem;
import dclib.epf.parts.TransformPart;
import dclib.epf.parts.TranslatePart;

public final class TranslateSystem extends EntitySystem {

	@Override
	public final void update(final float delta, final Entity entity) {
		if (entity.hasActive(TranslatePart.class)) {
			TransformPart transformPart = entity.get(TransformPart.class);
			Vector2 offset = entity.get(TranslatePart.class).getVelocity().scl(delta);
			Vector2 newPosition = transformPart.getPosition().add(offset);
			transformPart.setPosition(newPosition);
		}
	}

}
