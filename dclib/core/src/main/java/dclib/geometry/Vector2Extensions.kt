package dclib.geometry

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

fun Vector2.abs(): Vector2 {
    return Vector2(Math.abs(this.x), Math.abs(y))
}

fun Vector2.div(divisor: Vector2): Vector2 {
    return Vector2(x / divisor.x, y / divisor.y)
}

fun Vector2.toVector3(z: Float = 0f): Vector3 {
    return Vector3(x, y, z)
}