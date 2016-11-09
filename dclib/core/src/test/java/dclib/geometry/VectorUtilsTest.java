package dclib.geometry;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import org.junit.Test;
import test.dclib.geometry.VectorTestUtils;

import static org.junit.Assert.assertEquals;

public final class VectorUtilsTest {

    @Test
    public void unit_ReturnsExpected() {
        Vector2 unit = VectorUtils.unit(new Vector2(2.5f, 2.5f));
        VectorTestUtils.assertEquals(0.70710678f, 0.70710678f, unit);
    }

    @Test
    public void offset_ReturnsExpected() {
        Vector2 vector1 = new Vector2(-4.3f, 2);
        Vector2 vector2 = new Vector2(1.6f, -6.7f);
        VectorTestUtils.assertEquals(5.9f, -8.7f, VectorUtils.offset(vector1, vector2));
    }

    @Test
    public void getLineX_ReturnsExpected() {
        Vector2 from = new Vector2(2, 4);
        Vector2 to = new Vector2(4, 8);
        float lineY = 6;
        assertEquals(3, VectorUtils.getLineX(from, to, lineY), MathUtils.FLOAT_ROUNDING_ERROR);
    }

    @Test
    public void fromAngle_VectorAndLength_ReturnsExpected() {
        Vector2 vector = VectorUtils.toVector2(45, 2.5f);
        assertEqualsVectors(vector, 1.76776695f, 1.76776695f);
    }

    private void assertEqualsVectors(final Vector2 vector, final float expectedX, final float expectedY) {
        assertEquals(expectedX, vector.x, MathUtils.FLOAT_ROUNDING_ERROR);
    }
}
