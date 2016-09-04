package dclib.epf.util;

import java.util.List;

import com.badlogic.gdx.math.Polygon;

import dclib.epf.Entity;
import dclib.epf.parts.LimbsPart;
import dclib.epf.parts.TransformPart;
import dclib.limb.Limb;

public final class LimbUtils {

	private LimbUtils() {
	}

	public static final Entity findEntity(final List<Entity> entities, final Limb limb) {
		for (Entity entity : entities) {
			TransformPart transformPart = entity.tryGet(TransformPart.class);
			if (transformPart != null && transformPart.getPolygon() == limb.getPolygon()) {
				return entity;
			}
		}
		return null;
	}

	public static final Entity findContainer(final List<Entity> entities, final Polygon polygon) {
		for (Entity entity : entities) {
			LimbsPart limbsPart = entity.tryGet(LimbsPart.class);
			if (limbsPart != null) {
				for (Limb limb : limbsPart.getRoot().getDescendants()) {
					if (limb.getPolygon() == polygon) {
						return entity;
					}
				}
			}
		}
		return null;
	}

}
