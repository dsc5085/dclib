package dclib.physics.collision

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.World
import dclib.epf.EntityManager
import dclib.eventing.EventDelegate
import dclib.system.Updater

class CollisionChecker(private val entityManager: EntityManager, world: World) : Updater {
    val collided = EventDelegate<CollidedEvent>()

    private val fixtureToEntityMap = FixtureToEntityMap(entityManager)
    private val currentCollidedEvents = mutableSetOf<CollidedEvent>()

    init {
        val contactListener = DefaultContactListener()
        contactListener.contacted.on { handleContacted(it) }
        world.setContactListener(contactListener)
    }

    override fun update(delta: Float) {
        for (collidedEvent in currentCollidedEvents) {
            if (collidedEvent.source.entity.isActive && collidedEvent.target.entity.isActive) {
                collided.notify(collidedEvent)
            }
        }
        currentCollidedEvents.clear()
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

    private fun collide(source: Contacter, target: Contacter, contact: Contact) {
        val collidedEvent = CollidedEvent(source, target, contact)
        currentCollidedEvents.add(collidedEvent)
    }

    private fun createContacter(fixture: Fixture): Contacter? {
        val entity = fixtureToEntityMap.get(fixture)
        if (entity != null && entity.isActive) {
            return Contacter(fixture, entity)
        }
        return null
    }
}