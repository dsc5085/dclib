package dclib.geometry

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import org.junit.Assert.assertEquals
import org.junit.Test
import test.dclib.geometry.VectorTestUtils

class VectorUtilsTest {
    @Test
    fun unit_Base_Correct() {
        val unit = VectorUtils.unit(Vector2(2.5f, 2.5f))
        VectorTestUtils.assertEquals(0.70710678f, 0.70710678f, unit)
    }

    @Test
    fun offset_Base_Correct() {
        val vector1 = Vector2(-4.3f, 2f)
        val vector2 = Vector2(1.6f, -6.7f)
        VectorTestUtils.assertEquals(5.9f, -8.7f, VectorUtils.offset(vector1, vector2))
    }

    @Test
    fun getLineX_Base_Correct() {
        val from = Vector2(2f, 4f)
        val to = Vector2(4f, 8f)
        val lineY = 6f
        assertEquals(3f, VectorUtils.getLineX(from, to, lineY), MathUtils.FLOAT_ROUNDING_ERROR)
    }

    @Test
    fun toVector2_Base_Correct() {
        val vector = VectorUtils.toVector2(45f, 2.5f)
        VectorTestUtils.assertEquals(1.76776695f, 1.76776695f, vector)
    }
}
