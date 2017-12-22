package dclib.epf.graphics

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import dclib.epf.Entity
import dclib.epf.EntityManager
import dclib.epf.parts.SpritePart
import dclib.epf.parts.TransformPart
import dclib.graphics.ScreenHelper
import dclib.map.MapRenderer
import dclib.particles.ParticlesManager
import java.util.ArrayList

class SpriteDrawer(
		private val spriteBatch: PolygonSpriteBatch,
		private val screenHelper: ScreenHelper,
		private val mapRenderer: MapRenderer,
		private val getEntities: () -> List<Entity>,
		entityManager: EntityManager,
		private val particlesManager: ParticlesManager
) : Drawer {
	private val FOREGROUND_Z = 0f

	// Ensures new entities aren't drawn because their transforms might not have been initialized
	private val newEntities = ArrayList<Entity>()

	init {
		entityManager.entityAdded.on { newEntities.add(it.entity) }
	}

	override fun getName(): String {
		return "sprite"
	}

	override fun draw() {
		val drawnEntities = getEntities()
		mapRenderer.renderBackgrounds()
        draw(drawnEntities, Float.NEGATIVE_INFINITY, FOREGROUND_Z)
		mapRenderer.renderForeground()
		draw(drawnEntities, FOREGROUND_Z, Float.POSITIVE_INFINITY)
		particlesManager.draw()
	}

    private fun draw(entities: Collection<Entity>, minZ: Float, maxZ: Float) {
		val filteredEntities = entities.filter {
			val z = it[TransformPart::class].transform.z
			z >= minZ && z < maxZ
		}
        screenHelper.setProjectionMatrix(spriteBatch)
        spriteBatch.begin()
        for (entity in filteredEntities) {
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