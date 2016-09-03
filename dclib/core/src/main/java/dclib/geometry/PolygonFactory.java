package dclib.geometry;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ShortArray;

public final class PolygonFactory {

	private static final EarClippingTriangulator triangulator = new EarClippingTriangulator();

	private PolygonFactory() {
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
        int numVertices = PolygonUtils.NUM_TRIANGLE_VERTICES;
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

	public static final Polygon createDefault() {
		float[] vertices = new float[] { 0, 0, MathUtils.FLOAT_ROUNDING_ERROR, 0, 0, MathUtils.FLOAT_ROUNDING_ERROR };
		return new Polygon(vertices);
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
