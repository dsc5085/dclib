package dclib.physics.collision

import com.badlogic.gdx.math.Vector2

data class CollidedEvent(
        val source: Contacter,
        val target: Contacter,
        val isTouching: Boolean,
        val manifold: List<Vector2>
)