package dclib.physics.collision

import com.badlogic.gdx.physics.box2d.*
import dclib.eventing.EventDelegate

class DefaultContactListener : ContactListener {
    val contacted = EventDelegate<ContactedEvent>()

    override fun endContact(contact: Contact?) {
        if (isValid(contact!!.fixtureA) && isValid(contact.fixtureB)) {
            contacted.notify(ContactedEvent(contact))
        }
    }

    override fun beginContact(contact: Contact?) {
    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {
    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {
    }

    private fun isValid(fixture: Fixture?): Boolean {
        return fixture != null && fixture.body != null
    }
}