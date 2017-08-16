package dclib.physics.collision

import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.World
import dclib.epf.Entity
import dclib.epf.EntityManager
import dclib.eventing.EventDelegate
import dclib.system.Updater

class CollisionChecker(entityManager: EntityManager, world: World) : Updater {
    val collided = EventDelegate<CollidedEvent>()

    private val fixtureToEntityMap = FixtureToEntityMap(entityManager)
    private val currentCollidedEvents = mutableSetOf<CollidedEvent>()

    init {
        val contactListener = DefaultContactListener()
        contactListener.contacted.on(this::handleContacted)
        contactListener.contactEnded.on(this::handleContactEnded)
        world.setContactListener(contactListener)
    }

    fun getCollidedEvents(sourceEntity: Entity): List<CollidedEvent> {
        return currentCollidedEvents.filter { it.source.entity === sourceEntity }
    }

    override fun update(delta: Float) {
        currentCollidedEvents.removeAll { !fixtureToEntityMap.has(it.source.fixture)
                || !fixtureToEntityMap.has(it.target.fixture) }
        for (collidedEvent in currentCollidedEvents.toSet()) {
            // TODO: Group events of the same entity in the same event
            collided.notify(collidedEvent)
        }
    }

    private fun handleContacted(event: ContactedEvent) {
        val contact = event.contact
        val isSensorCollision = contact.fixtureA.isSensor || contact.fixtureB.isSensor
        if (isSensorCollision || contact.worldManifold.numberOfContactPoints > 0) {
            val c1 = createContacter(contact.fixtureA)
            val c2 = createContacter(contact.fixtureB)
            if (c1 != null && c2 != null) {
                // Deep clone manifold points since the original instances get modified
                val manifold = contact.worldManifold.points.map { it.cpy() }
                currentCollidedEvents.add(CollidedEvent(c1, c2, contact.isTouching, manifold))
                currentCollidedEvents.add(CollidedEvent(c2, c1, contact.isTouching, manifold))
            }
        }
    }

    private fun handleContactEnded(event: ContactedEvent) {
        currentCollidedEvents.removeAll {
            // TODO: Check manifold equality as well
            (it.source.fixture === event.contact.fixtureA && it.target.fixture === event.contact.fixtureB)
                    || (it.source.fixture === event.contact.fixtureB && it.target.fixture === event.contact.fixtureA)
        }
    }

    private fun createContacter(fixture: Fixture): Contacter? {
        val entity = fixtureToEntityMap.get(fixture)
        if (entity != null && entity.isActive) {
            return Contacter(fixture, entity)
        }
        return null
    }
}