package dclib.geometry

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

val Rectangle.top get() = y + height
val Rectangle.right get() = x + width
val Rectangle.size get() = getSize(Vector2())
val Rectangle.center get() = getCenter(Vector2())
val Rectangle.base get() = Vector2(center.x, y)

fun Rectangle.containsX(x: Float): Boolean {
    return x in this.x..right
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