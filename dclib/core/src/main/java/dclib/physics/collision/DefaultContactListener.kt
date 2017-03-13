package dclib.physics.collision

import com.badlogic.gdx.physics.box2d.*
import dclib.eventing.EventDelegate

class DefaultContactListener : ContactListener {
    val contacted = EventDelegate<ContactedEvent>()
    val contactEnded = EventDelegate<ContactedEvent>()

    override fun endContact(contact: Contact?) {
        tryNotifyContactEnded(contact)
    }

    override fun beginContact(contact: Contact?) {
        tryNotifyContacted(contact)
    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {
        tryNotifyContacted(contact)
    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {
        tryNotifyContacted(contact)
    }

    private fun tryNotifyContacted(contact: Contact?) {
        if (isValid(contact!!.fixtureA) && isValid(contact.fixtureB)) {
            contacted.notify(ContactedEvent(contact))
        }
    }

    private fun tryNotifyContactEnded(contact: Contact?) {
        if (isValid(contact!!.fixtureA) && isValid(contact.fixtureB)) {
            contactEnded.notify(ContactedEvent(contact))
        }
    }

    private fun isValid(fixture: Fixture?): Boolean {
        return fixture != null && fixture.body != null
    }
}