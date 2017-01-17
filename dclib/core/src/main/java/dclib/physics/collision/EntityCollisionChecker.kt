package dclib.physics.collision

import com.badlogic.gdx.physics.box2d.Fixture
import dclib.epf.Entity
import dclib.eventing.EventDelegate

class EntityCollisionChecker(contactChecker: ContactChecker) {
    val collided = EventDelegate<CollidedEvent>()

    init {
        contactChecker.contacted.on { handleContacted(it) }
    }

    private fun handleContacted(event: ContactedEvent) {
        val contact = event.contact
        val c1 = createContacter(contact.fixtureA)
        val c2 = createContacter(contact.fixtureB)
        val isSensorCollision = contact.fixtureA.isSensor || contact.fixtureB.isSensor
        if (c1 != null && c2 != null && (isSensorCollision || contact.worldManifold.numberOfContactPoints > 0)) {
            collided.notify(CollidedEvent(c1, c2, contact))
            collided.notify(CollidedEvent(c2, c1, contact))
        }
    }

    private fun createContacter(fixture: Fixture): Contacter? {
        val entity = fixture.body.userData as? Entity
        if (entity != null && entity.isActive) {
            return Contacter(fixture, entity)
        }
        return null
    }
}