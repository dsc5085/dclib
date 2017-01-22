package dclib.geometry

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

fun Vector3.toVector2(): Vector2 {
    return Vector2(x, y)
}