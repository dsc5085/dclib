package dclib.particles

import com.badlogic.gdx.math.Vector2
import dclib.epf.Entity
import dclib.epf.parts.TransformPart

class EntityPositionGetter(private val entity: Entity) : PositionGetter {
    override fun get(): Vector2? {
        val transform = entity[TransformPart::class].transform
        return if (entity.isActive) transform.toWorld(transform.localCenter) else null
    }
}