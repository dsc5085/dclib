package dclib.util

import com.badlogic.gdx.math.MathUtils

class IntRange(private val min: Int, private val max: Int) {

    init {
        if (min > max) {
            throw IllegalArgumentException("Min argument of " + min
                    + " cannot be greater than max argument of " + max)
        }
    }

    operator fun contains(value: Int): Boolean {
        return value in min..max
    }

    fun min(): Int {
        return min
    }

    fun max(): Int {
        return max
    }

    fun difference(): Int {
        return max - min
    }

    fun clamp(value: Int): Float {
        return MathUtils.clamp(value, min, max).toFloat()
    }

    fun random(): Int {
        return MathUtils.random(min, max)
    }

}
