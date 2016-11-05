package dclib.physics.limb

import dclib.epf.Entity
import dclib.epf.EntityManager
import dclib.epf.EntityRemovedListener
import dclib.epf.EntitySystem
import dclib.epf.parts.LimbAnimationsPart
import dclib.epf.parts.LimbsPart
import dclib.eventing.EventDelegate

class LimbsSystem(entityManager: EntityManager) : EntitySystem(entityManager) {
	private val limbRemovedDelegate: EventDelegate<LimbRemovedListener> = EventDelegate<LimbRemovedListener>()
	
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
				val limbsPart = LimbUtils.findContainer(entityManager.all, entity)?.tryGet(LimbsPart::class.java)
				if (limbsPart != null) {
					val removedLimb = limbsPart.root.remove(entity) ?: limbsPart.root
					limbRemovedDelegate.notify(LimbRemovedEvent(removedLimb))
					val descendantEntities = removedLimb.descendants.map { it.entity }
					entityManager.removeAll(descendantEntities)
				}
			}
		}
	}
}