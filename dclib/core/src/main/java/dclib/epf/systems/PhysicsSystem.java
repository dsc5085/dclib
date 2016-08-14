package dclib.epf.systems;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.epf.EntitySystem;
import dclib.epf.parts.PhysicsPart;
import dclib.epf.parts.TransformPart;
import dclib.epf.parts.TranslatePart;
import dclib.eventing.EventDelegate;
import dclib.physics.BodyCollidedEvent;
import dclib.physics.BodyCollidedListener;
import dclib.physics.BodyType;

public final class PhysicsSystem extends EntitySystem {

	private final EventDelegate<BodyCollidedListener> bodyCollidedDelegate = new EventDelegate<BodyCollidedListener>();

	private final EntityManager entityManager;
	private final float gravity;

	public PhysicsSystem(final EntityManager entityManager, final float gravity) {
		this.entityManager = entityManager;
		this.gravity = gravity;
	}

	public final void addBodyCollidedListener(final BodyCollidedListener listener) {
		bodyCollidedDelegate.listen(listener);
	}

	@Override
	public final void update(final float delta, final Entity entity) {
		if (entity.get(PhysicsPart.class).getBodyType() == BodyType.DYNAMIC) {
			entity.get(TranslatePart.class).addVelocity(0, gravity * delta);
			processBodyCollisions(entity);
		}
	}

	private void processBodyCollisions(final Entity dynamicEntity) {
		for (Entity staticEntity : entityManager.getAll()) {
			if (staticEntity.get(PhysicsPart.class).getBodyType() == BodyType.STATIC) {
				Polygon staticPolygon = staticEntity.get(TransformPart.class).getPolygon();
				processBodyCollision(dynamicEntity, staticPolygon);
			}
		}
	}

	private void processBodyCollision(final Entity dynamicEntity, final Polygon staticPolygon) {
		TransformPart transformPart = dynamicEntity.get(TransformPart.class);
		MinimumTranslationVector translation = new MinimumTranslationVector();
		if (Intersector.overlapConvexPolygons(transformPart.getPolygon(), staticPolygon, translation)) {
			TranslatePart translatePart = dynamicEntity.get(TranslatePart.class);
			Vector2 currentVelocity = translatePart.getVelocity();
			float normalXSign = -Math.signum(currentVelocity.x);
			float normalYSign = -Math.signum(currentVelocity.y);
			Vector2 offset = translation.normal.cpy().scl(normalXSign, normalYSign);
			offset.setLength(translation.depth);
			bounce(translation, translatePart);
			bodyCollidedDelegate.notify(new BodyCollidedEvent(dynamicEntity, offset));
			transformPart.translate(offset);
		}
	}

	private void bounce(final MinimumTranslationVector translation, final TranslatePart translatePart) {
		final float bounceDampening = 0.001f;
		Vector2 currentVelocity = translatePart.getVelocity();
		if (translation.normal.x != 0) {
			translatePart.setVelocityX(-currentVelocity.x * bounceDampening);
		}
		if (translation.normal.y != 0) {
			translatePart.setVelocityY(-currentVelocity.y * bounceDampening);
		}
	}

}
