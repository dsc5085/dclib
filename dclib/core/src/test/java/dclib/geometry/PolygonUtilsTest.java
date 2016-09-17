package dclib.geometry;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

public final class PolygonUtilsTest {

	@Test
	public void relativeCenter_ReturnsExpected() {
		Vector2 pivot = new Vector2(5, 5);
		Vector2 size = new Vector2(3, 1);
		assertEquals(new Vector2(3.5f, 4.5f), PolygonUtils.relativeCenter(pivot, size));
	}

}
