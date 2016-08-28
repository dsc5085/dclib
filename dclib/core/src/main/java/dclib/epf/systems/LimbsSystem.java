package dclib.epf.systems;

import java.util.List;

import com.badlogic.gdx.math.Polygon;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.epf.EntityRemovedListener;
import dclib.epf.parts.LimbAnimationsPart;
import dclib.epf.parts.LimbsPart;
import dclib.epf.parts.TransformPart;
import dclib.epf.util.LimbUtils;
import dclib.limb.Limb;

public final class LimbsSystem extends EntitySystem {

	private final EntityManager entityManager;

	public LimbsSystem(final EntityManager entityManager) {
		super(entityManager);
		this.entityManager = entityManager;
		entityManager.addEntityRemovedListener(entityRemoved());
	}

	@Override
	protected final void update(final float delta, final Entity entity) {
		LimbsPart limbsPart = entity.tryGet(LimbsPart.class);
		if (limbsPart != null) {
			limbsPart.update();
		}
		LimbAnimationsPart limbAnimationsPart = entity.tryGet(LimbAnimationsPart.class);
		if (limbAnimationsPart != null) {
			limbAnimationsPart.update(delta);
		}
	}

	private EntityRemovedListener entityRemoved() {
		return new EntityRemovedListener() {
			@Override
			public void removed(final Entity removedEntity) {
				TransformPart transformPart = removedEntity.tryGet(TransformPart.class);
				if (transformPart != null) {
					// TODO: Limb relationships are messy.  Cleanup
					Polygon polygon = transformPart.getPolygon();
					List<Entity> entities = entityManager.getAll();
					entities.add(removedEntity);
					for (Entity entity : entities) {
						if (entity.has(LimbsPart.class)) {
							if (removeLimb(polygon, entities, entity)) {
								break;
							}
						}
					}
				}
			}

			private boolean removeLimb(final Polygon limbPolygon, final List<Entity> entities, final Entity parent) {
				Limb limb = parent.get(LimbsPart.class).remove(limbPolygon);
				boolean found = limb != null;
				if (found) {
					for (Limb descendantLimb : limb.getDescendants()) {
						Entity descendant = LimbUtils.findEntity(entities, descendantLimb);
						entityManager.remove(descendant);
					}
				}
				return found;
			}
		};
	}

}
