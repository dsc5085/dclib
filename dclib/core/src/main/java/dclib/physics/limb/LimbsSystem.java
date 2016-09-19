package dclib.physics.limb;

import java.util.List;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.epf.EntityRemovedListener;
import dclib.epf.EntitySystem;
import dclib.epf.parts.LimbAnimationsPart;
import dclib.epf.parts.LimbsPart;

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
				LimbsPart limbsPart = removedEntity.tryGet(LimbsPart.class);
				if (limbsPart != null) {
					List<Entity> entities = entityManager.getAll();
					for (Limb limb : limbsPart.getAll()) {
						Entity limbEntity = LimbUtils.findEntity(entities, limb);
						entityManager.remove(limbEntity);
					}
				}
			}
		};
	}

}
