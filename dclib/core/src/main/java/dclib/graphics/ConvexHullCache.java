package dclib.graphics;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import dclib.geometry.VertexUtils;

public final class ConvexHullCache {

	private final TextureCache textureCache;
	private final Map<String, float[]> convexHulls = new HashMap<String, float[]>();

	public ConvexHullCache(final TextureCache textureCache) {
		this.textureCache = textureCache;
	}

	public final Polygon create(final String regionName, final Vector2 size) {
		if (!convexHulls.containsKey(regionName)) {
			TextureRegion textureRegion = textureCache.getTextureRegion(regionName);
			float[] convexHull = TextureGeometry.createConvexHull(textureRegion);
			convexHulls.put(regionName, convexHull);
		}
		float[] vertices = VertexUtils.setSize(convexHulls.get(regionName), size);
		return new Polygon(vertices);
	}

}
