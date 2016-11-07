package dclib.mechanics

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.google.common.base.Predicate
import dclib.epf.EntityManager
import dclib.epf.parts.CollisionRemovePart
import dclib.physics.collision.CollidedEvent

class RemoveOnCollided(entityManager: EntityManager, filter: Predicate<CollidedEvent>) : (CollidedEvent) -> Unit {
	private val entityManager = entityManager
	private val filter = filter

	override fun invoke(event: CollidedEvent) {
		val sourceEntity = event.source.entity
		val collisionRemovePart = sourceEntity.tryGet(CollisionRemovePart::class.java)
		if (collisionRemovePart != null) {
			val isTargetStatic = event.target.body.type === BodyType.StaticBody
			if (isTargetStatic || filter.apply(event)) {
				entityManager.remove(sourceEntity)
			}
		}
	}
}