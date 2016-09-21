package dclib.physics.limb;

import java.util.List;

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
		super(entityManager);
		this.entityManager = entityManager;
		entityManager.listen(entityRemoved());
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
				List<Entity> entities = entityManager.getAll();
				removeFromContainer(removedEntity, entities);
				removeChildLimbs(removedEntity, entities);
			}

			private void removeFromContainer(final Entity removedEntity, final List<Entity> entities) {
				Entity parent = LimbUtils.findContainer(entities, removedEntity);
				if (parent != null) {
					parent.get(LimbsPart.class).remove(removedEntity.get(TransformPart.class).getTransform());
				}
			}

			private void removeChildLimbs(final Entity removedEntity, final List<Entity> entities) {
				LimbsPart limbsPart = removedEntity.tryGet(LimbsPart.class);
				if (limbsPart != null) {
					for (Limb limb : limbsPart.getAll()) {
						Entity limbEntity = LimbUtils.findEntity(entities, limb);
						entityManager.remove(limbEntity);
					}
				}
			}
		};
	}

}
