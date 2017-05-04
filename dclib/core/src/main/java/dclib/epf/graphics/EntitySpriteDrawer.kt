package dclib.epf.graphics

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import dclib.epf.Entity
import dclib.epf.EntityManager
import dclib.epf.parts.SpritePart
import dclib.graphics.ScreenHelper
import java.util.*

class EntitySpriteDrawer(
		private val spriteBatch: PolygonSpriteBatch,
		private val screenHelper: ScreenHelper,
		private val getEntities: () -> List<Entity>,
		entityManager: EntityManager
) : EntityDrawer {
	// Ensures new entities aren't drawn because their transforms might not have been initialized
	private val newEntities = ArrayList<Entity>()

	init {
		entityManager.entityAdded.on { newEntities.add(it.entity) }
	}

	override fun getName(): String {
		return "sprite"
	}

	override fun draw(entities: Collection<Entity>) {
		screenHelper.setProjectionMatrix(spriteBatch)
		spriteBatch.begin()
		for (entity in getEntities()) {
			if (newEntities.contains(entity)) {
				newEntities.remove(entity)
			} else {
				draw(entity)
			}
		}
		spriteBatch.end()
	}

	private fun draw(entity: Entity) {
		val spritePart = entity.tryGet(SpritePart::class)
		if (spritePart != null) {
			spritePart.sprite.draw(spriteBatch)
		}
	}
}