package dclib.graphics;

import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Rectangle;

import dclib.geometry.PolygonFactory;
import dclib.geometry.VertexUtils;

public final class RegionFactory {
	
	private static final EarClippingTriangulator triangulator = new EarClippingTriangulator();

	public static final PolygonRegion createPolygonRegion(final TextureRegion textureRegion) {
		float[] vertices = PolygonFactory.createRectangleVertices(textureRegion.getRegionWidth(), 
				textureRegion.getRegionHeight());
		return createPolygonRegion(textureRegion, vertices);
	}
	
	public static final PolygonRegion createPolygonRegion(final TextureRegion textureRegion, final float[] vertices) {
		Rectangle bounds = VertexUtils.bounds(vertices);
		float[] shiftedVertices = VertexUtils.shift(vertices, -bounds.x, -bounds.y);
		// libgdx y-axis is flipped
		int regionY = (int)(textureRegion.getRegionHeight() - bounds.y - bounds.height);
		TextureRegion croppedRegion = new TextureRegion(textureRegion, (int)bounds.x, regionY, 
				(int)bounds.width, (int)bounds.height);
		short[] triangles = triangulator.computeTriangles(shiftedVertices).toArray();
		return new PolygonRegion(croppedRegion, shiftedVertices, triangles);
	}

}
