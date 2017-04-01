package dclib.physics.collision

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Contact

class CollidedEvent(val source: Contacter, val target: Contacter, private val contact: Contact) {
    val contactPoint: Vector2?
        get() {
            val worldManifold = contact.worldManifold
            return if (worldManifold.numberOfContactPoints > 0) worldManifold.points[0] else null
        }
}