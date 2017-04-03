package dclib.physics.collision

import com.badlogic.gdx.physics.box2d.Fixture
import dclib.epf.Entity
import dclib.epf.EntityAddedEvent
import dclib.epf.EntityManager
import dclib.epf.EntityRemovedEvent
import dclib.epf.parts.TransformPart
import dclib.physics.Box2dUtils

class FixtureToEntityMap(entityManager: EntityManager) {
    private val map = mutableMapOf<Fixture, Entity>()

    init {
        entityManager.entityAdded.on { handleEntityAdded(it) }
        entityManager.entityRemoved.on { handleEntityRemoved(it) }
    }

    fun has(fixture: Fixture): Boolean {
        return get(fixture) != null
    }

    fun get(fixture: Fixture): Entity? {
        return map.getOrElse(fixture, { null })
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
            for (fixture in body.fixtureList) {
                map.put(fixture, entity)
            }
        }
    }

    private fun handleEntityRemoved(event: EntityRemovedEvent) {
        for (entry in map.filter { it.value === event.entity }) {
            map.remove(entry.key)
        }
    }
}