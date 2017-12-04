package dclib.epf.graphics

import com.badlogic.gdx.graphics.g2d.PolygonSprite
import dclib.graphics.ScreenHelper
import dclib.physics.Transform

object SpriteSync {
    fun sync(sprite: PolygonSprite, transform: Transform, screenHelper: ScreenHelper) {
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