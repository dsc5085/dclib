package dclib.graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;

public final class CameraUtils {

	private CameraUtils() {
	}
	
	public static final void setViewBox(final Camera camera, final Rectangle viewBox) {
		camera.viewportWidth = viewBox.width;
		camera.viewportHeight = viewBox.height;
		camera.position.set(viewBox.x + camera.viewportWidth / 2, viewBox.y + camera.viewportHeight / 2, 0);
		camera.update();
	}
	
}
