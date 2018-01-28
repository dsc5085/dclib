package dclib.physics.collision

import com.badlogic.gdx.physics.box2d.Body
import dclib.epf.Entity
import dclib.epf.EntityAddedEvent
import dclib.epf.EntityDestroyedEvent
import dclib.epf.EntityManager
import dclib.epf.parts.TransformPart
import dclib.physics.Box2dUtils

class BodyToEntityMap(entityManager: EntityManager) {
    private val map = mutableMapOf<Body, Entity>()

    init {
        entityManager.entityAdded.on { handleEntityAdded(it) }
        entityManager.entityDestroyed.on { handleEntityDestroyed(it) }
    }

    fun has(body: Body): Boolean {
        return get(body) != null
    }

    operator fun get(body: Body): Entity? {
        return map.getOrElse(body, { null })
    }

    private fun handleEntityAdded(event: EntityAddedEvent) {
        val entity = event.entity
        val transformPart = entity.tryGet(TransformPart::class)
        if (transformPart != null) {
            tryPut(entity)
            transformPart.transformChanged.on { tryPut(entity) }
        }
    }

    private fun tryPut(entity: Entity) {
        val body = Box2dUtils.getBody(entity)
        if (body != null) {
            map.put(body, entity)
        }
    }

    private fun handleEntityDestroyed(event: EntityDestroyedEvent) {
        for (entry in map.filter { it.value === event.entity }) {
            map.remove(entry.key)
        }
    }
}