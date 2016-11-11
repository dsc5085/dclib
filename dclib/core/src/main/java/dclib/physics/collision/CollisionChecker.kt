package dclib.physics.collision

import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.World
import dclib.epf.Entity
import dclib.epf.EntityManager
import dclib.eventing.EventDelegate
import dclib.physics.Box2dUtils
import dclib.physics.Contacter
import dclib.system.Updater

class CollisionChecker(entityManager: EntityManager, world: World) : Updater {
	val collided = EventDelegate<CollidedEvent>()
	
	private val entityManager = entityManager
	private val world = world

	override fun update(delta: Float) {
		val entities = entityManager.all
		for (contact in world.contactList.filter { it.isTouching }) {
			val c1 = createContacter(contact.fixtureA, entities)
			val c2 = createContacter(contact.fixtureB, entities)
			if (c1 != null && c2 != null) {
				collided.notify(CollidedEvent(c1, c2))
				collided.notify(CollidedEvent(c2, c1))
			}
		}
	}

	private fun createContacter(fixture: Fixture?, entities: List<Entity>): Contacter? {
		var contacter: Contacter? = null
		if (fixture != null && fixture.body != null) {
			val entity = entities.firstOrNull { Box2dUtils.getBody(it) === fixture.body }
			if (entity != null && entity.isActive) {
				contacter = Contacter(fixture, entity)
			}
		}
		return contacter
	}
}