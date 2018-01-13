package dclib.mechanics

import com.google.common.base.Predicate
import dclib.epf.parts.CollisionDamagePart
import dclib.epf.parts.HealthPart
import dclib.physics.collision.CollidedEvent

class DamageOnCollided(private val filter: Predicate<CollidedEvent>) : (CollidedEvent) -> Unit {
	override fun invoke(event: CollidedEvent) {
		val source = event.collision.source.entity
		val target = event.collision.target.entity
		val collisionDamagePart = source.tryGet(CollisionDamagePart::class)
		val targetHealthPart = target.tryGet(HealthPart::class)
		if (collisionDamagePart != null && targetHealthPart != null && filter.apply(event)) {
			targetHealthPart.change(-collisionDamagePart.damage)
		}
	}
}