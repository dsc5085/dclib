package dclib.util

import com.badlogic.gdx.math.MathUtils

object Maths {
    val DEGREES_MAX = 360f
    val HALF_DEGREES_MAX = DEGREES_MAX / 2
    val RADIANS_MAX = MathUtils.PI2

    fun min(currentValue: Float, newValue: Float): Float {
        return if (java.lang.Float.isNaN(currentValue)) newValue else Math.min(currentValue, newValue)
    }

    fun max(currentValue: Float, newValue: Float): Float {
        return if (java.lang.Float.isNaN(currentValue)) newValue else Math.max(currentValue, newValue)
    }

    fun minAbs(value: Float, maxValue: Float): Float {
        return Math.min(Math.abs(value), maxValue) * Math.signum(value)
    }

    fun distance(value1: Float, value2: Float): Float {
        return Math.abs(value1 - value2)
    }

    fun degDelta(toDegrees: Float, fromDegrees: Float): Float {
        return ((toDegrees - fromDegrees + DEGREES_MAX + HALF_DEGREES_MAX) % DEGREES_MAX) - HALF_DEGREES_MAX
    }

    /**
     * Rounds down to the nearest integer that is divisible by the interval length.
     * @param value value to round down from
     * *
     * @param intervalLength length between each interval
     * *
     * @return rounded down value
     */
    fun round(value: Float, intervalLength: Float): Float {
        return value - value % intervalLength
    }
}
