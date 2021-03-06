package dclib.mechanics

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.google.common.base.Predicate
import dclib.epf.EntityManager
import dclib.epf.parts.CollisionDestroyPart
import dclib.physics.collision.CollidedEvent

class DestroyOnCollided(private val entityManager: EntityManager, private val filter: Predicate<CollidedEvent>)
	: (CollidedEvent) -> Unit {
	override fun invoke(event: CollidedEvent) {
		val sourceEntity = event.collision.source.entity
		val collisionDestroyPart = sourceEntity.tryGet(CollisionDestroyPart::class)
		if (collisionDestroyPart != null) {
			val targetBody = event.collision.target.body
			val isTargetStatic = targetBody.type === BodyType.StaticBody
			if (isTargetStatic || filter.apply(event)) {
				entityManager.destroy(sourceEntity)
			}
		}
	}
}