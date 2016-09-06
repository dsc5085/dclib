package dclib.epf.systems;

import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.epf.parts.TransformPart;
import dclib.epf.parts.TranslatePart;
import dclib.geometry.Transform;

public final class TranslateSystem extends EntitySystem {

	public TranslateSystem(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected final void update(final float delta, final Entity entity) {
		TranslatePart translatePart = entity.tryGet(TranslatePart.class);
		if (translatePart != null) {
			Transform transform = entity.get(TransformPart.class).getTransform();
			Vector2 offset = translatePart.getVelocity().scl(delta);
			transform.translate(offset);
		}
	}

}
