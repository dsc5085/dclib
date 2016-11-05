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
			override fun removed(entity: Entity) {
				val entities = entityManager.all
				val limbsPart = LimbUtils.findContainer(entities, entity)?.tryGet(LimbsPart::class.java)
				if (limbsPart != null) {
					val transform = entity[TransformPart::class.java].transform
					val removedLimb = limbsPart.remove(transform) ?: limbsPart.root
					val descendantEntities = removedLimb.descendants.map { LimbUtils.findEntity(entities, it) }
					entityManager.removeAll(descendantEntities)
				}
			}
		}
	}
}