package dclib.physics;

import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.epf.EntitySystem;
import dclib.epf.parts.TransformPart;

public final class TranslateSystem extends EntitySystem {

	public TranslateSystem(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected final void update(final float delta, final Entity entity) {
		TransformPart transformPart = entity.tryGet(TransformPart.class);
		if (transformPart != null) {
			Transform transform = entity.get(TransformPart.class).getTransform();
			// TODO: Figure out an alternative to reflection (instanceof)
			if (transform instanceof DefaultTransform) {
				Vector2 offset = transform.getVelocity().scl(delta);
				transform.translate(offset);
			}
		}
	}

}
