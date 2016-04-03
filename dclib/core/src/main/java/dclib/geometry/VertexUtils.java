package dclib.geometry;

import java.util.List;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public final class VertexUtils {
	
	private VertexUtils() {
	}
	
	public static final float[] toVerticesArray(final List<Vector2> vertices) {
		float[] verticesArray = new float[vertices.size() * 2];
		for (int i = 0; i < vertices.size(); i++) {
			Vector2 vertex = vertices.get(i);
			verticesArray[i * 2] = vertex.x;
			verticesArray[i * 2 + 1] = vertex.y;
		}
		return verticesArray;
	}
	
	/**
	 * Converts to a polygon, setting the position based off the vertices' minimum x and y values.
	 * @param vertices
	 * @return polygon with calculated position
	 */
	public static final Polygon toPolygon(final float[] vertices) {
		Vector2 position = new Vector2(VertexUtils.minX(vertices), VertexUtils.minY(vertices));
		float[] shiftedVertices = VertexUtils.shiftVertices(vertices, -position.x, -position.y);
		Polygon polygon = new Polygon(shiftedVertices);
		polygon.setPosition(position.x, position.y);
		return polygon;
	}
	
	public static final void flipY(final float[] vertices) {
		float maxY = maxY(vertices);
		for (int i = 0; i < vertices.length / 2; i++) {
			vertices[i * 2 + 1] = maxY - vertices[i * 2 + 1];
		}
	}
	
	public static final float minX(final float[] vertices) {
		float minX = vertices[0];
		for (int i = 0; i < vertices.length / 2; i++) {
			minX = Math.min(minX, vertices[i * 2]);
		}
		return minX;
	}
	
	public static final float maxX(final float[] vertices) {
		float maxX = vertices[0];
		for (int i = 0; i < vertices.length / 2; i++) {
			maxX = Math.max(maxX, vertices[i * 2]);
		}
		return maxX;
	}
	
	public static final float minY(final float[] vertices) {
		float minY = vertices[1];
		for (int i = 0; i < vertices.length / 2; i++) {
			minY = Math.min(minY, vertices[i * 2 + 1]);
		}
		return minY;
	}
	
	public static final float maxY(final float[] vertices) {
		float maxY = vertices[1];
		for (int i = 0; i < vertices.length / 2; i++) {
			maxY = Math.max(maxY, vertices[i * 2 + 1]);
		}
		return maxY;
	}
	
	public static final Rectangle bounds(final float[] vertices) {
		float minX = vertices[0];
		float maxX = vertices[0];
		float minY = vertices[1];
		float maxY = vertices[1];
		for (int i = 0; i < vertices.length / 2; i++) {
			minX = Math.min(minX, vertices[i * 2]);
			maxX = Math.max(maxX, vertices[i * 2]);
			minY = Math.min(minY, vertices[i * 2 + 1]);
			maxY = Math.max(maxY, vertices[i * 2 + 1]);
		}
		return new Rectangle(minX, minY, maxX - minX, maxY - minY);
	}
	
	public static float[] shiftVertices(final float[] vertices, final float shiftX, final float shiftY) {
		float[] shiftedVertices = new float[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			if (i % 2 == 0) {
				shiftedVertices[i] = vertices[i] + shiftX;
			}
			else {
				shiftedVertices[i] = vertices[i] + shiftY;
			}
		}
		return shiftedVertices;
	}
	
	public static float[] sizeVertices(final float[] vertices, final Vector2 size) {
		Vector2 verticesSize = VertexUtils.bounds(vertices).getSize(new Vector2());
		float scaleX = size.x / verticesSize.x;
		float scaleY = size.y / verticesSize.y;
		return scaleVertices(vertices, scaleX, scaleY);
	}
	
	public static float[] scaleVertices(final float[] vertices, final float scaleX, final float scaleY) {
		float[] scaledVertices = new float[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			if (i % 2 == 0) {
				scaledVertices[i] = vertices[i] * scaleX;
			}
			else {
				scaledVertices[i] = vertices[i] * scaleY;
			}
		}
		return scaledVertices;
	}

}
