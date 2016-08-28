package dclib.epf.util;

import java.util.List;

import dclib.epf.Entity;
import dclib.epf.parts.TransformPart;
import dclib.limb.Limb;

public final class LimbUtils {

	private LimbUtils() {
	}

	public static final Entity findEntity(final List<Entity> entities, final Limb limb) {
		for (Entity entity : entities) {
			if (entity.has(TransformPart.class)) {
				TransformPart transformPart = entity.get(TransformPart.class);
				if (transformPart.getPolygon() == limb.getPolygon()) {
					return entity;
				}
			}
		}
		return null;
	}

}
