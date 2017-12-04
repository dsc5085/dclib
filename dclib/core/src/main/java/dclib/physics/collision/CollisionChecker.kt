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
    private val currentCollisions = mutableSetOf<Collision>()

    init {
        val contactListener = DefaultContactListener()
        contactListener.contacted.on(this::handleContacted)
        contactListener.contactEnded.on(this::handleContactEnded)
        world.setContactListener(contactListener)
    }

    fun getCollisions(sourceEntity: Entity): List<Collision> {
        return currentCollisions.filter { it.source.entity === sourceEntity }
    }

    override fun update(delta: Float) {
        currentCollisions.removeAll { !fixtureToEntityMap.has(it.source.fixture)
                || !fixtureToEntityMap.has(it.target.fixture) }
        val collidedEvents = mutableListOf<CollidedEvent>()
        for (collision in currentCollisions.toSet()) {
            var collidedEvent = collidedEvents.firstOrNull {
                it.source === collision.source.entity && it.target === collision.target.entity
            }
            if (collidedEvent == null) {
                collidedEvent = CollidedEvent(collision.source.entity, collision.target.entity)
                collidedEvents.add(collidedEvent)
            }
            collidedEvent.collisions.add(collision)
        }
        for (collidedEvent in collidedEvents) {
            if (collidedEvent.source.isActive && collidedEvent.target.isActive) {
                collided.notify(collidedEvent)
            }
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
                currentCollisions.add(Collision(c1, c2, contact.isTouching, manifold))
                currentCollisions.add(Collision(c2, c1, contact.isTouching, manifold))
            }
        }
    }

    private fun handleContactEnded(event: ContactedEvent) {
        currentCollisions.removeAll {
            // TODO: Check manifold equality as well?
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