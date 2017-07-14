package dclib.epf.parts

import dclib.eventing.DefaultEvent
import dclib.eventing.EventDelegate
import dclib.mechanics.HealthChangedEvent

class HealthPart(private val maxHealth: Float) {
	val healthChanged = EventDelegate<HealthChangedEvent>()
	val healthGone = EventDelegate<DefaultEvent>()

	var health: Float = maxHealth
		private set

	fun reset() {
		health = maxHealth
	}

	fun change(offset: Float) {
		val actualOffset = Math.max(offset, -health)
		health += actualOffset
		healthChanged.notify(HealthChangedEvent(actualOffset))
		if (health <= 0) {
			healthGone.notify(DefaultEvent())
		}
	}
}