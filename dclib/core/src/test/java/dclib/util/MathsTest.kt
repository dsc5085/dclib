package dclib.util

import com.badlogic.gdx.math.Vector2
import org.junit.Assert.assertEquals
import org.junit.Test

class MathsTest {
    @Test
    fun round_Base_Correct() {
        assertEquals(9f, Maths.round(11f, 3f), 0f)
    }

    @Test
    fun round_Negative_Correct() {
        assertEquals(-720f, Maths.round(-840f, 360f), 0f)
    }

    @Test
    fun getScaledRotation_Identity_Correct() {
        val scale = Vector2(1f, 1f)
        val scaledRotation = Maths.getScaledRotation(-378f, scale)
        assertEquals(-378f, scaledRotation)
    }

    @Test
    fun getScaledRotation_Scale_Correct() {
        val scale = Vector2(0.5f, 1f)
        val scaledRotation = Maths.getScaledRotation(-20f, scale)
        assertEquals(-36.0524f, scaledRotation)
    }

    @Test
    fun getScaledRotation_Negative_Correct() {
        val scale = Vector2(-1f, 1f)
        val scaledRotation = Maths.getScaledRotation(20f, scale)
        assertEquals(160f, scaledRotation, 0.0001f)
    }
}
