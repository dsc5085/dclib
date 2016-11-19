package dclib.geometry

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import org.junit.Assert.assertEquals
import org.junit.Test

class RectangleUtilsTest {
    @Test
    fun top_ReturnsExpected() {
        val rectangle = createRectangle()
        assertEquals(7.7f, rectangle.top, MathUtils.FLOAT_ROUNDING_ERROR)
    }

    @Test
    fun right_ReturnsExpected() {
        val rectangle = createRectangle()
        assertEquals(5.8f, rectangle.right, MathUtils.FLOAT_ROUNDING_ERROR)
    }

    @Test
    fun scale_ReturnsExpected() {
        val rectangle = createRectangle().scale(3f)
        assertEquals(7.5f, rectangle.height, MathUtils.FLOAT_ROUNDING_ERROR)
        assertEquals(7.5f, rectangle.width, MathUtils.FLOAT_ROUNDING_ERROR)
        assertEquals(9.9f, rectangle.x, MathUtils.FLOAT_ROUNDING_ERROR)
        assertEquals(15.6f, rectangle.y, MathUtils.FLOAT_ROUNDING_ERROR)
    }

    private fun createRectangle(): Rectangle {
        return Rectangle(3.3f, 5.2f, 2.5f, 2.5f)
    }
}
