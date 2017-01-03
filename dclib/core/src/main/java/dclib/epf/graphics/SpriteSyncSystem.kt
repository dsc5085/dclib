package dclib.epf.graphics

import dclib.epf.Entity
import dclib.epf.EntityManager
import dclib.epf.EntitySystem
import dclib.epf.parts.SpritePart
import dclib.epf.parts.TransformPart
import dclib.graphics.ScreenHelper

class SpriteSyncSystem(
        entityManager: EntityManager,
        private val screenHelper: ScreenHelper)
    : EntitySystem(entityManager) {
    public override fun update(delta: Float, entity: Entity) {
        val spritePart = entity.tryGet(SpritePart::class)
        if (spritePart != null) {
            val transform = entity[TransformPart::class].transform
            val sprite = spritePart.sprite
            val origin = screenHelper.toPixelUnits(transform.origin)
            sprite.setOrigin(origin.x, origin.y)
            val size = screenHelper.toPixelUnits(transform.localSize)
            sprite.setSize(size.x, size.y)
            val scale = transform.scale
            sprite.setScale(scale.x, scale.y)
            val position = screenHelper.toPixelUnits(transform.position)
            sprite.setPosition(position.x, position.y)
            sprite.rotation = transform.rotation
        }
    }
}
