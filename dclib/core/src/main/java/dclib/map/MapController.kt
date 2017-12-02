package dclib.map

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.PolygonSprite
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.maps.MapRenderer
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import dclib.graphics.RenderUtils
import dclib.graphics.ScreenHelper
import dclib.graphics.TextureCache
import dclib.graphics.shader.MaskShader

class MapController(
        map: TiledMap,
        private val spriteBatch: PolygonSpriteBatch,
        private val textureCache: TextureCache,
        private val screenHelper: ScreenHelper,
        private val camera: OrthographicCamera,
        private val stage: Stage
) {
    private val mapRenderer: MapRenderer
    private val maskShader = MaskShader.create()
    private val mapBuffer: FrameBuffer
    private val decalsBuffer: FrameBuffer
    private val decals = mutableListOf<PolygonSprite>()

    private val transparent = textureCache.getTextureRegion("objects/transparent")

    init {
        val scale = screenHelper.pixelsPerUnit / MapUtils.getPixelsPerUnit(map)
        mapRenderer = OrthogonalTiledMapRenderer(map, scale, spriteBatch)
        mapBuffer = FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.width, Gdx.graphics.height, false)
        decalsBuffer = FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.width, Gdx.graphics.height, false)
    }

    fun addDecal(decal: PolygonSprite) {
        decals.add(decal)
    }

    fun renderLayer(layerIndex: Int) {
        renderDecalBuffer()
        renderLayerBuffer(layerIndex)

        spriteBatch.projectionMatrix = stage.camera.combined
        spriteBatch.begin()
        spriteBatch.draw(mapBuffer.colorBufferTexture, 0f, 0f, mapBuffer.colorBufferTexture.width.toFloat(), mapBuffer.colorBufferTexture.height.toFloat(),
                0, 0, mapBuffer.colorBufferTexture.width, mapBuffer.colorBufferTexture.height, false, true)
        spriteBatch.end()


//        spriteBatch.projectionMatrix = stage.camera.combined
//        spriteBatch.begin()
//        spriteBatch.draw(decalsBuffer.colorBufferTexture, 0f, 0f, decalsBuffer.colorBufferTexture.width.toFloat(), decalsBuffer.colorBufferTexture.height.toFloat(),
//                0, 0, decalsBuffer.colorBufferTexture.width, decalsBuffer.colorBufferTexture.height, false, true)
//        spriteBatch.end()

        val texture = mapBuffer.colorBufferTexture
        spriteBatch.projectionMatrix = stage.camera.combined
        decalsBuffer.colorBufferTexture.bind(1)
        mapBuffer.colorBufferTexture.bind(2)
        // now we need to reset glActiveTexture to zero!!!! since sprite batch does not do this for us
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0)
        spriteBatch.shader = maskShader
        spriteBatch.begin()
        spriteBatch.draw(mapBuffer.colorBufferTexture, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat(), 0, 0, mapBuffer.colorBufferTexture.width, mapBuffer.colorBufferTexture.height, false, true)
        spriteBatch.end()
        spriteBatch.shader = null
    }

    fun dispose() {
        decalsBuffer.dispose()
    }

    private fun renderDecalBuffer() {
        spriteBatch.projectionMatrix = camera.combined
        decalsBuffer.begin()
        RenderUtils.clear()
        spriteBatch.begin()
        for (decal in decals) {
            decal.draw(spriteBatch)
        }
        spriteBatch.end()
        decalsBuffer.end()
    }

    private fun renderLayerBuffer(layerIndex: Int) {
        mapBuffer.begin()
        RenderUtils.clear()
        mapRenderer.setView(camera)
        mapRenderer.render(intArrayOf(layerIndex))
        mapBuffer.end()
    }
}