package dclib.epf.systems;

import java.util.List;

import com.badlogic.gdx.math.Polygon;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.epf.EntityRemovedListener;
import dclib.epf.EntitySystem;
import dclib.epf.parts.LimbAnimationsPart;
import dclib.epf.parts.LimbsPart;
import dclib.epf.parts.TransformPart;

public final class LimbsSystem extends EntitySystem {

	private final EntityManager entityManager;

	public LimbsSystem(final EntityManager entityManager) {
		this.entityManager = entityManager;
		entityManager.addEntityRemovedListener(entityRemoved());
	}

	@Override
	public final void update(final float delta, final Entity entity) {
		if (entity.hasActive(LimbsPart.class)) {
			entity.get(LimbsPart.class).update();
		}
		if (entity.hasActive(LimbAnimationsPart.class)) {
			entity.get(LimbAnimationsPart.class).update(delta);
		}
	}

	private EntityRemovedListener entityRemoved() {
		return new EntityRemovedListener() {
			@Override
			public void removed(final Entity removedEntity) {
				if (removedEntity.has(LimbsPart.class)) {
					List<Polygon> descendantPolygons = removedEntity.get(LimbsPart.class).getRoot().getDescendants();
					for (Entity entity : entityManager.getAll()) {
						if (entity.has(TransformPart.class)) {
							if (descendantPolygons.contains(entity.get(TransformPart.class).getPolygon())) {
								entityManager.remove(entity);
							}
						}
					}
				}
			}
		};
	}

}
