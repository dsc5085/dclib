package dclib.geometry;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public final class VectorUtilsTest {

	@Test
	public void unit_Vector_ReturnsExpected() {
		Vector2 unit = VectorUtils.unit(new Vector2(2.5f, 2.5f));
		assertEqualsVectors(unit, 0.70710678f, 0.70710678f);
	}

	@Test
	public void lengthened_VectorAndLength_ReturnsExpected() {
		Vector2 lengthened = VectorUtils.lengthened(new Vector2(5.2f, 5.2f), 1);
		assertEqualsVectors(lengthened, 0.70710678f, 0.70710678f);
	}

	@Test
	public void distance_BetweenVectors_ReturnsExpected() {
		Vector2 vector1 = new Vector2(-4.3f, 2);
		Vector2 vector2 = new Vector2(1.6f, -6.7f);
		assertEquals(10.511898, VectorUtils.distance(vector1, vector2), MathUtils.FLOAT_ROUNDING_ERROR);
	}
	
	@Test
	public void offset_BetweenVectors_ReturnsExpected() {
		Vector2 vector1 = new Vector2(-4.3f, 2);
		Vector2 vector2 = new Vector2(1.6f, -6.7f);
		assertEqualsVectors(VectorUtils.offset(vector1, vector2), 5.9f, -8.7f);
	}
	
	@Test
	public void angle_Vector_ReturnsExpected() {
		Vector2 vector = VectorUtils.fromAngle(45);
		assertEqualsVectors(vector, 0.70710678f, 0.70710678f);
	}
	
	@Test
	public void angle_VectorAndLength_ReturnsExpected() {
		Vector2 vector = VectorUtils.fromAngleAndLength(45, 2.5f);
		assertEqualsVectors(vector, 1.76776695f, 1.76776695f);
	}

	private void assertEqualsVectors(final Vector2 vector, final float expectedX, final float expectedY) {
		assertEquals(expectedX, vector.x, MathUtils.FLOAT_ROUNDING_ERROR);
		assertEquals(expectedY, vector.y, MathUtils.FLOAT_ROUNDING_ERROR);
	}
	
}
