package dclib.geometry;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Polygon;
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
        final int numVerticesInTriangle = 3;
        for (int i = 0; i < trianglesVerticesIndexes.size / numVerticesInTriangle; i++)
        {
        	float[] triangleVertices = new float[numVerticesInTriangle * 2]; 
        	for (int j = 0; j < numVerticesInTriangle; j++) {
        		int verticesStartIndex = trianglesVerticesIndexes.get(i * numVerticesInTriangle + j) * 2;
        		int triangleVerticesStartIndex = j * 2;
            	triangleVertices[triangleVerticesStartIndex] = vertices[verticesStartIndex];
            	triangleVertices[triangleVerticesStartIndex + 1] = vertices[verticesStartIndex + 1];
        	}
            trianglesVertices.add(triangleVertices);
        }
        return trianglesVertices;
	}
	
	public static final float[] createRectangleVertices(final float width, final float height) {
		return createRectangleVertices(0, 0, width, height);
	}
	
	public static final float[] createRectangleVertices(final float x, final float y, final float width, 
			final float height) {
		return new float[] { x, y, x + width, y, x + width, y + height, x, y + height };
	}
	
}
