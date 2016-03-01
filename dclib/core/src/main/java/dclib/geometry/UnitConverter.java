package dclib.geometry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

// TODO: too many responsibilities
public final class UnitConverter {
	
	private final float pixelsPerUnit;
	private final Camera camera;
	private final Rectangle viewport;
	
	public UnitConverter(final float pixelsPerUnit, final Camera camera) {
		this(pixelsPerUnit, camera, 
				new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
	}
	
	public UnitConverter(final float pixelsPerUnit, final Camera camera, final Rectangle viewport) {
		this.pixelsPerUnit = pixelsPerUnit;
		this.camera = camera;
		this.viewport = viewport;
	}
	
	public final float getPixelsPerUnit() {
		return pixelsPerUnit;
	}

	public final Vector2 toPixelUnits(final Vector2 worldUnits) {
		return worldUnits.cpy().scl(pixelsPerUnit);
	}
	
	public final Vector2 toWorldUnits(final float pixelX, final float pixelY) {
		// TODO: not the most efficient
		return toWorldCoords(pixelX, pixelY).sub(toWorldCoords(0, 0));
	}
	
	public final Vector2 toWorldCoords(final float screenX, final float screenY) {
		Vector3 worldCoords3 = new Vector3(screenX, screenY, 0);
		camera.unproject(worldCoords3, viewport.x, viewport.y, viewport.width, viewport.height);
		worldCoords3.scl(1 / pixelsPerUnit);
		return new Vector2(worldCoords3.x, worldCoords3.y);
	}
	
}
