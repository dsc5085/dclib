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
				val entities = entityManager.getAll()
				removeFromContainer(removedEntity, entities)
				removeChildLimbs(removedEntity, entities)
			}

			private fun removeFromContainer(removedEntity: Entity, entities: List<Entity>) {
				val parent = LimbUtils.findContainer(entities, removedEntity)
				if (parent != null) {
					val transform = removedEntity.get(TransformPart::class.java).getTransform()
					parent.get(LimbsPart::class.java).remove(transform)
				}
			}

			private fun removeChildLimbs(removedEntity: Entity, entities: List<Entity>?) {
				val limbsPart = removedEntity.tryGet(LimbsPart::class.java)
				if (limbsPart != null) {
					for (limb in limbsPart.getAll()) {
						val limbEntity = LimbUtils.findEntity(entities, limb)
						entityManager.remove(limbEntity)
					}
				}
			}
		}
	}
}