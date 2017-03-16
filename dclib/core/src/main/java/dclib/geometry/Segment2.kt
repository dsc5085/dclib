package dclib.geometry

import com.badlogic.gdx.math.Vector2

class Segment2(val a: Vector2, val b: Vector2) {
    val angle get() = VectorUtils.offset(a, b).angle()
}