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
import dclib.epf.parts.LimbsPart;
import dclib.epf.parts.PhysicsPart;
import dclib.epf.parts.TransformPart;
import dclib.epf.parts.TranslatePart;
import dclib.eventing.EventDelegate;
import dclib.geometry.PolygonFactory;
import dclib.geometry.RectangleUtils;
import dclib.physics.BodyType;
import dclib.physics.CollidedEvent;
import dclib.physics.CollidedListener;

public final class PhysicsSystem {

	private final EventDelegate<CollidedListener> collidedDelegate = new EventDelegate<CollidedListener>();

	private final EntityManager entityManager;
	private final float gravity;

	public PhysicsSystem(final EntityManager entityManager, final float gravity) {
		this.entityManager = entityManager;
		this.gravity = gravity;
		addCollidedListener(collided());
	}

	public final void addCollidedListener(final CollidedListener listener) {
		collidedDelegate.listen(listener);
	}

	public final void update(final float delta) {
		List<Entity> entities = getEntities();
		checkCollisions(entities);
		applyGravity(entities, delta);
	}

	private final CollidedListener collided() {
		return new CollidedListener() {
			@Override
			public void collided(final Entity collider, final Entity collidee, final Vector2 offset) {
				BodyType colliderBodyType = collider.get(PhysicsPart.class).getBodyType();
				BodyType collideeBodyType = collidee.get(PhysicsPart.class).getBodyType();
				if (colliderBodyType == BodyType.DYNAMIC && collideeBodyType == BodyType.STATIC) {
					collider.get(TransformPart.class).translate(offset);
					TranslatePart translatePart = collider.get(TranslatePart.class);
					if (offset.x != 0) {
						translatePart.setVelocityX(0);
					}
					if (offset.y != 0) {
						translatePart.setVelocityY(0);
					}
				}
			}
		};
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

	private void checkCollisions(final List<Entity> entities) {
		for (int i = 0; i < entities.size() - 1; i++) {
			for (int j = i + 1; j < entities.size(); j++) {
				checkCollision(entities.get(i), entities.get(j));
			}
		}
	}

	private void checkCollision(final Entity e1, final Entity e2) {
		// TODO: Cache these collision polygons
		Polygon polygon1 = getCollisionPolygon(e1);
		Polygon polygon2 = getCollisionPolygon(e2);
		Vector2 offset = getTranslationOffset(polygon1, polygon2);
		if (offset.len() > 0) {
			collidedDelegate.notify(new CollidedEvent(e1, e2, offset.cpy().scl(-1)));
			collidedDelegate.notify(new CollidedEvent(e2, e1, offset));
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

	private Vector2 getTranslationOffset(final Polygon collider, final Polygon collidee) {
		Vector2 offset = new Vector2();
		MinimumTranslationVector translation = new MinimumTranslationVector();
		if (Intersector.overlapConvexPolygons(collider, collidee, translation)) {
			offset = translation.normal.cpy().scl(translation.depth);
			offset.setLength(translation.depth);
		}
		return offset;
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
