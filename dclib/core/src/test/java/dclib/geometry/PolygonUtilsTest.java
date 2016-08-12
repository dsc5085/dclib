package dclib.geometry;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import test.dclib.geometry.TestPolygonFactory;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public final class PolygonUtilsTest {

	@Test
	public void size_RotatedPolygon_ReturnsExpected() {
		float[] vertices = new float[] { 0, 0, 2, 1, 1, 3 };
		Polygon polygon = new Polygon(vertices);
		polygon.rotate(45);
		assertEquals(new Vector2(2, 3), PolygonUtils.size(polygon));
	}

	@Test
	public void center_ReturnsExpected() {
		float[] vertices = new float[] { 0, 0, 0, 2, 2, 4, 0, 4 };
		Polygon polygon = new Polygon(vertices);
		assertEquals(new Vector2(1, 2), PolygonUtils.center(polygon));
	}

	@Test
	public void relativeCenter_ReturnsExpected() {
		Vector2 pivot = new Vector2(5, 5);
		Vector2 size = new Vector2(3, 1);
		assertEquals(new Vector2(3.5f, 4.5f), PolygonUtils.relativeCenter(pivot, size));
	}

	@Test
	public void toGlobal_LocalVectorAndPolygon_ReturnsExpected() {
		Vector2 local = new Vector2(0, 1);
		Polygon polygon = TestPolygonFactory.createSimpleRectangle();
		assertEquals(new Vector2(2, 1), PolygonUtils.toGlobal(local, polygon));
	}

	@Test
	public void toGlobal_LocalXYAndPolygon_ReturnsExpected() {
		Polygon polygon = TestPolygonFactory.createSimpleRectangle();
		assertEquals(new Vector2(2, 1), PolygonUtils.toGlobal(0, 1, polygon));
	}

}
