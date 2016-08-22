package dclib.epf.systems;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.epf.EntitySystem;
import dclib.epf.parts.LimbsPart;
import dclib.epf.parts.PhysicsPart;
import dclib.epf.parts.TransformPart;
import dclib.epf.parts.TranslatePart;
import dclib.eventing.EventDelegate;
import dclib.geometry.PolygonFactory;
import dclib.geometry.VectorUtils;
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
		PhysicsPart physicsPart = entity.get(PhysicsPart.class);
		if (physicsPart.getBodyType() == BodyType.DYNAMIC) {
			processBodyCollisions(entity);
			entity.get(TranslatePart.class).addVelocity(0, gravity * physicsPart.getGravityScale() * delta);
			updateCollisionBounds(entity);
		}
	}

	private void updateCollisionBounds(final Entity entity) {
		if (entity.has(LimbsPart.class)) {
			LimbsPart limbsPart = entity.get(LimbsPart.class);
			Rectangle bounds = limbsPart.getCollisionBounds();
			TransformPart transformPart = entity.get(TransformPart.class);
			float[] vertices = PolygonFactory.createRectangleVertices(bounds.width, bounds.height);
			// TODO: Don't replace all vertices.  Just update size.
			transformPart.getPolygon().setVertices(vertices);
			Vector2 offset = VectorUtils.offset(bounds.getPosition(new Vector2()), transformPart.getPosition());
			limbsPart.getRoot().translate(offset.x, offset.y);
		}
	}

	private void processBodyCollisions(final Entity entity) {
		List<Vector2> offsets = new ArrayList<Vector2>();
		for (Entity staticEntity : entityManager.getAll()) {
			if (staticEntity.get(PhysicsPart.class).getBodyType() == BodyType.STATIC) {
				Polygon staticPolygon = staticEntity.get(TransformPart.class).getPolygon();
				Vector2 offset = getTranslationOffset(entity, staticPolygon);
				if (offset.len() > 0) {
					bounce(offset, entity.get(TranslatePart.class));
					entity.get(TransformPart.class).translate(offset);
					offsets.add(offset);
				}
			}
		}
		if (!offsets.isEmpty()) {
			bodyCollidedDelegate.notify(new BodyCollidedEvent(entity, offsets));
		}
	}

	private Vector2 getTranslationOffset(final Entity entity, final Polygon staticPolygon) {
		Vector2 offset = new Vector2();
		TransformPart transformPart = entity.get(TransformPart.class);
		MinimumTranslationVector translation = new MinimumTranslationVector();
		if (Intersector.overlapConvexPolygons(transformPart.getPolygon(), staticPolygon, translation)) {
			TranslatePart translatePart = entity.get(TranslatePart.class);
			Vector2 currentVelocity = translatePart.getVelocity();
			float normalXSign = -Math.signum(currentVelocity.x);
			float normalYSign = -Math.signum(currentVelocity.y);
			offset = translation.normal.cpy().scl(normalXSign, normalYSign);
			offset.setLength(translation.depth);
		}
		return offset;
	}

	private void bounce(final Vector2 offset, final TranslatePart translatePart) {
		final float bounceDampening = 0.001f;
		Vector2 currentVelocity = translatePart.getVelocity();
		if (offset.x != 0) {
			translatePart.setVelocityX(-currentVelocity.x * bounceDampening);
		}
		if (offset.y != 0) {
			translatePart.setVelocityY(-currentVelocity.y * bounceDampening);
		}
	}

}
