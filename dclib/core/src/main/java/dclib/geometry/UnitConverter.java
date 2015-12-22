package dclib.geometry;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public final class UnitConverter {
	
	private final float pixelsPerUnit;
	private final Camera camera;
	
	public UnitConverter(final float pixelsPerUnit) {
		this(pixelsPerUnit, new OrthographicCamera());
	}
	
	public UnitConverter(final float pixelsPerUnit, final Camera camera) {
		this.pixelsPerUnit = pixelsPerUnit;
		this.camera = camera;
	}
	
	public final float getPixelsPerUnit() {
		return pixelsPerUnit;
	}
	
	public final Vector2 worldToPixel(final Vector2 worldCoords) {
		return worldCoords.cpy().scl(pixelsPerUnit);
	}
	
	public final Vector2 pixelToWorld(final float screenX, final float screenY) {
		return new Vector2(screenX / pixelsPerUnit, screenY / pixelsPerUnit);
	}
	
	public final Vector2 screenToWorld(final float screenX, final float screenY) {
		Rectangle viewPort = new Rectangle(0, 0, camera.viewportWidth, camera.viewportHeight);
		return screenToWorld(screenX, screenY, viewPort);
	}
	
	public final Vector2 screenToWorld(final float screenX, final float screenY, final Rectangle viewPort) {
		Vector3 worldCoords3 = new Vector3(screenX, screenY, 0);
		camera.unproject(worldCoords3, viewPort.x, viewPort.y, viewPort.width, viewPort.height);
		worldCoords3.scl(1 / pixelsPerUnit);
		return new Vector2(worldCoords3.x, worldCoords3.y);
	}
	
}
