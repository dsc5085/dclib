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
import dclib.epf.parts.CollisionPart;
import dclib.epf.parts.LimbsPart;
import dclib.epf.parts.TransformPart;
import dclib.eventing.EventDelegate;
import dclib.geometry.PolygonFactory;
import dclib.geometry.RectangleUtils;
import dclib.physics.CollidedListener;
import dclib.physics.Collision;
import dclib.physics.CollisionFilter;
import dclib.system.Updater;

public final class CollisionSystem implements Updater {

	private final EventDelegate<CollidedListener> collidedDelegate = new EventDelegate<CollidedListener>();

	private final EntityManager entityManager;
	private final List<CollisionFilter> filters = new ArrayList<CollisionFilter>();
	private final List<Collision> collisions = new ArrayList<Collision>();

	public CollisionSystem(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public final void addCollidedListener(final CollidedListener listener) {
		collidedDelegate.listen(listener);
	}

	public final void add(final CollisionFilter filter) {
		filters.add(filter);
	}

	public final List<Collision> getCollisions(final Entity collider) {
		List<Collision> colliderCollisions = new ArrayList<Collision>();
		for (Collision collision : collisions) {
			if (collision.getCollider() == collider) {
				colliderCollisions.add(collision);
			}
		}
		return colliderCollisions;
	}

	@Override
	public final void update(final float delta) {
		collisions.clear();
		checkCollisions(entityManager.getAll());
	}

	private void checkCollisions(final List<Entity> entities) {
		System.out.println(entities.size());
		for (int i = 0; i < entities.size() - 1; i++) {
			Entity e1 = entities.get(i);
			for (int j = i + 1; j < entities.size(); j++) {
				Entity e2 = entities.get(j);
				checkCollision(e1, e2);
			}
		}
	}

	private void checkCollision(final Entity e1, final Entity e2) {
		if (e1.has(CollisionPart.class) && e2.has(CollisionPart.class) && !shouldFilter(e1, e2)) {
			// TODO: Cache these collision polygons
			Polygon polygon1 = getCollisionPolygon(e1);
			Polygon polygon2 = getCollisionPolygon(e2);
			Vector2 offset1 = getTranslationOffset(polygon1, polygon2);
			if (offset1.len() > 0) {
				notifyCollided(e1, e2, offset1);
				Vector2 offset2 = getTranslationOffset(polygon2, polygon1);
				notifyCollided(e2, e1, offset2);
			}
		}
	}

	private boolean shouldFilter(final Entity e1, final Entity e2) {
		for (CollisionFilter filter : filters) {
			if (filter.shouldFilter(e1, e2)) {
				return true;
			}
		}
		return false;
	}

	private void notifyCollided(final Entity collider, final Entity collidee, final Vector2 offset) {
		Collision collision = new Collision(collider, collidee, offset);
		collidedDelegate.notify(collision);
		collisions.add(collision);
	}

	private Polygon getCollisionPolygon(final Entity entity) {
		Polygon boundsPolygon;
		LimbsPart limbsPart = entity.tryGet(LimbsPart.class);
		if (limbsPart != null) {
			List<Polygon> collisionPolygons = limbsPart.getCollisionPolygons();
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
			offset = translation.normal.cpy();
			offset.setLength(translation.depth);
		}
		return offset;
	}

}
