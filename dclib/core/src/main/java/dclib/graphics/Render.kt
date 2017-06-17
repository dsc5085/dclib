package dclib.graphics

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class Render {
    val pixelsPerUnit = 32f
    val sprite = PolygonSpriteBatch()
    val shape = ShapeRenderer()

    fun dispose() {
        sprite.dispose()
        shape.dispose()
    }
}