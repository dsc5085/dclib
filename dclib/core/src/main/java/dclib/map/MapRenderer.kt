package dclib.map

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.PolygonSprite
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.maps.MapRenderer
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.google.common.collect.EvictingQueue
import dclib.graphics.RenderUtils
import dclib.graphics.shader.MaskShader

class MapRenderer(
        private val map: TiledMap,
        private val spriteBatch: PolygonSpriteBatch,
        pixelsPerUnit: Float,
        private val camera: OrthographicCamera,
        // TODO: Create this in the constructor
        private val stageCamera: Camera
) {
    private val MAX_DECALS = 3000

    private val mapRenderer: MapRenderer
    private val maskShader = MaskShader.create()
    private val mapBuffer: FrameBuffer
    private val decalsBuffer: FrameBuffer
    private val decals = EvictingQueue.create<PolygonSprite>(MAX_DECALS)

    init {
        val scale = pixelsPerUnit / MapUtils.getPixelsPerUnit(map)
        mapRenderer = OrthogonalTiledMapRenderer(map, scale, spriteBatch)
        mapBuffer = FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.width, Gdx.graphics.height, false)
        decalsBuffer = FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.width, Gdx.graphics.height, false)
    }

    fun addDecal(decal: PolygonSprite) {
        decals.add(decal)
    }

    fun dispose() {
        mapBuffer.dispose()
        decalsBuffer.dispose()
        maskShader.dispose()
    }

    fun renderBackgrounds() {
        for (layer in MapUtils.getBackgroundLayers(map)) {
            render(layer)
        }
    }

    fun renderForeground() {
        render(MapUtils.getForegroundLayer(map))
        renderDecalBuffer()
        renderDecals()
    }

    private fun render(layer: TiledMapTileLayer) {
        renderLayerBuffer(layer)
        renderLayer()
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

    private fun renderLayerBuffer(layer: TiledMapTileLayer) {
        // Normally, tiles bigger than the layer tile's width and height don't get rendered if part of them isn't
        // in the camera's view. By increasing the camera's view, this can greatly mitigate the problem.
        val viewBufferMultiplier = 4f
        val viewBuffer = Vector2(layer.tileWidth, layer.tileHeight).scl(viewBufferMultiplier)
        val previousCameraPosition = camera.position.cpy()
        val previousCameraSize = Vector2(camera.viewportWidth, camera.viewportHeight)
        camera.position.sub(viewBuffer.x, viewBuffer.y, 0f)
        camera.viewportWidth += viewBuffer.x * 2
        camera.viewportHeight += viewBuffer.y * 2
        mapBuffer.begin()
        RenderUtils.clear()
        mapRenderer.setView(camera)
        val layerIndex = map.layers.indexOf(layer)
        mapRenderer.render(intArrayOf(layerIndex))
        mapBuffer.end()
        camera.position.set(previousCameraPosition)
        camera.viewportWidth = previousCameraSize.x
        camera.viewportHeight = previousCameraSize.y
    }

    private fun renderLayer() {
        val texture = mapBuffer.colorBufferTexture
        spriteBatch.projectionMatrix = stageCamera.combined
        spriteBatch.begin()
        spriteBatch.draw(texture, 0f, 0f, texture.width.toFloat(), texture.height.toFloat(), 0, 0,
                texture.width, texture.height, false, true)
        spriteBatch.end()
    }

    private fun renderDecals() {
        val texture = mapBuffer.colorBufferTexture
        spriteBatch.projectionMatrix = stageCamera.combined
        decalsBuffer.colorBufferTexture.bind(1)
        mapBuffer.colorBufferTexture.bind(2)
        // Reset active texture since sprite batch doesn't do it
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0)
        spriteBatch.shader = maskShader
        spriteBatch.begin()
        spriteBatch.draw(texture, 0f, 0f, texture.width.toFloat(), texture.height.toFloat(), 0, 0,
                texture.width, texture.height, false, true)
        spriteBatch.end()
        spriteBatch.shader = null
    }
}