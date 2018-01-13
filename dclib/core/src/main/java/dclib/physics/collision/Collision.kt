package dclib.physics.collision

import com.badlogic.gdx.math.Vector2
import com.google.common.base.Objects

class Collision(
        val source: Contacter,
        val target: Contacter,
        val isTouching: Boolean,
        val manifold: List<Vector2>) {
    override fun equals(other: Any?): Boolean {
        return other is Collision && source == other.source && target == other.target
    }

    override fun hashCode(): Int {
        return Objects.hashCode(source, target)
    }
}