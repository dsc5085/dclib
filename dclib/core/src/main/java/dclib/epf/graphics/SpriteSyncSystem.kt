package dclib.epf.graphics

import dclib.epf.Entity
import dclib.epf.EntityManager
import dclib.epf.EntitySystem
import dclib.epf.parts.SpritePart
import dclib.epf.parts.TransformPart
import dclib.graphics.ScreenHelper

class SpriteSyncSystem(
        entityManager: EntityManager,
        private val screenHelper: ScreenHelper
) : EntitySystem(entityManager) {
    override val isIncremental = false

    public override fun update(delta: Float, entity: Entity) {
        val spritePart = entity.tryGet(SpritePart::class)
        if (spritePart != null) {
            val transform = entity[TransformPart::class].transform
            SpriteSync.sync(spritePart.sprite, transform, screenHelper)
        }
    }
}
