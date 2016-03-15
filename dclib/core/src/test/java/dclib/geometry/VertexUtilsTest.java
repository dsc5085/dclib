package dclib.geometry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public final class VertexUtilsTest {

	@Test
	public void flipY_Vertices_ReturnsExpected() {
		float[] vertices = createVertices();
		VertexUtils.flipY(vertices);
		float[] expected = new float[] { 2.1f, 1.7f, 5.2f, 3.6f, 4, 0 };
		assertTrue(Arrays.equals(expected, vertices));
	}
	
	@Test
	public void minX_Vertices_ReturnsExpected() {
		float[] vertices = createVertices();
		assertEquals(2.1f, VertexUtils.minX(vertices), 0);
	}
	
	@Test
	public void maxX_Vertices_ReturnsExpected() {
		float[] vertices = createVertices();
		assertEquals(5.2f, VertexUtils.maxX(vertices), 0);
	}
	
	@Test
	public void minY_Vertices_ReturnsExpected() {
		float[] vertices = createVertices();
		assertEquals(1.4f, VertexUtils.minY(vertices), 0);
	}
	
	@Test
	public void maxY_Vertices_ReturnsExpected() {
		float[] vertices = createVertices();
		assertEquals(5, VertexUtils.maxY(vertices), 0);
	}
	
	@Test
	public void bounds_Vertices_ReturnsExpected() {
		float[] vertices = createVertices();
		Rectangle expected = new Rectangle(2.1f, 1.4f, 3.1f, 3.6f);
		assertEquals(expected, VertexUtils.bounds(vertices));
	}
	
	@Test
	public void sizedVertices_Vertices_ReturnsExpected() {
		float[] vertices = createVertices();
		float[] expected = new float[] { 2.032258f, 1.8333334f, 5.032258f, 0.7777778f, 3.8709679f, 2.777778f };
		Vector2 size = new Vector2(3, 2);
		assertTrue(Arrays.equals(expected, VertexUtils.sizeVertices(vertices, size)));
	}
	
	private float[] createVertices() {
		return new float[] { 2.1f, 3.3f, 5.2f, 1.4f, 4, 5 };
	}
	
}
