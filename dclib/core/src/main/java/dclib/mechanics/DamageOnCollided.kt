package dclib.mechanics

import com.google.common.base.Predicate
import dclib.epf.parts.CollisionDamagePart
import dclib.epf.parts.HealthPart
import dclib.physics.collision.CollidedEvent

class DamageOnCollided(private val filter: Predicate<CollidedEvent>) : (CollidedEvent) -> Unit {
	override fun invoke(event: CollidedEvent) {
		val collisionDamagePart = event.source.entity.tryGet(CollisionDamagePart::class)
		val targetHealthPart = event.target.entity.tryGet(HealthPart::class)
		if (collisionDamagePart != null && targetHealthPart != null && filter.apply(event)) {
			targetHealthPart.change(-collisionDamagePart.damage)
		}
	}
}