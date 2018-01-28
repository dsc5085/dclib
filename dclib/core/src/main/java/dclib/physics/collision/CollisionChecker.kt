package dclib.physics.collision

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.RayCastCallback
import com.badlogic.gdx.physics.box2d.World
import dclib.epf.EntityManager
import dclib.eventing.EventDelegate
import dclib.system.Updater

class CollisionChecker(entityManager: EntityManager, private val world: World) : Updater {
    val collided = EventDelegate<CollidedEvent>()

    val bodyToEntityMap = BodyToEntityMap(entityManager)

    private val currentCollisions = mutableSetOf<Collision>()

    init {
        val contactListener = DefaultContactListener()
        contactListener.contacted.on(this::handleContacted)
        contactListener.contactEnded.on(this::handleContactEnded)
        world.setContactListener(contactListener)
    }

    fun getCollisions(sourceBody: Body): List<Collision> {
        return currentCollisions.filter { it.source.body === sourceBody }
    }

    fun rayCast(callback: RayCastCallback, point1: Vector2, point2: Vector2) {
        world.rayCast(callback, point1, point2)
    }

    override fun update(delta: Float) {
        currentCollisions.removeAll { !bodyToEntityMap.has(it.source.fixture.body)
                || !bodyToEntityMap.has(it.target.fixture.body) }
        for (collision in currentCollisions.toSet()) {
            if (collision.source.entity.isActive && collision.target.entity.isActive) {
                collided.notify(CollidedEvent(collision))
            }
        }
    }

    private fun handleContacted(event: ContactedEvent) {
        val contact = event.contact
        val isSensorCollision = contact.fixtureA.isSensor || contact.fixtureB.isSensor
        if (isSensorCollision || contact.worldManifold.numberOfContactPoints > 0) {
            val c1 = createContacter(contact.fixtureA)
            val c2 = createContacter(contact.fixtureB)
            if (c1 != null && c2 != null) {
                // Deep clone manifold points since the original instances get modified
                val manifold = contact.worldManifold.points.map { it.cpy() }
                currentCollisions.add(Collision(c1, c2, contact.isTouching, manifold))
                currentCollisions.add(Collision(c2, c1, contact.isTouching, manifold))
            }
        }
    }

    private fun handleContactEnded(event: ContactedEvent) {
        currentCollisions.removeAll {
            // TODO: Check manifold equality as well?
            (it.source.fixture === event.contact.fixtureA && it.target.fixture === event.contact.fixtureB)
                    || (it.source.fixture === event.contact.fixtureB && it.target.fixture === event.contact.fixtureA)
        }
    }

    private fun createContacter(fixture: Fixture): Contacter? {
        val entity = bodyToEntityMap[fixture.body]
        if (entity != null && entity.isActive) {
            return Contacter(fixture, entity)
        }
        return null
    }
}