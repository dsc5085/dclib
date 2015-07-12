package dclib.geometry;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

public final class VectorUtilsTest {

	@Test
	public void unit_Vector_ReturnsExpected() {
		assertEquals(new Vector2(0.7071068f, 0.7071068f), VectorUtils.unit(new Vector2(5, 5)));
	}
	
}
