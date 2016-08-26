package dclib.epf.systems;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
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

public final class PhysicsSystem {

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

	public final void update(final float delta) {
		List<Entity> entities = getEntities();
		processStaticCollisions(entities);
		processNormalCollisions(entities);
		applyGravity(entities, delta);
	}

	private List<Entity> getEntities() {
		List<Entity> entities = new ArrayList<Entity>();
		for (Entity entity : entityManager.getAll()) {
			if (entity.has(PhysicsPart.class)) {
				entities.add(entity);
			}
		}
		return entities;
	}

	private void processStaticCollisions(final List<Entity> entities) {
		for (Entity entity : entities) {
			PhysicsPart physicsPart = entity.get(PhysicsPart.class);
			if (physicsPart.getBodyType() == BodyType.DYNAMIC) {
				List<Vector2> offsets = new ArrayList<Vector2>();
				Polygon collisionPolygon = getCollisionPolygon(entity);
				Entity staticEntity = null;
				for (Entity other : entityManager.getAll()) {
					Vector2 offset = processStaticCollision(entity, other, collisionPolygon);
					if (offset.len() > 0) {
						offsets.add(offset);
						staticEntity = other;
					}
				}
				processOffsets(entity, staticEntity, offsets);
			}
		}
	}

	private Vector2 processStaticCollision(final Entity entity, final Entity other, final Polygon collisionPolygon) {
		TransformPart transformPart = entity.get(TransformPart.class);
		if (other.get(PhysicsPart.class).getBodyType() == BodyType.STATIC) {
			Polygon otherPolygon = other.get(TransformPart.class).getPolygon();
			Vector2 offset = getTranslationOffset(collisionPolygon, otherPolygon);
			if (offset.len() > 0) {
				collisionPolygon.translate(offset.x, offset.y);
				transformPart.translate(offset);
				return offset;
			}
		}
		return new Vector2();
	}

	private void processOffsets(final Entity e1, final Entity e2, final List<Vector2> offsets) {
		Vector2 summedOffset = new Vector2();
		for (Vector2 offset : offsets) {
			summedOffset.add(offset);
		}
		if (!offsets.isEmpty()) {
			bodyCollidedDelegate.notify(new BodyCollidedEvent(e1, e2, summedOffset));
			bounce(summedOffset, e1.get(TranslatePart.class));
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

	private void processNormalCollisions(final List<Entity> entities) {
		for (int i = 0; i < entities.size() - 1; i++) {
			for (int j = i + 1; j < entities.size(); j++) {
				processNormalCollision(entities.get(i), entities.get(j));
			}
		}
	}

	private void processNormalCollision(final Entity e1, final Entity e2) {
		final BodyType[] collisionBodyTypes = new BodyType[] { BodyType.DYNAMIC, BodyType.SENSOR };
		PhysicsPart physicsPart1 = e1.get(PhysicsPart.class);
		PhysicsPart physicsPart2 = e2.get(PhysicsPart.class);
		boolean canE1Collide = ArrayUtils.contains(collisionBodyTypes, physicsPart1.getBodyType());
		boolean canE2Collide = ArrayUtils.contains(collisionBodyTypes, physicsPart2.getBodyType());
		if (canE1Collide && canE2Collide) {
			Polygon polygon1 = e1.get(TransformPart.class).getPolygon();
			Polygon polygon2 = e2.get(TransformPart.class).getPolygon();
			if (Intersector.overlapConvexPolygons(polygon1, polygon2)) {
				bodyCollidedDelegate.notify(new BodyCollidedEvent(e1, e2, new Vector2()));
				bodyCollidedDelegate.notify(new BodyCollidedEvent(e2, e1, new Vector2()));
			}
		}
	}

	private void applyGravity(final List<Entity> entities, final float delta) {
		for (Entity entity : entities) {
			PhysicsPart physicsPart = entity.get(PhysicsPart.class);
			if (physicsPart.getBodyType() == BodyType.DYNAMIC) {
				float gravityScale = entity.get(PhysicsPart.class).getGravityScale();
				float velocityY = gravity * gravityScale * delta;
				entity.get(TranslatePart.class).addVelocity(0, velocityY);
			}
		}
	}

}
