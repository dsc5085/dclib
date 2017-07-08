package dclib.epf

import dclib.eventing.EventDelegate

interface EntityManager {
	val entityAdded: EventDelegate<EntityAddedEvent>
	val entityDestroyed: EventDelegate<EntityDestroyedEvent>
	fun contains(entity: Entity): Boolean
	fun getAll(): Collection<Entity>
	fun add(entity: Entity)
	fun addAll(entities: Collection<Entity>)
	fun destroy(entity: Entity)
	fun destroyAll(entities: Collection<Entity>)
	fun dispose()
}