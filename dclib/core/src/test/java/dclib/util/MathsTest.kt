package dclib.util

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
}
