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
        val c1 = createContacter(event.fixtureA)
        val c2 = createContacter(event.fixtureB)
        if (c1 != null && c2 != null) {
            collided.notify(CollidedEvent(c1, c2))
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