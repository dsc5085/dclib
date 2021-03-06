package dclib.geometry;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;
import test.dclib.geometry.TestPolygonFactory;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public final class PolygonUtilsTest {

	private static float[] vertices = new float[] { 2.1f, 3.3f, 5.2f, 1.4f, 4, 5 };
	private static Polygon polygon;
	private static Polygon polygonCopy;

	@Before
	public void setUp() {
		vertices = new float[] { 2.1f, 3.3f, 5.2f, 1.4f, 4, 5 };
		polygon = TestPolygonFactory.createTriangle();
        polygonCopy = PolygonUtils.INSTANCE.copy(polygon);
    }

	@Test
	public void flipY_ReturnsExpected() {
        float[] flippedVertices = PolygonUtils.INSTANCE.flipY(vertices);
        float[] expected = new float[]{2.1f, 1.7f, 5.2f, 3.6f, 4, 0};
        assertTrue(Arrays.equals(expected, flippedVertices));
	}

	@Test
	public void minX_ReturnsExpected() {
        assertEquals(2.1f, PolygonUtils.INSTANCE.minX(vertices), 0);
    }

	@Test
	public void maxX_ReturnsExpected() {
        assertEquals(5.2f, PolygonUtils.INSTANCE.maxX(vertices), 0);
    }

	@Test
	public void minY_ReturnsExpected() {
        assertEquals(1.4f, PolygonUtils.INSTANCE.minY(vertices), 0);
    }

	@Test
	public void maxY_ReturnsExpected() {
        assertEquals(5, PolygonUtils.INSTANCE.maxY(vertices), 0);
    }

	@Test
	public void bounds_ReturnsExpected() {
		Rectangle expected = new Rectangle(2.1f, 1.4f, 3.1f, 3.6f);
        assertEquals(expected, PolygonUtils.INSTANCE.bounds(vertices));
    }

	@Test
	public void setSize_ReturnsExpected() {
		float[] expected = new float[] { 2.032258f, 1.8333334f, 5.032258f, 0.7777778f, 3.8709679f, 2.777778f };
		Vector2 size = new Vector2(3, 2);
		PolygonUtils.INSTANCE.setSize(vertices, size);
        assertTrue(Arrays.equals(expected, vertices));
    }

	@Test
	public void relativeCenter_ReturnsExpected() {
		Vector2 pivot = new Vector2(5, 5);
		Vector2 size = new Vector2(3, 1);
        assertEquals(new Vector2(3.5f, 4.5f), PolygonUtils.INSTANCE.relativeCenter(pivot, size));
    }

	@Test
	public void copy_HasSameVertices() {
		assertEquals(polygon.getVertices(), polygonCopy.getVertices());
	}

	@Test
	public void copy_HasSameOriginX() {
		assertEquals(polygon.getOriginX(), polygonCopy.getOriginX(), 0);
	}

	@Test
	public void copy_HasSameOriginY() {
		assertEquals(polygon.getOriginY(), polygonCopy.getOriginY(), 0);
	}

	@Test
	public void copy_HasSameScaleX() {
		assertEquals(polygon.getScaleX(), polygonCopy.getScaleX(), 0);
	}

	@Test
	public void copy_HasSameScaleY() {
		assertEquals(polygon.getScaleY(), polygonCopy.getScaleY(), 0);
	}

	@Test
	public void copy_HasSameX() {
		assertEquals(polygon.getX(), polygonCopy.getX(), 0);
	}

	@Test
	public void copy_HasSameY() {
		assertEquals(polygon.getY(), polygonCopy.getY(), 0);
	}

	@Test
	public void copy_Polygon_HasSameRotation() {
		assertEquals(polygon.getRotation(), polygonCopy.getRotation(), 0);
	}

	@Test
	public void createRectangleVertices_WidthAndHeight_ReturnsExpected() {
        float[] rectangleVertices = PolygonUtils.INSTANCE.createRectangleVertices(5, 3);
        float[] expected = new float[]{0, 0, 5, 0, 5, 3, 0, 3};
        assertTrue(Arrays.equals(expected, rectangleVertices));
	}

	@Test
	public void createRectangleVertices_Rectangle_ReturnsExpected() {
        float[] rectangleVertices = PolygonUtils.INSTANCE.createRectangleVertices(new Rectangle(2, 2, 5, 3));
        float[] expected = new float[]{2, 2, 7, 2, 7, 5, 2, 5};
        assertTrue(Arrays.equals(expected, rectangleVertices));
	}

}
