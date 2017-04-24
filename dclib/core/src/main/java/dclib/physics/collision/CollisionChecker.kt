package dclib.physics.collision

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.World
import dclib.epf.EntityManager
import dclib.eventing.EventDelegate
import dclib.system.Updater

class CollisionChecker(entityManager: EntityManager, world: World) : Updater {
    val collided = EventDelegate<CollidedEvent>()

    private val fixtureToEntityMap = FixtureToEntityMap(entityManager)
    private val currentCollidedEvents = mutableSetOf<CollidedEvent>()

    init {
        val contactListener = DefaultContactListener()
        // TODO: Use function reference style, e.g. ::handleContacted
        contactListener.contacted.on { handleContacted(it) }
        contactListener.contactEnded.on { handleContactEnded(it) }
        world.setContactListener(contactListener)
    }

    override fun update(delta: Float) {
        currentCollidedEvents.removeAll { !fixtureToEntityMap.has(it.source.fixture)
                || !fixtureToEntityMap.has(it.target.fixture) }
        for (collidedEvent in currentCollidedEvents.toSet()) {
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
                collide(c1, c2, contact)
                collide(c2, c1, contact)
            }
        }
    }

    private fun handleContactEnded(event: ContactedEvent) {
        val entityA = fixtureToEntityMap.get(event.contact.fixtureA)
        val entityB = fixtureToEntityMap.get(event.contact.fixtureB)
        currentCollidedEvents.removeAll { it.source.entity === entityA && it.target.entity === entityB }
        currentCollidedEvents.removeAll { it.source.entity === entityB && it.target.entity === entityA }
    }

    private fun collide(source: Contacter, target: Contacter, contact: Contact) {
        val worldManifold = contact.worldManifold
        val contactPoint = if (worldManifold.numberOfContactPoints > 0) worldManifold.points[0].cpy() else null
        val collidedEvent = CollidedEvent(source, target, contactPoint)
        if (currentCollidedEvents.none { it.source.entity === source.entity && it.target.entity === target.entity }) {
            currentCollidedEvents.add(collidedEvent)
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