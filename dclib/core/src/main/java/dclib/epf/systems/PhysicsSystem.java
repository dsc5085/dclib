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
import dclib.geometry.RectangleUtils;
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
			float velocityY = gravity * physicsPart.getGravityScale() * delta;
			entity.get(TranslatePart.class).addVelocity(0, velocityY);
		}
	}

	private void processBodyCollisions(final Entity entity) {
		List<Vector2> offsets = new ArrayList<Vector2>();
		Polygon collisionPolygon = getCollisionPolygon(entity);
		for (Entity staticEntity : entityManager.getAll()) {
			TransformPart transformPart = entity.get(TransformPart.class);
			if (staticEntity.get(PhysicsPart.class).getBodyType() == BodyType.STATIC) {
				Polygon staticPolygon = staticEntity.get(TransformPart.class).getPolygon();
				Vector2 offset = getTranslationOffset(collisionPolygon, staticPolygon);
				if (offset.len() > 0) {
					bounce(offset, entity.get(TranslatePart.class));
					collisionPolygon.translate(offset.x, offset.y);
					transformPart.translate(offset);
					offsets.add(offset);
				}
			}
		}
		if (!offsets.isEmpty()) {
			bodyCollidedDelegate.notify(new BodyCollidedEvent(entity, offsets));
		}
	}

	private Polygon getCollisionPolygon(final Entity entity) {
		Polygon boundsPolygon;
		if (entity.has(LimbsPart.class)) {
			List<Polygon> collisionPolygons = entity.get(LimbsPart.class).getCollisionPolygons();
			boundsPolygon = getBoundsPolygon(collisionPolygons);
		} else {
			boundsPolygon = entity.get(TransformPart.class).getPolygon();
		}
		return boundsPolygon;
	}

	public final Polygon getBoundsPolygon(final List<Polygon> polygons) {
		Vector2 min = new Vector2(Float.MAX_VALUE, Float.MAX_VALUE);
		Vector2 max = new Vector2(-Float.MAX_VALUE, -Float.MAX_VALUE);
		for (Polygon polygon : polygons) {
			Rectangle bounds = polygon.getBoundingRectangle();
			min.x = Math.min(min.x, bounds.x);
			min.y = Math.min(min.y, bounds.y);
			max.x = Math.max(max.x, RectangleUtils.right(bounds));
			max.y = Math.max(max.y, RectangleUtils.top(bounds));
		}
		Rectangle bounds = new Rectangle(min.x, min.y, max.x - min.x, max.y - min.y);
		float[] vertices = PolygonFactory.createRectangleVertices(bounds);
		return new Polygon(vertices);
	}

	private Vector2 getTranslationOffset(final Polygon colliderPolygon, final Polygon staticPolygon) {
		Vector2 offset = new Vector2();
		MinimumTranslationVector translation = new MinimumTranslationVector();
		if (Intersector.overlapConvexPolygons(colliderPolygon, staticPolygon, translation)) {
			offset = translation.normal.cpy().scl(translation.depth);
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
