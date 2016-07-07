package dclib.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public final class VertexUtils {
	
	private VertexUtils() {
	}
	
	public static final float[] toArray(final List<Vector2> vertices) {
		float[] verticesArray = new float[vertices.size() * 2];
		for (int i = 0; i < vertices.size(); i++) {
			Vector2 vertex = vertices.get(i);
			verticesArray[i * 2] = vertex.x;
			verticesArray[i * 2 + 1] = vertex.y;
		}
		return verticesArray;
	}
	
	public static final List<Vector2> toList(final float[] vertices) {
		List<Vector2> verticesList = new ArrayList<Vector2>();
		for (int i = 0; i < vertices.length / 2; i++) {
			verticesList.add(new Vector2(vertices[i * 2], vertices[i * 2 + 1]));
		}
		return verticesList;
	}
	
	/**
	 * Converts to a polygon, setting the position based off the vertices' minimum x and y values.
	 * @param vertices
	 * @return polygon with calculated position
	 */
	public static final Polygon toPolygon(final float[] vertices) {
		Vector2 position = new Vector2(VertexUtils.minX(vertices), VertexUtils.minY(vertices));
		float[] shiftedVertices = VertexUtils.shift(vertices, -position.x, -position.y);
		Polygon polygon = new Polygon(shiftedVertices);
		polygon.setPosition(position.x, position.y);
		return polygon;
	}
	
	public static final float minX(final float[] vertices) {
		float minX = Float.MAX_VALUE;
		for (int i = 0; i < vertices.length / 2; i++) {
			minX = Math.min(minX, vertices[i * 2]);
		}
		return minX;
	}
	
	public static final float maxX(final float[] vertices) {
		float maxX = -Float.MAX_VALUE;
		for (int i = 0; i < vertices.length / 2; i++) {
			maxX = Math.max(maxX, vertices[i * 2]);
		}
		return maxX;
	}
	
	public static final float minY(final float[] vertices) {
		float minY = Float.MAX_VALUE;
		for (int i = 0; i < vertices.length / 2; i++) {
			minY = Math.min(minY, vertices[i * 2 + 1]);
		}
		return minY;
	}
	
	public static final float maxY(final float[] vertices) {
		float maxY = -Float.MAX_VALUE;
		for (int i = 0; i < vertices.length / 2; i++) {
			maxY = Math.max(maxY, vertices[i * 2 + 1]);
		}
		return maxY;
	}
	
	public static final Rectangle bounds(final float[] vertices) {
		float minX = minX(vertices);
		float maxX = maxX(vertices);
		float minY = minY(vertices);
		float maxY = maxY(vertices);
		return new Rectangle(minX, minY, maxX - minX, maxY - minY);
	}
	
	public static float[] shift(final float[] vertices, final float shiftX, final float shiftY) {
		float[] shiftedVertices = new float[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			if (i % 2 == 0) {
				shiftedVertices[i] = vertices[i] + shiftX;
			} else {
				shiftedVertices[i] = vertices[i] + shiftY;
			}
		}
		return shiftedVertices;
	}
	
	public static float[] setSize(final float[] vertices, final Vector2 size) {
		Vector2 verticesSize = VertexUtils.bounds(vertices).getSize(new Vector2());
		float scaleX = size.x / verticesSize.x;
		float scaleY = size.y / verticesSize.y;
		return scale(vertices, scaleX, scaleY);
	}
	
	public static float[] scale(final float[] vertices, final float scale) {
		return scale(vertices, scale, scale);
	}
	
	public static float[] scale(final float[] vertices, final float scaleX, final float scaleY) {
		float[] scaledVertices = new float[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			if (i % 2 == 0) {
				scaledVertices[i] = vertices[i] * scaleX;
			} else {
				scaledVertices[i] = vertices[i] * scaleY;
			}
		}
		return scaledVertices;
	}
	
	public static final float[] flipY(final float[] vertices) {
		float[] flippedVertices = Arrays.copyOf(vertices, vertices.length);
		float maxY = maxY(vertices);
		for (int i = 0; i < vertices.length / 2; i++) {
			flippedVertices[i * 2 + 1] = maxY - vertices[i * 2 + 1];
		}
		return flippedVertices;
	}

}
