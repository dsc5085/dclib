package dclib.physics

import dclib.epf.Entity
import dclib.epf.EntityManager
import dclib.epf.EntitySystem
import dclib.epf.parts.AutoRotatePart
import dclib.epf.parts.TransformPart
import dclib.geometry.VectorUtils

class AutoRotateSystem(entityManager: EntityManager) : EntitySystem(entityManager) {
    override fun update(delta: Float, entity: Entity) {
        if (entity.has(AutoRotatePart::class)) {
            val transform = entity[TransformPart::class].transform
            setCenteredRotation(transform)
        }
    }

    private fun setCenteredRotation(transform: Transform) {
        val oldCenter = transform.center
        transform.rotation = transform.velocity.angle()
        val offset = VectorUtils.offset(transform.center, oldCenter)
        transform.translate(offset)
    }
}
