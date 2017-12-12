package dclib.graphics

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import dclib.geometry.scale
import dclib.geometry.toVector2

object CameraUtils {
    fun getViewport(camera: Camera, pixelsPerUnit: Float): Rectangle {
        val viewport = Rectangle(camera.position.x - camera.viewportWidth / 2,
                camera.position.y - camera.viewportHeight / 2, camera.viewportWidth, camera.viewportHeight)
        return viewport.scale(1 / pixelsPerUnit)
    }

    fun setViewport(camera: Camera, worldViewport: Rectangle, pixelsPerUnit: Float) {
        val viewport = worldViewport.scale(pixelsPerUnit)
        camera.viewportWidth = viewport.width
        camera.viewportHeight = viewport.height
        camera.position.set(viewport.x + camera.viewportWidth / 2, viewport.y + camera.viewportHeight / 2, 0f)
        camera.update()
    }

    fun center(camera: Camera, screenHelper: ScreenHelper): Vector2 {
        return screenHelper.toWorldUnits(camera.position.toVector2().x, camera.position.toVector2().y)
//        val cameraCenter = camera.position.toVector2().add(camera.viewportWidth / 2f, camera.viewportHeight / 2f)
//        return screenHelper.toWorldCoord(cameraCenter)
    }

    fun lookAt(center: Vector2, screenHelper: ScreenHelper, camera: Camera) {
        val worldViewportSize = screenHelper.toWorldUnits(camera.viewportWidth, camera.viewportHeight)
        val newCameraX = center.x - worldViewportSize.x / 2
        val newCameraY = center.y - worldViewportSize.y / 2
        val viewport = Rectangle(newCameraX, newCameraY, worldViewportSize.x, worldViewportSize.y)
        setViewport(camera, viewport, screenHelper.pixelsPerUnit)
    }
}
