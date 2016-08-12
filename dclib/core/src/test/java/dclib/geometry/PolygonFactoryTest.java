package dclib.geometry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import test.dclib.geometry.TestPolygonFactory;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public final class PolygonFactoryTest {

	private static Polygon polygon;
	private static Polygon polygonCopy;

	@BeforeClass
	public static void oneTimeSetUp() {
		polygon = TestPolygonFactory.createTriangle();
		polygonCopy = PolygonFactory.copy(polygon);
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
		float[] rectangleVertices = PolygonFactory.createRectangleVertices(5, 3);
		float[] expected = new float[] { 0, 0, 5, 0, 5, 3, 0, 3 };
		assertTrue(Arrays.equals(expected, rectangleVertices));
	}

	@Test
	public void createRectangleVertices_Rectangle_ReturnsExpected() {
		float[] rectangleVertices = PolygonFactory.createRectangleVertices(new Rectangle(2, 2, 5, 3));
		float[] expected = new float[] { 2, 2, 7, 2, 7, 5, 2, 5 };
		assertTrue(Arrays.equals(expected, rectangleVertices));
	}

}
