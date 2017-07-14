package dclib.mechanics

import dclib.epf.Entity
import dclib.epf.EntityManager
import dclib.epf.EntitySystem
import dclib.epf.parts.TimedDeathPart

class TimedDeathSystem(private val entityManager: EntityManager) : EntitySystem(entityManager) {
    override fun update(delta: Float, entity: Entity) {
        val timedDeathPart = entity.tryGet(TimedDeathPart::class)
        if (timedDeathPart != null) {
            timedDeathPart.update(delta)
            if (timedDeathPart.isDead) {
                entityManager.destroy(entity)
            }
        }
    }
}
