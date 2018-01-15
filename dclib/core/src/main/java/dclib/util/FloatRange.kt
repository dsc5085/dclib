package dclib.util

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.MathUtils

class FloatRange(a: Float, b: Float) {
    val min: Float
    val max: Float

    init {
        this.min = Math.min(a, b)
        this.max = Math.max(a, b)
    }

    operator fun contains(value: Float): Boolean {
        return value in min..max
    }

    fun difference(): Float {
        return max - min
    }

    fun clamp(value: Float): Float {
        return MathUtils.clamp(value, min, max)
    }

    fun interpolate(value: Float): Float {
        return Interpolation.linear.apply(min, max, value)
    }

    fun random(): Float {
        return MathUtils.random(min, max)
    }
}
