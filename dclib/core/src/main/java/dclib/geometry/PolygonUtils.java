package dclib.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ShortArray;

import dclib.util.Maths;

public final class PolygonUtils {

    public static final int NUM_TRIANGLE_VERTICES = 3;

	private static final EarClippingTriangulator triangulator = new EarClippingTriangulator();

	private PolygonUtils() {
	}

	public static final float[] toFloats(final Vector2[] vertices) {
		float[] vertexFloats = new float[vertices.length * 2];
		for (int i = 0; i < vertices.length; i++) {
			Vector2 vertex = vertices[i];
			vertexFloats[i * 2] = vertex.x;
			vertexFloats[i * 2 + 1] = vertex.y;
		}
		return vertexFloats;
	}

	public static final Vector2[] toVectors(final float[] vertices) {
		Vector2[] vectors = new Vector2[vertices.length / 2];
		for (int i = 0; i < vectors.length; i++) {
			vectors[i] = new Vector2(vertices[i * 2], vertices[i * 2 + 1]);
		}
		return vectors;
	}

	/**
	 * Converts to a polygon, setting the position based off the vertices' minimum x and y values.
	 * @param vertices
	 * @return polygon with calculated position
	 */
	public static final Polygon toPolygon(final float[] vertices) {
		Vector2 position = new Vector2(minX(vertices), minY(vertices));
		float[] shiftedVertices = shift(vertices, -position.x, -position.y);
		Polygon polygon = new Polygon(shiftedVertices);
		polygon.setPosition(position.x, position.y);
		return polygon;
	}

	public static final float minX(final float[] vertices) {
		float minX = Float.NaN;
		for (int i = 0; i < vertices.length / 2; i++) {
			minX = Maths.min(minX, vertices[i * 2]);
		}
		return minX;
	}

	public static final float maxX(final float[] vertices) {
		float maxX = Float.NaN;
		for (int i = 0; i < vertices.length / 2; i++) {
			maxX = Maths.max(maxX, vertices[i * 2]);
		}
		return maxX;
	}

	public static final float minY(final float[] vertices) {
		float minY = Float.NaN;
		for (int i = 0; i < vertices.length / 2; i++) {
			minY = Maths.min(minY, vertices[i * 2 + 1]);
		}
		return minY;
	}

	public static final float maxY(final float[] vertices) {
		float maxY = Float.NaN;
		for (int i = 0; i < vertices.length / 2; i++) {
			maxY = Maths.max(maxY, vertices[i * 2 + 1]);
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
		Vector2 verticesSize = bounds(vertices).getSize(new Vector2());
		Vector2 scale = new Vector2(size.x / verticesSize.x, size.y / verticesSize.y);
		return scale(vertices, scale);
	}

	public static float[] scale(final float[] vertices, final float scale) {
		return scale(vertices, new Vector2(scale, scale));
	}

	public static float[] scale(final float[] vertices, final Vector2 scale) {
		float[] scaledVertices = new float[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			if (i % 2 == 0) {
				scaledVertices[i] = vertices[i] * scale.x;
			} else {
				scaledVertices[i] = vertices[i] * scale.y;
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

	public static final Vector2 relativeCenter(final Vector2 pivot, final Vector2 size) {
		Vector2 halfSize = size.cpy().scl(0.5f);
		return pivot.cpy().sub(halfSize);
	}

	public static final Polygon copy(final Polygon polygon) {
		Polygon copy = new Polygon(polygon.getVertices());
		copy.setOrigin(polygon.getOriginX(), polygon.getOriginY());
		copy.setPosition(polygon.getX(), polygon.getY());
		copy.setRotation(polygon.getRotation());
		copy.setScale(polygon.getScaleX(), polygon.getScaleY());
		return copy;
	}

	public static final List<float[]> triangulate(final float[] vertices) {
		List<float[]> trianglesVertices = new ArrayList<float[]>();
        ShortArray trianglesVerticesIndexes = triangulator.computeTriangles(vertices);
        int numVertices = NUM_TRIANGLE_VERTICES;
        for (int i = 0; i < trianglesVerticesIndexes.size / numVertices; i++)
        {
        	float[] triangleVertices = new float[numVertices * 2];
        	for (int j = 0; j < numVertices; j++) {
        		int verticesStartIndex = trianglesVerticesIndexes.get(i * numVertices + j) * 2;
        		int triangleVerticesStartIndex = j * 2;
            	triangleVertices[triangleVerticesStartIndex] = vertices[verticesStartIndex];
            	triangleVertices[triangleVerticesStartIndex + 1] = vertices[verticesStartIndex + 1];
        	}
            trianglesVertices.add(triangleVertices);
        }
        return trianglesVertices;
	}

	public static final float[] createDefault() {
		return new float[] { 0, 0, MathUtils.FLOAT_ROUNDING_ERROR, 0, 0, MathUtils.FLOAT_ROUNDING_ERROR };
	}

	public static final float[] createRectangleVertices(final float width, final float height) {
		return createRectangleVertices(new Rectangle(0, 0, width, height));
	}

	public static final float[] createRectangleVertices(final Rectangle rectangle) {
		return new float[] {
			rectangle.x, rectangle.y,
			rectangle.x + rectangle.width, rectangle.y,
			rectangle.x + rectangle.width, rectangle.y + rectangle.height,
			rectangle.x, rectangle.y + rectangle.height
		};
	}

}
