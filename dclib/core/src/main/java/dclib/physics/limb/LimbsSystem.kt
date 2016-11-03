package dclib.physics.limb

import dclib.epf.Entity
import dclib.epf.EntityManager
import dclib.epf.EntityRemovedListener
import dclib.epf.EntitySystem
import dclib.epf.parts.LimbAnimationsPart
import dclib.epf.parts.LimbsPart
import dclib.epf.parts.TransformPart

class LimbsSystem(entityManager: EntityManager) : EntitySystem(entityManager) {
	private val entityManager: EntityManager = entityManager

	init {
		entityManager.listen(entityRemoved())
	}

	override fun update(delta: Float, entity: Entity) {
		entity.tryGet(LimbsPart::class.java)?.update()
		entity.tryGet(LimbAnimationsPart::class.java)?.update(delta)
	}

	private fun entityRemoved(): EntityRemovedListener {
		return object : EntityRemovedListener {
			override fun removed(removedEntity: Entity) {
				val entities = entityManager.all
				val parent = LimbUtils.findContainer(entities, removedEntity)
				if (parent != null) {
					val transform = removedEntity.get(TransformPart::class.java).transform
					val removedLimb = parent.get(LimbsPart::class.java).remove(transform)
					val descendantEntities = removedLimb?.descendants?.map { LimbUtils.findEntity(entities, it) }
					entityManager.removeAll(descendantEntities)
				}
			}
		}
	}
}