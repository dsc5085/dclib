package dclib.physics.limb;

import java.util.List;

import dclib.epf.Entity;
import dclib.epf.parts.LimbsPart;
import dclib.epf.parts.TransformPart;
import dclib.physics.Transform;

public final class LimbUtils {

	private LimbUtils() {
	}

	public static final Entity findEntity(final List<Entity> entities, final Limb limb) {
		for (Entity entity : entities) {
			TransformPart transformPart = entity.tryGet(TransformPart.class);
			if (transformPart != null && transformPart.getTransform() == limb.getTransform()) {
				return entity;
			}
		}
		return null;
	}

	public static final Entity findContainer(final List<Entity> entities, final Entity entityToFind) {
		Transform transform = entityToFind.get(TransformPart.class).getTransform();
		if (entityToFind.has(LimbsPart.class)) {
			return entityToFind;
		}
		for (Entity entity : entities) {
			LimbsPart limbsPart = entity.tryGet(LimbsPart.class);
			if (limbsPart != null) {
				for (Limb limb : limbsPart.getAll()) {
					if (limb.getTransform() == transform) {
						return entity;
					}
				}
			}
		}
		return null;
	}

}
