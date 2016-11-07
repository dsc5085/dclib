package dclib.epf.parts

import dclib.eventing.DefaultEvent
import dclib.eventing.EventDelegate

class HealthPart(maxHealth: Float) {
	val healthGone = EventDelegate<DefaultEvent>()
	private val maxHealth = maxHealth
	private var health = maxHealth

	fun reset() {
		health = maxHealth
	}

	fun decrease(value: Float) {
		health -= value
		if (health <= 0) {
			healthGone.notify(DefaultEvent())
		}
	}
}