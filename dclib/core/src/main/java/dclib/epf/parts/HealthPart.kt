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

	fun change(change: Float) {
		val actualChange = Math.max(change, -health)
		health += actualChange
		healthChanged.notify(HealthChangedEvent(actualChange))
		if (health <= 0) {
			healthGone.notify(DefaultEvent())
		}
	}
}