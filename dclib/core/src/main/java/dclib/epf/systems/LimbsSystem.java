package dclib.epf.systems;

import java.util.List;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.epf.EntityRemovedListener;
import dclib.epf.EntitySystem;
import dclib.epf.parts.LimbAnimationsPart;
import dclib.epf.parts.LimbsPart;
import dclib.epf.util.LimbUtils;
import dclib.limb.Limb;

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
					List<Limb> descendants = removedEntity.get(LimbsPart.class).getRoot().getDescendants();
					List<Entity> entities = entityManager.getAll();
					for (Limb descendant : descendants) {
						Entity limbEntity = LimbUtils.findEntity(entities, descendant);
						entityManager.remove(limbEntity);
					}
				}
			}
		};
	}

}
