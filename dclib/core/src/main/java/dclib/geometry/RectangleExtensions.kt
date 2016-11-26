package dclib.geometry

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

import dclib.util.Maths

val Rectangle.top: Float
    get() = y + height

val Rectangle.right: Float
    get() = x + width

val Rectangle.size: Vector2
    get() = getSize(Vector2())

val Rectangle.center: Vector2
    get() = getCenter(Vector2())

val Rectangle.base: Vector2
    get() = Vector2(center.x, y)

fun Rectangle.containsX(x: Float): Boolean {
    return Maths.between(x, this.x, right)
}

fun Rectangle.scale(scale: Float): Rectangle {
    width *= scale
    height *= scale
    x *= scale
    y *= scale
    return this
}

fun Rectangle.grow(x: Float, y: Float): Rectangle {
    return Rectangle(this.x - x, this.y - y, width + x * 2, height + y * 2)
}