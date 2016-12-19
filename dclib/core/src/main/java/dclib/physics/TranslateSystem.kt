package dclib.physics

import dclib.epf.Entity
import dclib.epf.EntityManager
import dclib.epf.EntitySystem
import dclib.epf.parts.TransformPart

class TranslateSystem(entityManager: EntityManager) : EntitySystem(entityManager) {
    override fun update(delta: Float, entity: Entity) {
        val transformPart = entity.tryGet(TransformPart::class)
        if (transformPart != null) {
            val transform = transformPart.transform
            if (transform is DefaultTransform) {
                val offset = transform.getVelocity().scl(delta)
                transform.translate(offset)
            }
        }
    }
}
