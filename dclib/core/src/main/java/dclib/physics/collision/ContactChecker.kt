package dclib.physics.collision

import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.World
import dclib.eventing.EventDelegate
import dclib.system.Updater

class ContactChecker(private val world: World) : Updater {
    val contacted = EventDelegate<ContactedEvent>()

    override fun update(delta: Float) {
        // TODO: Optimize
        for (contact in world.contactList.filter { it.isTouching }) {
            if (isValid(contact.fixtureA) && isValid(contact.fixtureB)) {
                contacted.notify(ContactedEvent(contact.fixtureA, contact.fixtureB))
                contacted.notify(ContactedEvent(contact.fixtureB, contact.fixtureA))
            }
        }
    }

    private fun isValid(fixture: Fixture?): Boolean {
        return fixture != null && fixture.body != null
    }
}