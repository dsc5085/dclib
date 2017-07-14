package dclib.epf

import dclib.eventing.EventDelegate

/**
 * Manages a group of entities. Provides accessing, removing, and adding entities.
 * @author David Chen
 *
 */
class DefaultEntityManager : EntityManager {
	override val entityAdded = EventDelegate<EntityAddedEvent>()
	override val entityDestroyed = EventDelegate<EntityDestroyedEvent>()

	private val entities = mutableSetOf<Entity>()

	override fun contains(entity: Entity): Boolean {
		return entities.contains(entity)
	}

	/**
	 * @return all managed entities
	 */
	override fun getAll(): Collection<Entity> {
		return entities.toSet()
	}

	/**
	 * Adds an entity.
	 * @param entity the entity to add
	 */
	override fun add(entity: Entity) {
		entity.isActive = true
		if (entities.contains(entity)) {
			throw IllegalArgumentException("Could not add entity $entity. It already exists")
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
	 * Destroys an entity.
	 * @param entity entity to destroy
	 */
	override fun destroy(entity: Entity) {
		if (entities.remove(entity)) {
			entity.isActive = false
			entityDestroyed.notify(EntityDestroyedEvent(entity))
		}
	}

	/**
	 * Destroys the entities in the passed in collection.
	 * @param entities entities to destroy
	 */
	override fun destroyAll(entities: Collection<Entity>) {
		for (entity in entities) {
			destroy(entity)
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