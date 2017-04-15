package dclib.physics.particles

import com.badlogic.gdx.math.Vector2

interface PositionGetter {
    fun get(): Vector2?
}