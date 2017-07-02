package dclib.epf

import dclib.eventing.EventDelegate

interface EntityManager {
	val entityAdded: EventDelegate<EntityAddedEvent>
	val entityRemoved: EventDelegate<EntityRemovedEvent>
	fun contains(entity: Entity): Boolean
	fun getAll(): Collection<Entity>
	fun add(entity: Entity)
	fun addAll(entities: Collection<Entity>)
	// TODO: Rename to destroy. it makes more senseq
	fun remove(entity: Entity)
	fun removeAll(entities: Collection<Entity>)
	fun dispose()
}