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
			TransformPart transformPart = entity.tryGet(TransformPart.class);
			if (transformPart != null) {
				if (transformPart.getPolygon() == limb.getPolygon()) {
					return entity;
				}
			}
		}
		return null;
	}

}
