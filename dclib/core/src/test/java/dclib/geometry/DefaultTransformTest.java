package dclib.geometry;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import test.dclib.geometry.TestPolygonFactory;

public final class DefaultTransformTest {

	@Test
	public void size_RotatedPolygon_ReturnsExpected() {
		float[] vertices = new float[] { 0, 0, 2, 1, 1, 3 };
		Transform transform = new DefaultTransform(0, new Polygon(vertices));
		transform.setRotation(45);
		assertEquals(new Vector2(2, 3), transform.getSize());
	}

	@Test
	public void getCenter_ReturnsExpected() {
		float[] vertices = new float[] { 0, 0, 0, 2, 2, 4, 0, 4 };
		Transform transform = new DefaultTransform(0, new Polygon(vertices));
		assertEquals(new Vector2(1, 2), transform.getCenter());
	}

	@Test
	public void toGlobal_LocalVectorAndPolygon_ReturnsExpected() {
		Vector2 local = new Vector2(0, 1);
		Transform transform = new DefaultTransform(0, TestPolygonFactory.createSimpleRectangle());
		assertEquals(new Vector2(2, 1), transform.toGlobal(local));
	}

}
