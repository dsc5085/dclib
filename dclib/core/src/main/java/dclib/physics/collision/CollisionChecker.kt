package dclib.physics.collision

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.Fixture
import dclib.epf.Entity
import dclib.epf.EntityManager
import dclib.epf.parts.TransformPart
import dclib.eventing.EventDelegate
import dclib.physics.Box2dTransform
import dclib.system.Updater

class CollisionChecker(private val entityManager: EntityManager, contactChecker: ContactChecker) : Updater {
    val collided = EventDelegate<CollidedEvent>()

    private val currentCollidedEvents = mutableListOf<CollidedEvent>()

    init {
        contactChecker.contacted.on { handleContacted(it) }
    }

    fun getTargets(source: Entity): List<Contacter> {
        val events = currentCollidedEvents.filter { it.source.entity == source && it.target.entity.isActive }
        return events.map { it.target }
    }

    override fun update(delta: Float) {
        currentCollidedEvents.clear()
    }

    private fun handleContacted(event: ContactedEvent) {
        val contact = event.contact
        val c1 = createContacter(contact.fixtureA)
        val c2 = createContacter(contact.fixtureB)
        val isSensorCollision = contact.fixtureA.isSensor || contact.fixtureB.isSensor
        if (c1 != null && c2 != null && (isSensorCollision || contact.worldManifold.numberOfContactPoints > 0)) {
            collide(c1, c2, contact)
            collide(c2, c1, contact)
        }
    }

    private fun collide(source: Contacter, target: Contacter, contact: Contact) {
        val collidedEvent = CollidedEvent(source, target, contact)
        collided.notify(collidedEvent)
        currentCollidedEvents.add(collidedEvent)
    }

    private fun createContacter(fixture: Fixture): Contacter? {
        val entity = findEntity(fixture)
        if (entity != null && entity.isActive) {
            return Contacter(fixture, entity)
        }
        return null
    }

    private fun findEntity(fixture: Fixture): Entity? {
        return entityManager.all.firstOrNull {
            val transform = it.tryGet(TransformPart::class)?.transform
            transform is Box2dTransform && transform.body.fixtureList.contains(fixture)
        }
    }
}