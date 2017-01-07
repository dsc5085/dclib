package dclib.epf

import dclib.eventing.EventDelegate

/**
 * Manages a group of entities. Provides accessing, removing, and adding entities.
 * @author David Chen
 *
 */
class DefaultEntityManager() : EntityManager {
	override val entityAdded = EventDelegate<EntityAddedEvent>()
	override val entityRemoved = EventDelegate<EntityRemovedEvent>()
	
	private val entities = mutableSetOf<Entity>()

	/**
	 * @return all managed entities
	 */
	override val all: List<Entity>
		get() = entities.toList()

	override fun contains(entity: Entity): Boolean {
		return entities.contains(entity)
	}

	/**
	 * Adds an entity.
	 * @param entity the entity to add
	 */
	override fun add(entity: Entity) {
		entity.isActive = true
		if (entities.contains(entity)) {
			throw IllegalArgumentException("Could not add entity ${entity}. It already exists")
		}
		entities.add(entity)
		entityAdded.notify(EntityAddedEvent(entity))
	}

	/**
	 * Adds the entities in the passed in collection.
	 * @param entities entities to add and manage
	 */
	override fun addAll(entities: Collection<Entity>) {
		for (entity in entities) {
			add(entity)
		}
	}

	/**
	 * Removes an entity.
	 * @param entity entity to remove
	 */
	override fun remove(entity: Entity) {
		if (entities.remove(entity)) {
			entity.isActive = false
			entityRemoved.notify(EntityRemovedEvent(entity))
		}
	}

	/**
	 * Removes the entities in the passed in collection.
	 * @param entities entities to remove
	 */
	override fun removeAll(entities: Collection<Entity>) {
		for (entity in entities) {
			remove(entity)
		}
	}

	override fun dispose() {
		val it = entities.iterator()
		while (it.hasNext()) {
			val entity = it.next()
			entity.isActive = false
			it.remove()
		}
	}
}