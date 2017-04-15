package dclib.physics.particles

import com.badlogic.gdx.math.Vector2

class StaticPositionGetter(private val position: Vector2) : PositionGetter {
    override fun get(): Vector2? {
        return position.cpy()
    }
}