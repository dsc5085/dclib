package dclib.mechanics

import dclib.epf.EntityAddedEvent
import dclib.epf.EntityManager
import dclib.epf.parts.HealthPart

class RemoveOnNoHealthEntityAdded(entityManager: EntityManager) : (EntityAddedEvent) -> Unit {
	private val entityManager = entityManager

	override fun invoke(event: EntityAddedEvent) {
		val healthPart = event.entity.tryGet(HealthPart::class.java)
		if (healthPart != null) {
			healthPart.healthGone.on { entityManager.remove(event.entity) }
		}
	}
}