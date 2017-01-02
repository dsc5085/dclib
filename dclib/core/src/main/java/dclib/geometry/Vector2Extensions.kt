package dclib.geometry

import com.badlogic.gdx.math.Vector2

fun Vector2.abs(): Vector2 {
    return Vector2(Math.abs(this.x), Math.abs(y))
}

fun Vector2.div(divisor: Vector2): Vector2 {
    return Vector2(x / divisor.x, y / divisor.y)
}