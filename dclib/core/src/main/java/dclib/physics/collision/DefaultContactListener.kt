package dclib.physics.collision

import com.badlogic.gdx.physics.box2d.*
import dclib.eventing.EventDelegate

class DefaultContactListener : ContactListener {
    val contacted = EventDelegate<ContactedEvent>()

    override fun endContact(contact: Contact?) {
        process(contact)
    }

    override fun beginContact(contact: Contact?) {
        process(contact)
    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {
        process(contact)
    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {
        process(contact)
    }

    private fun process(contact: Contact?) {
        if (isValid(contact!!.fixtureA) && isValid(contact.fixtureB)) {
            contacted.notify(ContactedEvent(contact))
        }
    }

    private fun isValid(fixture: Fixture?): Boolean {
        return fixture != null && fixture.body != null
    }
}