package dclib.physics.particles

import com.badlogic.gdx.math.Vector2
import dclib.physics.Transform

class TransformPositionGetter(private val transform: Transform) : PositionGetter {
    override fun get(): Vector2 {
        return transform.toWorld(transform.localSize.scl(0.5f))
    }
}