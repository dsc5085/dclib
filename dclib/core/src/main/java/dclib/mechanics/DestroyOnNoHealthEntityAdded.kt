package dclib.mechanics

import dclib.epf.EntityAddedEvent
import dclib.epf.EntityManager
import dclib.epf.parts.HealthPart

class DestroyOnNoHealthEntityAdded(private val entityManager: EntityManager) : (EntityAddedEvent) -> Unit {
	override fun invoke(event: EntityAddedEvent) {
        val healthPart = event.entity.tryGet(HealthPart::class)
		if (healthPart != null) {
			healthPart.healthGone.on { entityManager.destroy(event.entity) }
		}
	}
}