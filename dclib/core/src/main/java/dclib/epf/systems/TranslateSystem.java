package dclib.epf.systems;

import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.epf.parts.TransformPart;
import dclib.epf.parts.TranslatePart;

public final class TranslateSystem extends EntitySystem {

	public TranslateSystem(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected final void update(final float delta, final Entity entity) {
		if (entity.has(TranslatePart.class)) {
			TransformPart transformPart = entity.get(TransformPart.class);
			Vector2 offset = entity.get(TranslatePart.class).getVelocity().scl(delta);
			Vector2 newPosition = transformPart.getPosition().add(offset);
			transformPart.setPosition(newPosition);
		}
	}

}
