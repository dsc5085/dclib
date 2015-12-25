package dclib.graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;

import dclib.geometry.RectangleUtils;

public final class CameraUtils {

	private CameraUtils() {
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
	
}
