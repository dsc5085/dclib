package dclib.epf

import dclib.eventing.EventDelegate

interface EntityManager {
	val entityAdded: EventDelegate<EntityAddedEvent>
	val entityRemoved: EventDelegate<EntityRemovedEvent>
	val all: List<Entity>
	fun contains(entity: Entity): Boolean
	fun add(entity: Entity)
	fun addAll(entities: Collection<Entity>)
	fun remove(entity: Entity)
	fun removeAll(entities: Collection<Entity>)
	fun dispose()
}