package dclib.physics

import dclib.epf.Entity
import dclib.epf.EntityManager
import dclib.epf.EntitySystem
import dclib.epf.parts.AutoRotatePart
import dclib.epf.parts.TransformPart

class AutoRotateSystem(entityManager: EntityManager) : EntitySystem(entityManager) {
    override fun update(delta: Float, entity: Entity) {
        if (entity.has(AutoRotatePart::class)) {
            val transform = entity[TransformPart::class].transform
            transform.setCenteredRotation(transform.velocity.angle())
        }
    }
}
