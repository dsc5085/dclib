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
		TranslatePart translatePart = entity.tryGet(TranslatePart.class);
		if (translatePart != null) {
			TransformPart transformPart = entity.get(TransformPart.class);
			Vector2 offset = translatePart.getVelocity().scl(delta);
			transformPart.translate(offset);
		}
	}

}
