package dclib.graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.epf.parts.TransformPart;
import dclib.geometry.RectangleUtils;
import dclib.geometry.UnitConverter;

public final class CameraUtils {

	private CameraUtils() {
	}

	public static final Rectangle getViewport(final Camera camera, final float pixelsPerUnit) {
		Rectangle viewport = new Rectangle(camera.position.x - camera.viewportWidth / 2,
				camera.position.y - camera.viewportHeight / 2, camera.viewportWidth, camera.viewportHeight);
		return RectangleUtils.scale(viewport, 1 / pixelsPerUnit);
	}

	// TODO: Combine functionality with UnitConverter?
	public static final void setViewport(final Camera camera, final Rectangle worldViewport,
			final float pixelsPerUnit) {
		Rectangle viewport = RectangleUtils.scale(worldViewport, pixelsPerUnit);
		camera.viewportWidth = viewport.width;
		camera.viewportHeight = viewport.height;
		camera.position.set(viewport.x + camera.viewportWidth / 2, viewport.y + camera.viewportHeight / 2, 0);
		camera.update();
	}

	public static final void follow(final Entity entity, final UnitConverter unitConverter, final Camera camera) {
		Vector2 worldViewportSize = unitConverter.toWorldUnits(camera.viewportWidth, camera.viewportHeight);
		Vector2 center = entity.get(TransformPart.class).getCenter();
		float newCameraX = center.x  - worldViewportSize.x / 2;
		float newCameraY = center.y - worldViewportSize.y / 2;
		Rectangle viewport = new Rectangle(newCameraX, newCameraY, worldViewportSize.x, worldViewportSize.y);
		CameraUtils.setViewport(camera, viewport, unitConverter.getPixelsPerUnit());
		camera.update();
	}

}
