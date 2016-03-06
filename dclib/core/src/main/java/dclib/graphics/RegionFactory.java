package dclib.graphics;

import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Rectangle;

import dclib.geometry.VertexUtils;

public final class RegionFactory {
	
	private static final EarClippingTriangulator triangulator = new EarClippingTriangulator();
	
	public static final PolygonRegion createPolygonRegion(final PolygonRegion polygonRegion, final float[] vertices) {
		return createPolygonRegion(polygonRegion.getRegion(), vertices);
	}
	
	public static final PolygonRegion createPolygonRegion(final TextureRegion textureRegion, final float[] vertices) {
		Rectangle bounds = VertexUtils.bounds(vertices);
		float[] shiftedVertices = new float[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			if (i % 2 == 0) {
				shiftedVertices[i] = vertices[i] - bounds.x;
			}
			else {
				shiftedVertices[i] = vertices[i] - bounds.y;
			}
		}
		TextureRegion croppedRegion = new TextureRegion(textureRegion, (int)bounds.x, (int)bounds.y, 
				(int)bounds.width, (int)bounds.height);
		short[] triangles = triangulator.computeTriangles(shiftedVertices).toArray();
		return new PolygonRegion(croppedRegion, shiftedVertices, triangles);
	}

}
