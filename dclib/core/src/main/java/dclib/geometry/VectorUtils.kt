package dclib.geometry

import com.badlogic.gdx.math.Vector2

object VectorUtils {
    fun inv(vector: Vector2): Vector2 {
        return Vector2(1 / vector.x, 1 / vector.y)
    }

    fun sign(vector: Vector2): Vector2 {
        return Vector2(Math.signum(vector.x), Math.signum(vector.y))
    }

    fun unit(vector: Vector2): Vector2 {
        val length = vector.len()
        return vector.cpy().scl(1 / length)
    }

    fun offset(from: Vector2, to: Vector2): Vector2 {
        return to.cpy().sub(from)
    }

    /**
     * Returns the x component of a line between from and to, given the y value.
     * @param p1
     * *
     * @param p2
     * *
     * @param lineY absolute y of point along line
     * *
     * @return
     */
    fun getLineX(p1: Vector2, p2: Vector2, lineY: Float): Float {
        val offset = offset(p1, p2)
        val slope = offset.y / offset.x
        return p1.x + (lineY - p1.y) / slope
    }

    fun getLineY(p1: Vector2, p2: Vector2, lineX: Float): Float {
        val offset = offset(p1, p2)
        val slope = offset.y / offset.x
        return p1.y + (lineX - p1.x) * slope
    }

    fun toVector2(degrees: Float, length: Float): Vector2 {
        return Vector2(length, 0f).setAngle(degrees)
    }
}
