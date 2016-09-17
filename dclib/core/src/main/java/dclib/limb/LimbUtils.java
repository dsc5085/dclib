package dclib.limb;

import java.util.List;

import dclib.epf.Entity;
import dclib.epf.parts.LimbsPart;
import dclib.epf.parts.TransformPart;
import dclib.geometry.Transform;

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

	public static final Entity findContainer(final List<Entity> entities, final Transform transform) {
		for (Entity entity : entities) {
			LimbsPart limbsPart = entity.tryGet(LimbsPart.class);
			if (limbsPart != null) {
				for (Limb limb : limbsPart.getRoot().getDescendants()) {
					if (limb.getTransform() == transform) {
						return entity;
					}
				}
			}
		}
		return null;
	}

}
