package dclib.physics.limb

import dclib.epf.Entity
import dclib.epf.EntityManager
import dclib.epf.EntitySystem
import dclib.epf.parts.LimbAnimationsPart
import dclib.epf.parts.LimbsPart
import dclib.eventing.EventDelegate

class LimbsSystem(entityManager: EntityManager) : EntitySystem(entityManager) {
	val limbRemoved = EventDelegate<LimbRemovedEvent>()
	
	private val entityManager = entityManager

	init {
		entityManager.entityRemoved.on { handleEntityRemoved(it.entity) }
	}

	override fun update(delta: Float, entity: Entity) {
		entity.tryGet(LimbsPart::class.java)?.update()
		entity.tryGet(LimbAnimationsPart::class.java)?.update(delta)
	}

	private fun handleEntityRemoved(entity: Entity) {
        val container = LimbUtils.findContainer(entityManager.all, entity)
        val limbsPart = container?.tryGet(LimbsPart::class.java)
		if (limbsPart != null) {
			val removedLimb = limbsPart.root.remove(entity) ?: limbsPart.root
            limbRemoved.notify(LimbRemovedEvent(removedLimb, container!!))
			val descendantEntities = removedLimb.descendants.map { it.entity }
			entityManager.removeAll(descendantEntities)
		}
	}
}