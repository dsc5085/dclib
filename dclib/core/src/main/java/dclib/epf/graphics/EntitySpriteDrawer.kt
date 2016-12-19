package dclib.epf.graphics

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import dclib.epf.Entity
import dclib.epf.EntityManager
import dclib.epf.parts.SpritePart
import dclib.epf.parts.TransformPart
import dclib.graphics.ScreenHelper
import java.util.*

class EntitySpriteDrawer(spriteBatch: PolygonSpriteBatch, screenHelper: ScreenHelper, entityManager: EntityManager)
 : EntityDrawer {
	private val spriteBatch = spriteBatch
	private val screenHelper = screenHelper
	// Ensures new entities aren't drawn because their transforms might not have been initialized
	private val newEntities = ArrayList<Entity>()

	init {
		entityManager.entityAdded.on { newEntities.add(it.entity) }
	}

	override fun draw(entities: List<Entity>) {
		Collections.sort(entities, EntityZComparator())
		screenHelper.setProjectionMatrix(spriteBatch)
		spriteBatch.begin()
		for (entity in entities) {
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

	private class EntityZComparator : Comparator<Entity> {
		override fun compare(e1: Entity, e2: Entity): Int {
			return getValue(e1).compareTo(getValue(e2))
		}

		private fun getValue(entity: Entity): Float {
			val transformPart = entity.tryGet(TransformPart::class)
			return if (transformPart != null) transformPart.transform.z else 0f
		}
	}
}