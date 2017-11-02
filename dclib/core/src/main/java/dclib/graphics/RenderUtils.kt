package dclib.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.PolygonSprite
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage

object RenderUtils {
    fun drawCursor(sprite: PolygonSprite, spriteBatch: PolygonSpriteBatch, stage: Stage) {
        spriteBatch.projectionMatrix = stage.camera.combined
        spriteBatch.begin()
        sprite.setPosition(Gdx.input.x - sprite.width / 2, Gdx.graphics.height - Gdx.input.y - sprite.height / 2)
        sprite.draw(spriteBatch)
        spriteBatch.end()
    }

    fun clear() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }
}
