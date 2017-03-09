package dclib.physics.collision

import com.badlogic.gdx.physics.box2d.Fixture
import dclib.epf.Entity
import dclib.epf.EntityAddedEvent
import dclib.epf.EntityManager
import dclib.epf.EntityRemovedEvent
import dclib.epf.parts.TransformPart
import dclib.physics.Box2dTransform

class FixtureToEntityMap(entityManager: EntityManager) {
    private val map = mutableMapOf<Fixture, Entity>()

    init {
        entityManager.entityAdded.on { handleEntityAdded(it) }
        entityManager.entityRemoved.on { handleEntityRemoved(it) }
    }

    fun get(fixture: Fixture): Entity? {
        return map.getOrElse(fixture, { null })
    }

    private fun handleEntityAdded(event: EntityAddedEvent) {
        val entity = event.entity
        val transform = entity.tryGet(TransformPart::class)?.transform
        if (transform is Box2dTransform) {
            for (fixture in transform.body.fixtureList) {
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