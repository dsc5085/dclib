package test.dclib.geometry;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import dclib.util.Maths;
import org.junit.Assert;

public class VectorTestUtils {

	private VectorTestUtils() {
	}

	public static void assertEquals(final float expectedX, final float expectedY, final Vector2 vector) {
        Vector2 expected = new Vector2(expectedX, expectedY);
        Assert.assertTrue("Expected: " + expected + " Actual: " + vector,
                Maths.distance(vector.x, expectedX) < MathUtils.FLOAT_ROUNDING_ERROR &&
                        Maths.distance(vector.y, expectedY) < MathUtils.FLOAT_ROUNDING_ERROR);
    }

}
