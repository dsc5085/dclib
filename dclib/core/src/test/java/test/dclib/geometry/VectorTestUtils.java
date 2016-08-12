package test.dclib.geometry;

import org.junit.Assert;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class VectorTestUtils {

	private VectorTestUtils() {
	}

	public static void assertEquals(final float expectedX, final float expectedY, final Vector2 vector) {
		Assert.assertEquals(expectedX, vector.x, MathUtils.FLOAT_ROUNDING_ERROR);
		Assert.assertEquals(expectedY, vector.y, MathUtils.FLOAT_ROUNDING_ERROR);
	}

}
